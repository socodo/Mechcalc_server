package com.socodo.mechcalc.auth.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.socodo.mechcalc.auth.dto.request.*;
import com.socodo.mechcalc.auth.dto.response.*;
import com.socodo.mechcalc.exception.AppException;
import com.socodo.mechcalc.exception.ErrorCode;
import com.socodo.mechcalc.user.entity.User;
import com.socodo.mechcalc.user.mapper.UserMapper;
import com.socodo.mechcalc.user.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {

    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    JwtService jwtService;

    @NonFinal 
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    String googleClientId;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_CREDENTIALS));

        validateActiveAccount(user);

        if (user.getLockedUntil() != null && user.getLockedUntil().isAfter(Instant.now())) {
            throw new AppException(ErrorCode.ACCOUNT_LOCKED);
        }

        boolean isAuthenticated = passwordEncoder.matches(request.getPassword(), user.getPasswordHash());

        if (!isAuthenticated) {
            int attempts = (user.getFailedLoginAttempts() != null ? user.getFailedLoginAttempts() : 0) + 1;
            user.setFailedLoginAttempts(attempts);

            if (attempts >= 5) {
                user.setLockedUntil(Instant.now().plus(Duration.ofMinutes(15)));
            }

            userRepository.save(user);
            throw new AppException(ErrorCode.INVALID_CREDENTIALS);
        }

        user.setFailedLoginAttempts(0);
        user.setLockedUntil(null);
        userRepository.save(user);

        return buildAuthenticationResponse(user);
    }

    public IntrospectResponse introspect(IntrospectRequest request) {
        boolean valid = jwtService.isAccessTokenValid(request.getAccessToken());

        if (valid) {
            try {
                String email = jwtService.extractSubject(request.getAccessToken());
                valid = userRepository.findByEmail(email)
                        .filter(this::isActiveAccount)
                        .isPresent();
            } catch (RuntimeException exception) {
                valid = false;
            }
        }

        return IntrospectResponse.builder()
                .valid(valid)
                .build();
    }
    
    public AuthenticationResponse refreshToken(RefreshRequest request) {
        String refreshToken = request.getToken();
        if (!jwtService.isRefreshTokenValid(refreshToken)) {
            throw new AppException(ErrorCode.INVALID_CREDENTIALS); 
        }

        String email = jwtService.extractSubject(refreshToken);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_CREDENTIALS));

        validateActiveAccount(user);

        return AuthenticationResponse.builder()
                .accessToken(jwtService.generateAccessToken(user))
                .refreshToken(refreshToken) 
                .tokenType("Bearer")
                .expiresIn(jwtService.getAccessTokenExpirationSeconds())
                .user(userMapper.toResponse(user))
                .build();
    }

    public AuthenticationResponse googleLogin(GoogleLoginRequest request) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                    .setAudience(Collections.singletonList(googleClientId))
                    .build();

            GoogleIdToken idToken = verifier.verify(request.getIdToken());
            
            if (idToken == null) {
                throw new AppException(ErrorCode.INVALID_CREDENTIALS);
            }

            GoogleIdToken.Payload payload = idToken.getPayload();
            String email = payload.getEmail();

            String name = (String) payload.get("name");

            User user = userRepository.findByEmail(email).orElseGet(() -> {
                User newUser = User.builder()
                        .email(email)
                        .fullName(name)
                        .role("USER")
                        .status(User.UserStatus.ACTIVE)
                        .passwordHash(passwordEncoder.encode(UUID.randomUUID().toString())) 
                        .build();
                return userRepository.save(newUser);
            });

            validateActiveAccount(user);

            return buildAuthenticationResponse(user);

        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error during Google Authentication", e);
            throw new AppException(ErrorCode.INVALID_CREDENTIALS);
        }
    }

    public AuthenticationResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new AppException(ErrorCode.USER_ALREADY_EXISTS);
        }

        User user = User.builder()
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .role("USER")
                .status(User.UserStatus.ACTIVE)
                .build();

        userRepository.save(user);
        
        return buildAuthenticationResponse(user);
    }

    private AuthenticationResponse buildAuthenticationResponse(User user) {
        return AuthenticationResponse.builder()
                .accessToken(jwtService.generateAccessToken(user))
                .refreshToken(jwtService.generateRefreshToken(user))
                .tokenType("Bearer")
                .expiresIn(jwtService.getAccessTokenExpirationSeconds())
                .user(userMapper.toResponse(user))
                .build();
    }

    private void validateActiveAccount(User user) {
        if (user.getStatus() != User.UserStatus.ACTIVE) {
            throw new AppException(ErrorCode.ACCOUNT_BANNED);
        }
    }

    private boolean isActiveAccount(User user) {
        return user.getStatus() == User.UserStatus.ACTIVE;
    }
}

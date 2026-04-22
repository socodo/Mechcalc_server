package com.socodo.mechcalc.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

import com.socodo.mechcalc.dto.request.AuthenticationRequest;
import com.socodo.mechcalc.dto.request.GoogleLoginRequest;
import com.socodo.mechcalc.dto.request.IntrospectRequest;
import com.socodo.mechcalc.dto.request.RefreshRequest;
import com.socodo.mechcalc.dto.response.AuthenticationResponse;
import com.socodo.mechcalc.dto.response.IntrospectResponse;
import com.socodo.mechcalc.entity.User;
import com.socodo.mechcalc.exception.AppException;
import com.socodo.mechcalc.exception.ErrorCode;
import com.socodo.mechcalc.mapper.UserMapper;
import com.socodo.mechcalc.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

        boolean isAuthenticated = passwordEncoder.matches(request.getPassword(), user.getPasswordHash());
        if (!isAuthenticated) {
            log.warn("Authentication failed for email: {}", request.getEmail());
            throw new AppException(ErrorCode.INVALID_CREDENTIALS);
        }

        log.info("Authentication successful for email: {}", request.getEmail());
        return buildAuthenticationResponse(user);
    }

    public IntrospectResponse introspect(IntrospectRequest request) {
        return IntrospectResponse.builder()
                .valid(jwtService.isAccessTokenValid(request.getAccessToken()))
                .build();
    }
    
    public AuthenticationResponse refreshToken(RefreshRequest request) {
        String refreshToken = request.getToken();
        if (!jwtService.isRefreshTokenValid(refreshToken)) {
            log.warn("Invalid refresh token attempted");
            throw new AppException(ErrorCode.INVALID_CREDENTIALS); 
        }

        String email = jwtService.extractSubject(refreshToken);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_CREDENTIALS));

        log.info("Successfully refreshed token for email: {}", email);

        return AuthenticationResponse.builder()
                .accessToken(jwtService.generateAccessToken(user))
                .refreshToken(refreshToken) 
                .tokenType("Bearer")
                .expiresIn(jwtService.getAccessTokenExpirationSeconds())
                .user(userMapper.toResponse(user))
                .build();
    }

    public AuthenticationResponse googleLogin(GoogleLoginRequest request) {
        log.info("Backend is using Client ID: {}", googleClientId);
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                    .setAudience(Collections.singletonList(googleClientId))
                    .build();

            GoogleIdToken idToken = verifier.verify(request.getIdToken());
            
            if (idToken == null) {
                log.warn("Google token invalid");
                throw new AppException(ErrorCode.INVALID_CREDENTIALS);
            }

            GoogleIdToken.Payload payload = idToken.getPayload();
            String email = payload.getEmail();
            String name = (String) payload.get("name");

            User user = userRepository.findByEmail(email).orElseGet(() -> {
                log.info("Creating new user from Google Login: {}", email);
                User newUser = User.builder()
                        .email(email)
                        .fullName(name)
                        .role("USER")
                        .passwordHash(passwordEncoder.encode(UUID.randomUUID().toString())) 
                        .build();
                return userRepository.save(newUser);
            });

            return buildAuthenticationResponse(user);

        } catch (Exception e) {
            log.error("Error during Google Authentication", e);
            throw new AppException(ErrorCode.INVALID_CREDENTIALS);
        }
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
}
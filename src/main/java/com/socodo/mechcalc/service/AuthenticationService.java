package com.socodo.mechcalc.service;

import com.socodo.mechcalc.dto.request.AuthenticationRequest;
import com.socodo.mechcalc.dto.request.IntrospectRequest;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {

    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    JwtService jwtService;

    public AuthenticationResponse authenticated(AuthenticationRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_CREDENTIALS));

        boolean isAuthenticated = passwordEncoder.matches(request.getPassword(), user.getPasswordHash());
        if (!isAuthenticated) {
            log.warn("Authentication failed for email: {}", request.getEmail());
            throw new AppException(ErrorCode.INVALID_CREDENTIALS);
        }

        log.info("Authentication successful for email: {}", request.getEmail());

        return AuthenticationResponse.builder()
                .accessToken(jwtService.generateAccessToken(user))
                .refreshToken(jwtService.generateRefreshToken(user))
                .tokenType("Bearer")
                .expiresIn(jwtService.getAccessTokenExpirationSeconds())
                .user(userMapper.toResponse(user))
                .build();
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        return authenticated(request);
    }

    public IntrospectResponse introspect(IntrospectRequest request) {
        return IntrospectResponse.builder()
                .valid(jwtService.isAccessTokenValid(request.getAccessToken()))
                .build();
    }
}

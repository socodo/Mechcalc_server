package com.socodo.mechcalc.controller;

import com.socodo.mechcalc.dto.request.*;
import com.socodo.mechcalc.dto.response.*;

import com.socodo.mechcalc.service.AuthenticationService;
import com.socodo.mechcalc.service.UserService;
import com.socodo.mechcalc.dto.request.RefreshRequest;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

    UserService userService;
    AuthenticationService authenticationService;

    @PostMapping("/register")
    public ApiResponse<UserResponse> register(@Valid @RequestBody UserCreateRequest request) {
        UserResponse result = userService.registerUser(request);
        return ApiResponse.success("Registration successful", result);
    }

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> authenticate(@Valid @RequestBody AuthenticationRequest request) {
        AuthenticationResponse result = authenticationService.authenticate(request);
        return ApiResponse.success("Login successful", result);
    }

    @PostMapping("/google")
    public ApiResponse<AuthenticationResponse> googleLogin(@Valid @RequestBody GoogleLoginRequest request) {
        AuthenticationResponse result = authenticationService.googleLogin(request);
        return ApiResponse.success("Google login successful", result);
    }

    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> introspect(@Valid @RequestBody IntrospectRequest request) {
        IntrospectResponse result = authenticationService.introspect(request);
        return ApiResponse.success("Token valid", result);
    }

    @PostMapping("/refresh")
    public ApiResponse<AuthenticationResponse> refreshToken(@Valid @RequestBody RefreshRequest request){
        AuthenticationResponse result = authenticationService.refreshToken(request);
        return ApiResponse.success("Token refreshed successfully", result);
    }
}
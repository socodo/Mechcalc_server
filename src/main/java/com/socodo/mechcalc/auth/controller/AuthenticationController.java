package com.socodo.mechcalc.auth.controller;

import com.socodo.mechcalc.auth.dto.request.AuthenticationRequest;
import com.socodo.mechcalc.auth.dto.request.GoogleLoginRequest;
import com.socodo.mechcalc.auth.dto.request.IntrospectRequest;
import com.socodo.mechcalc.auth.dto.request.RefreshRequest;
import com.socodo.mechcalc.auth.dto.respone.AuthenticationResponse;
import com.socodo.mechcalc.auth.dto.respone.IntrospectResponse;
import com.socodo.mechcalc.auth.service.AuthenticationService;
import com.socodo.mechcalc.common.dto.response.ApiResponse;
import com.socodo.mechcalc.user.dto.request.UserCreateRequest;
import com.socodo.mechcalc.user.dto.response.UserResponse;
import com.socodo.mechcalc.user.service.UserService;

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
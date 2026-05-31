package com.socodo.mechcalc.auth.controller;

import com.socodo.mechcalc.auth.dto.request.AuthenticationRequest;
import com.socodo.mechcalc.auth.dto.request.GoogleLoginRequest;
import com.socodo.mechcalc.auth.dto.request.IntrospectRequest;
import com.socodo.mechcalc.auth.dto.request.RefreshRequest;
import com.socodo.mechcalc.auth.dto.request.RegisterRequest;
import com.socodo.mechcalc.auth.dto.response.AuthenticationResponse;
import com.socodo.mechcalc.auth.dto.response.IntrospectResponse;
import com.socodo.mechcalc.auth.service.AuthenticationService;
import com.socodo.mechcalc.common.dto.response.ApiResponse;

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

    AuthenticationService authenticationService;

    @PostMapping("/register")
    public ApiResponse<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthenticationResponse result = authenticationService.register(request);
        return ApiResponse.success("Đăng ký thành công", result);
    }

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> authenticate(@Valid @RequestBody AuthenticationRequest request) {
        AuthenticationResponse result = authenticationService.authenticate(request);
        return ApiResponse.success("Đăng nhập thành công", result);
    }

    @PostMapping("/google")
    public ApiResponse<AuthenticationResponse> googleLogin(@Valid @RequestBody GoogleLoginRequest request) {
        AuthenticationResponse result = authenticationService.googleLogin(request);
        return ApiResponse.success("Đăng nhập bằng Google thành công", result);
    }

    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> introspect(@Valid @RequestBody IntrospectRequest request) {
        IntrospectResponse result = authenticationService.introspect(request);
        return ApiResponse.success("Token hợp lệ", result);
    }

    @PostMapping("/refresh")
    public ApiResponse<AuthenticationResponse> refreshToken(@Valid @RequestBody RefreshRequest request){
        AuthenticationResponse result = authenticationService.refreshToken(request);
        return ApiResponse.success("Làm mới token thành công", result);
    }
}

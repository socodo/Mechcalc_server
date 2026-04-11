package com.socodo.mechcalc.controller;

import com.socodo.mechcalc.dto.request.AuthenticationRequest;
import com.socodo.mechcalc.dto.request.IntrospectRequest;
import com.socodo.mechcalc.dto.response.ApiResponse;
import com.socodo.mechcalc.dto.response.AuthenticationResponse;
import com.socodo.mechcalc.dto.response.IntrospectResponse;
import com.socodo.mechcalc.service.AuthenticationService;
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

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> authenticate(
            @Valid @RequestBody AuthenticationRequest request
    ) {
        AuthenticationResponse result = authenticationService.authenticated(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .code("SUCCESS")
                .success(true)
                .message("Login successful")
                .data(result)
                .build();
    }

    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> introspect(
            @Valid @RequestBody IntrospectRequest request
    ) {
        IntrospectResponse result = authenticationService.introspect(request);
        return ApiResponse.success("Token introspection completed", result);
    }
}
package com.socodo.mechcalc.controller;

import com.socodo.mechcalc.dto.request.UserCreateRequest;
import com.socodo.mechcalc.dto.request.UserUpdateRequest;
import com.socodo.mechcalc.dto.response.ApiResponse;
import com.socodo.mechcalc.dto.response.UserResponse;
import com.socodo.mechcalc.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @PostMapping("/register")
    public ApiResponse<UserResponse> register(@Valid @RequestBody UserCreateRequest request) {
        UserResponse response = userService.register(request);
        return ApiResponse.success("Register successful", response);
    }

    @GetMapping
    public ApiResponse<List<UserResponse>> getUsers() {
        List<UserResponse> users = userService.getUsers();
        return ApiResponse.success("Users fetched successfully", users);
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUser(@PathVariable UUID id) {
        UserResponse user = userService.getUser(id);
        return ApiResponse.success("User fetched successfully", user);
    }

    @PutMapping("/{id}")
    public ApiResponse<UserResponse> updateUser(
            @PathVariable UUID id,
            @Valid @RequestBody UserUpdateRequest request
    ) {
        UserResponse user = userService.updateUser(id, request);
        return ApiResponse.success("User updated successfully", user);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ApiResponse.success("User deleted successfully");
    }
}
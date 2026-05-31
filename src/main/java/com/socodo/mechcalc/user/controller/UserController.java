package com.socodo.mechcalc.user.controller;

import com.socodo.mechcalc.common.dto.response.ApiResponse;
import com.socodo.mechcalc.user.dto.request.UserCreateRequest;
import com.socodo.mechcalc.user.dto.request.UserStatusUpdateRequest;
import com.socodo.mechcalc.user.dto.request.UserUpdateRequest;
import com.socodo.mechcalc.user.dto.response.UserResponse;
import com.socodo.mechcalc.user.service.UserService;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

    //USER

    @GetMapping("/my-profile")
    public ApiResponse<UserResponse> getMyProfile() {
        UserResponse user = userService.getMyInfo();
        return ApiResponse.success("Lấy thông tin cá nhân thành công", user);
    }

    @PutMapping("/my-profile")
    public ApiResponse<UserResponse> updateMyProfile(@Valid @RequestBody UserUpdateRequest request) {
        UserResponse user = userService.updateMyInfo(request);
        return ApiResponse.success("Cập nhật thông tin cá nhân thành công", user);
    }

    // ADMIN

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/create-admin")
    public ApiResponse<UserResponse> createAdmin(@Valid @RequestBody UserCreateRequest request) {
        UserResponse response = userService.createAdmin(request);
        return ApiResponse.success("Tạo tài khoản quản trị thành công", response);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ApiResponse<List<UserResponse>> getUsers() {
        List<UserResponse> users = userService.getUsers();
        return ApiResponse.success("Lấy danh sách người dùng thành công", users);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUser(@PathVariable UUID id) {
        UserResponse user = userService.getUser(id);
        return ApiResponse.success("Lấy thông tin người dùng thành công", user);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("/{id}/status")
    public ApiResponse<UserResponse> updateUserStatus(
            @PathVariable UUID id,
            @Valid @RequestBody UserStatusUpdateRequest request
    ) {
        UserResponse user = userService.updateUserStatus(id, request);
        return ApiResponse.success("Cập nhật trạng thái người dùng thành công", user);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ApiResponse.success("Xóa người dùng thành công");
    }
}

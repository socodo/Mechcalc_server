package com.socodo.mechcalc.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequest {

    @Email(message = "Email is not valid")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Pattern(
    regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$", 
    message = "Mật khẩu tối thiểu 8 ký tự, bao gồm chữ hoa, chữ thường, số và ký tự đặc biệt"
)
    private String password;

    @NotBlank(message = "Full name is required")
    private String fullName;

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone must be exactly 10 digits")
    private String phone;

    private String avatarUrl;

    private String organization;
}

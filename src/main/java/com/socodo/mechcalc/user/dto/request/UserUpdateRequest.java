package com.socodo.mechcalc.user.dto.request;

import jakarta.validation.constraints.Email;
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
public class UserUpdateRequest {

    @Email(message = "Email is not valid")
    private String email;

    private String fullName;

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone must be exactly 10 digits")
    private String phone;

    private String avatarUrl;

    private String organization;
}

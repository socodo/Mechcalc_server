package com.socodo.mechcalc.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;
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
public class UserResponse {

    private UUID id;
    private String email;
    private String fullName;
    private String phone;
    private String avatarUrl;
    private String organization;
    private String status;
    private Integer failedLoginAttempts;
    private LocalDateTime lockedUntil;
    private LocalDateTime createdAt;
}

package com.socodo.mechcalc.user.dto.response;

import java.time.Instant;
import java.util.UUID;
import com.socodo.mechcalc.user.entity.User;
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
    private User.UserStatus status;
    private Integer failedLoginAttempts;
    private Instant lockedUntil;
    private Instant createdAt;
}

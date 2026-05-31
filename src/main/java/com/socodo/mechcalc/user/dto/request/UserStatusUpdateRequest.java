package com.socodo.mechcalc.user.dto.request;

import com.socodo.mechcalc.user.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserStatusUpdateRequest {

    @NotNull(message = "Trạng thái người dùng không được để trống")
    private User.UserStatus status;
}

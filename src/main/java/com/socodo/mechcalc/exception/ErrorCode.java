package com.socodo.mechcalc.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "Uncategorized error"),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "Invalid request"),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "Invalid email or password, lock account after 5 failed attempts"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "User already exists"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Unauthorized"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "Forbidden"),
    PROJECT_NOT_FOUND(HttpStatus.NOT_FOUND, "Project not found"),
    CALCULATION_NOT_FOUND(HttpStatus.NOT_FOUND, "Motor calculation not found"),
    ACCOUNT_LOCKED(HttpStatus.FORBIDDEN, "Tài khoản đã bị khóa do nhập sai quá 5 lần. Vui lòng thử lại sau 15 phút");
    private final HttpStatus status;
    private final String message;
}

package com.socodo.mechcalc.common.dto.response;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private static final String SUCCESS_CODE = "SUCCESS";
    private static final String ERROR_CODE = "ERROR";

    @Builder.Default
    private Instant timestamp = Instant.now();

    private String code;
    private boolean success;
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(String message, T data) {
        return build(SUCCESS_CODE, true, message, data);
    }

    public static ApiResponse<Void> success(String message) {
        return build(SUCCESS_CODE, true, message, null);
    }

    public static ApiResponse<Void> error(String message) {
        return error(ERROR_CODE, message);
    }

    public static ApiResponse<Void> error(String code, String message) {
        return build(code, false, message, null);
    }

    public static <T> ApiResponse<T> error(String code, String message, T data) {
        return build(code, false, message, data);
    }

    private static <T> ApiResponse<T> build(String code, boolean success, String message, T data) {
        return ApiResponse.<T>builder()
                .code(code)
                .success(success)
                .message(message)
                .data(data)
                .build();
    }
}

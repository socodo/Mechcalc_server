package com.socodo.mechcalc.exception;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.socodo.mechcalc.common.dto.response.ApiResponse;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse<Void>> handleAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        return ResponseEntity.status(errorCode.getStatus())
                .body(ApiResponse.error(errorCode.name(), exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationException(
            MethodArgumentNotValidException exception
    ) {
        Map<String, String> errors = new LinkedHashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(fieldError ->
                errors.put(fieldError.getField(), fieldError.getDefaultMessage())
        );

        return ResponseEntity.status(ErrorCode.INVALID_REQUEST.getStatus())
                .body(ApiResponse.<Map<String, String>>builder()
                        .code(ErrorCode.INVALID_REQUEST.name())
                        .success(false)
                        .message(ErrorCode.INVALID_REQUEST.getMessage())
                        .data(errors)
                        .build());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgumentException(IllegalArgumentException exception) {
        return ResponseEntity.status(ErrorCode.INVALID_REQUEST.getStatus())
                .body(ApiResponse.error(ErrorCode.INVALID_REQUEST.name(), exception.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleUnhandledException(Exception exception) {
        return ResponseEntity.status(ErrorCode.UNCATEGORIZED_EXCEPTION.getStatus())
                .body(ApiResponse.error(
                        ErrorCode.UNCATEGORIZED_EXCEPTION.name(),
                        ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage()
                ));
    }
}

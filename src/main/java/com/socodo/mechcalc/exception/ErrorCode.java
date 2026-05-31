package com.socodo.mechcalc.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "Đã xảy ra lỗi. Vui lòng thử lại sau."),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "Yêu cầu không hợp lệ."),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "Email hoặc mật khẩu không đúng."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "Không tìm thấy người dùng."),
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "Email đã được sử dụng."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Bạn cần đăng nhập để tiếp tục."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "Bạn không có quyền thực hiện thao tác này."),
    PROJECT_NOT_FOUND(HttpStatus.NOT_FOUND, "Không tìm thấy dự án."),
    CALCULATION_NOT_FOUND(HttpStatus.NOT_FOUND, "Không tìm thấy kết quả tính chọn động cơ."),
    CHAIN_CALCULATION_NOT_FOUND(HttpStatus.NOT_FOUND, "Không tìm thấy kết quả tính bộ truyền xích."),
    ACCOUNT_BANNED(HttpStatus.FORBIDDEN, "Tài khoản đã bị cấm. Vui lòng liên hệ quản trị viên."),
    ACCOUNT_LOCKED(HttpStatus.FORBIDDEN, "Tài khoản đã bị khóa tạm thời do đăng nhập sai quá 5 lần. Vui lòng thử lại sau 15 phút.");
    private final HttpStatus status;
    private final String message;
}

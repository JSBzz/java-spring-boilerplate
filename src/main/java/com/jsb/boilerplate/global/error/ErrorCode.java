package com.jsb.boilerplate.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // Common
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C001", " 올바르지 않은 입력값입니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "C002", " 허용되지 않은 메소드입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C003", " 서버 내부 오류입니다."),
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "C004", " 타입이 올바르지 않습니다."),
    HANDLE_ACCESS_DENIED(HttpStatus.FORBIDDEN, "C005", " 권한이 없습니다."),

    // Member
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "M001", " 존재하지 않는 회원입니다."),
    LOGIN_ID_DUPLICATION(HttpStatus.CONFLICT, "M002", " 이미 존재하는 아이디입니다."),
    INVALID_LOGIN_ID(HttpStatus.BAD_REQUEST, "M003", " 아이디 또는 비밀번호가 올바르지 않습니다."),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "A001", " 아이디 또는 비밀번호가 올바르지 않습니다."),

    // Auth
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "A002", " 유효하지 않은 토큰입니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "A003", " 토큰이 만료되었습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}

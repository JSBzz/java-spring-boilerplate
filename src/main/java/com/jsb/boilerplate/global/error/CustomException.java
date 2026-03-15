package com.jsb.boilerplate.global.error;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final int status;
    private final String code;

    public CustomException(String message, int status, String code) {
        super(message);
        this.status = status;
        this.code = code;
    }
}

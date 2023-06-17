package io.wisoft.capstonedesign.global.exception.service;

import io.wisoft.capstonedesign.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class InfoNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String message;

    public InfoNotFoundException() {
        this.errorCode = ErrorCode.NOT_FOUND_INFO;
        this.message = ErrorCode.NOT_FOUND_INFO.getMessage();
    }
}

package io.wisoft.capstonedesign.global.exception.service;

import io.wisoft.capstonedesign.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class NotExistTokenException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String message;

    public NotExistTokenException() {
        this.errorCode = ErrorCode.NOT_EXIST_TOKEN;
        this.message = ErrorCode.NOT_EXIST_TOKEN.getMessage();
    }
}

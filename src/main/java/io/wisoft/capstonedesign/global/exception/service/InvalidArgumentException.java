package io.wisoft.capstonedesign.global.exception.service;

import io.wisoft.capstonedesign.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class InvalidArgumentException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String message;

    public InvalidArgumentException() {
        this.errorCode = ErrorCode.INVALID_ARGUMENT;
        this.message = ErrorCode.INVALID_ARGUMENT.getMessage();
    }
}

package io.wisoft.capstonedesign.global.exception.service;

import io.wisoft.capstonedesign.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class UnAuthorizedAccessException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String message;

    public UnAuthorizedAccessException() {
        this.errorCode = ErrorCode.UNAUTHORIZED_ACCESS;
        this.message = ErrorCode.UNAUTHORIZED_ACCESS.getMessage();
    }
}

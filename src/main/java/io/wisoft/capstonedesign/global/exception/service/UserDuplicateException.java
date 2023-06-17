package io.wisoft.capstonedesign.global.exception.service;

import io.wisoft.capstonedesign.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class UserDuplicateException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String message;

    public UserDuplicateException() {
        this.errorCode = ErrorCode.DUPLICATE_USER;
        this.message = ErrorCode.DUPLICATE_USER.getMessage();
    }
}

package io.wisoft.capstonedesign.global.exception.service;

import io.wisoft.capstonedesign.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String message;

    public UserNotFoundException() {
        this.errorCode = ErrorCode.NOT_FOUND_ACCOUNT;
        this.message = ErrorCode.NOT_FOUND_ACCOUNT.getMessage();
    }
}

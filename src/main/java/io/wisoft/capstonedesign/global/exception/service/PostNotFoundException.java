package io.wisoft.capstonedesign.global.exception.service;

import io.wisoft.capstonedesign.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class PostNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String message;

    public PostNotFoundException() {
        this.errorCode = ErrorCode.NOT_FOUND_POST;
        this.message = ErrorCode.NOT_FOUND_POST.getMessage();
    }
}

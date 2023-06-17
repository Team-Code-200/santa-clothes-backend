package io.wisoft.capstonedesign.global.exception.service;

import io.wisoft.capstonedesign.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class OrderNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String message;

    public OrderNotFoundException() {
        this.errorCode = ErrorCode.NOT_FOUND_ORDER;
        this.message = ErrorCode.NOT_FOUND_ORDER.getMessage();
    }
}

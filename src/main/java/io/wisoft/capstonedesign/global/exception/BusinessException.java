package io.wisoft.capstonedesign.global.exception;

import lombok.Getter;

@Getter
public abstract class BusinessException extends RuntimeException {

    public BusinessException(final String message) {
        super(message);
    }

    public abstract ErrorCode getErrorCode();
}

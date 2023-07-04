package io.wisoft.capstonedesign.global.exception.service;

import io.wisoft.capstonedesign.global.exception.BusinessException;
import io.wisoft.capstonedesign.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class InvalidArgumentException extends BusinessException {

    public InvalidArgumentException() {
        super(ErrorCode.INVALID_ARGUMENT.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.INVALID_ARGUMENT;
    }
}

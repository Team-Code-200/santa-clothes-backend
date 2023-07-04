package io.wisoft.capstonedesign.global.exception.service;

import io.wisoft.capstonedesign.global.exception.BusinessException;
import io.wisoft.capstonedesign.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class NotExistTokenException extends BusinessException {

    public NotExistTokenException() {
        super(ErrorCode.NOT_EXIST_TOKEN.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.NOT_EXIST_TOKEN;
    }
}

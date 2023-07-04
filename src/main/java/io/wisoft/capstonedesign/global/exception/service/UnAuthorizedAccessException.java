package io.wisoft.capstonedesign.global.exception.service;

import io.wisoft.capstonedesign.global.exception.BusinessException;
import io.wisoft.capstonedesign.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class UnAuthorizedAccessException extends BusinessException {

    public UnAuthorizedAccessException() {
        super(ErrorCode.UNAUTHORIZED_ACCESS.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.UNAUTHORIZED_ACCESS;
    }
}

package io.wisoft.capstonedesign.global.exception.service;

import io.wisoft.capstonedesign.global.exception.BusinessException;
import io.wisoft.capstonedesign.global.exception.ErrorCode;

public class UnAuthorizedAccessException extends BusinessException {

    public UnAuthorizedAccessException(ErrorCode errorCode) {
        super(errorCode);
    }
}

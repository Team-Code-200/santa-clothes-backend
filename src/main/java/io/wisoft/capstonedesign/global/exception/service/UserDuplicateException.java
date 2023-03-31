package io.wisoft.capstonedesign.global.exception.service;

import io.wisoft.capstonedesign.global.exception.BusinessException;
import io.wisoft.capstonedesign.global.exception.ErrorCode;

public class UserDuplicateException extends BusinessException {

    public UserDuplicateException(ErrorCode errorCode) {
        super(errorCode);
    }
}

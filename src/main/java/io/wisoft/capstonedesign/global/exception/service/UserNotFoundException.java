package io.wisoft.capstonedesign.global.exception.service;

import io.wisoft.capstonedesign.global.exception.BusinessException;
import io.wisoft.capstonedesign.global.exception.ErrorCode;

public class UserNotFoundException extends BusinessException {

    public UserNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}

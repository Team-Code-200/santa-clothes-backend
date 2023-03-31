package io.wisoft.capstonedesign.global.exception.service;

import io.wisoft.capstonedesign.global.exception.BusinessException;
import io.wisoft.capstonedesign.global.exception.ErrorCode;

public class PostNotFoundException extends BusinessException {

    public PostNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}

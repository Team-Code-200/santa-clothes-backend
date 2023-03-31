package io.wisoft.capstonedesign.global.exception.service;

import io.wisoft.capstonedesign.global.exception.BusinessException;
import io.wisoft.capstonedesign.global.exception.ErrorCode;

public class OrderNotFoundException extends BusinessException {

    public OrderNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}

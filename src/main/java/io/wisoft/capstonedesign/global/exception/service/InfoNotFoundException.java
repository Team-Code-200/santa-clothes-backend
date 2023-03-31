package io.wisoft.capstonedesign.global.exception.service;

import io.wisoft.capstonedesign.global.exception.BusinessException;
import io.wisoft.capstonedesign.global.exception.ErrorCode;

public class InfoNotFoundException extends BusinessException {

    public InfoNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}

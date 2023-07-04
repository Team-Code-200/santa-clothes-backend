package io.wisoft.capstonedesign.global.exception.service;

import io.wisoft.capstonedesign.global.exception.BusinessException;
import io.wisoft.capstonedesign.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class InfoNotFoundException extends BusinessException {

    public InfoNotFoundException() {
        super(ErrorCode.NOT_FOUND_INFO.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.NOT_FOUND_INFO;
    }
}

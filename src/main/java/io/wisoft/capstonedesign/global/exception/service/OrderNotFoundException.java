package io.wisoft.capstonedesign.global.exception.service;

import io.wisoft.capstonedesign.global.exception.BusinessException;
import io.wisoft.capstonedesign.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class OrderNotFoundException extends BusinessException {

    public OrderNotFoundException() {
        super(ErrorCode.NOT_FOUND_ORDER.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.NOT_FOUND_ORDER;
    }
}

package io.wisoft.capstonedesign.global.exception.service;

import io.wisoft.capstonedesign.global.exception.BusinessException;
import io.wisoft.capstonedesign.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class UserNotFoundException extends BusinessException {

    public UserNotFoundException() {
        super(ErrorCode.NOT_FOUND_ACCOUNT.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.NOT_FOUND_ACCOUNT;
    }
}

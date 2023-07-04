package io.wisoft.capstonedesign.global.exception.service;

import io.wisoft.capstonedesign.global.exception.BusinessException;
import io.wisoft.capstonedesign.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class UserDuplicateException extends BusinessException {

    public UserDuplicateException() {
        super(ErrorCode.DUPLICATE_USER.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.DUPLICATE_USER;
    }
}

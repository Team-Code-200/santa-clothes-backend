package io.wisoft.capstonedesign.global.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponse {

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;

    @Builder
    public ErrorResponse(final ErrorCode errorCode) {
        this.httpStatus = errorCode.getHttpStatus();
        this.errorCode = errorCode.getCode();
        this.message = errorCode.getMessage();
    }
}

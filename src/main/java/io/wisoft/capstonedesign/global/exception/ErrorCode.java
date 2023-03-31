package io.wisoft.capstonedesign.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    NOT_FOUND_ACCOUNT("A001", "회원을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    DUPLICATE_USER("A002", "이미 존재하는 회원입니다.", HttpStatus.CONFLICT),
    NOT_FOUND_POST("P001", "게시물을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    NOT_FOUND_INFO("I001", "배송 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    NOT_FOUND_ORDER("O001", "주문 내역을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    UNAUTHORIZED_ACCESS("U001", "권한이 없습니다.", HttpStatus.FORBIDDEN);


    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}

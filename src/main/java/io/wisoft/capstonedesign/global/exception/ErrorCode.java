package io.wisoft.capstonedesign.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    // COMMON -> 가장 흔히 발생하는 예외
    NOT_FOUND_ACCOUNT("A001", "회원을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    NOT_FOUND_POST("P001", "게시물을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    NOT_FOUND_INFO("I001", "배송 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    NOT_FOUND_ORDER("O001", "주문 내역을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

    // TOKEN -> 토큰 관련 예외
    NOT_EXIST_TOKEN("T001", "토큰이 존재하지 않습니다.", HttpStatus.UNAUTHORIZED),

    // DUPLICATE -> 중복 예외
    DUPLICATE_USER("A002", "이미 존재하는 회원입니다.", HttpStatus.CONFLICT),

    // ILLEGAL -> 이상 접근, 서버 에러
    UNAUTHORIZED_ACCESS("U001", "권한이 없습니다.", HttpStatus.FORBIDDEN),
    INTERNAL_SERVER_ERROR("S001", "일시적으로 접속이 원활하지 않습니다.", HttpStatus.INTERNAL_SERVER_ERROR);


    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}

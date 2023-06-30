package io.wisoft.capstonedesign.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    // COMMON -> 가장 흔히 발생하는 예외
    NOT_FOUND_ACCOUNT("404", "회원을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    NOT_FOUND_POST("404", "게시물을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    NOT_FOUND_INFO("404", "배송 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    NOT_FOUND_ORDER("404", "주문 내역을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    INVALID_ARGUMENT("400", "필수로 요구되는 파라미터가 없습니다.", HttpStatus.BAD_REQUEST),

    // TOKEN -> 토큰 관련 예외
    NOT_EXIST_TOKEN("401", "토큰이 존재하지 않습니다.", HttpStatus.UNAUTHORIZED),

    // DUPLICATE -> 중복 예외
    DUPLICATE_USER("409", "이미 존재하는 회원입니다.", HttpStatus.CONFLICT),

    // ILLEGAL -> 이상 접근, 서버 에러
    UNAUTHORIZED_ACCESS("403", "권한이 없습니다.", HttpStatus.FORBIDDEN),
    INTERNAL_SERVER_ERROR("500", "일시적으로 접속이 원활하지 않습니다.", HttpStatus.INTERNAL_SERVER_ERROR);


    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}

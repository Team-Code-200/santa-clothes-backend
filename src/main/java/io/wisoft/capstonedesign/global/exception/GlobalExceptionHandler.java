package io.wisoft.capstonedesign.global.exception;

import io.wisoft.capstonedesign.global.exception.service.*;
import io.wisoft.capstonedesign.global.slack.SlackService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private final TaskExecutor executor;
    private final SlackService slackService;

    @Autowired
    public GlobalExceptionHandler(@Qualifier("taskExecutor") final TaskExecutor executor, final SlackService slackService) {
        this.executor = executor;
        this.slackService = slackService;
    }

    /**
     * 필수로 요구되는 파라미터 누락시 발생하는 예외
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(final HttpServletRequest request) {

        createLogAndSendAsync(new InvalidArgumentException(), request);
        return getErrorResponse(new InvalidArgumentException().getErrorCode());
    }

    /**
     * 회원 엔티티 부재시 발생하는 예외
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(final UserNotFoundException exception, final HttpServletRequest request) {

        createLogAndSendAsync(exception, request);
        return getErrorResponse(exception.getErrorCode());
    }

    /**
     * 게시물(산타샵 물품) 엔티티 부재시 발생하는 예외
     */
    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePostNotFoundException(final PostNotFoundException exception, final HttpServletRequest request) {

        createLogAndSendAsync(exception, request);
        return getErrorResponse(exception.getErrorCode());
    }

    /**
     * 배송 정보 엔티티 부재시 발생하는 예외
     */
    @ExceptionHandler(InfoNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleInfoNotFoundException(final InfoNotFoundException exception, final HttpServletRequest request) {

        createLogAndSendAsync(exception, request);
        return getErrorResponse(exception.getErrorCode());
    }

    /**
     * 주문 정보 엔티티 부재시 발생하는 예외
     */
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleOrderNotFoundException(final OrderNotFoundException exception, final HttpServletRequest request) {

        createLogAndSendAsync(exception, request);
        return getErrorResponse(exception.getErrorCode());
    }

    /**
     * 회원 중복시 발생하는 예외
     */
    @ExceptionHandler(UserDuplicateException.class)
    public ResponseEntity<ErrorResponse> handleUserDuplicateException(final UserDuplicateException exception, final HttpServletRequest request) {

        createLogAndSendAsync(exception, request);
        return getErrorResponse(exception.getErrorCode());
    }

    /**
     * 잘못된 접근시(권한 X) 발생하는 예외
     */
    @ExceptionHandler(UnAuthorizedAccessException.class)
    public ResponseEntity<ErrorResponse> handleUnAuthorizedAccessException(final UnAuthorizedAccessException exception, final HttpServletRequest request) {

        createLogAndSendAsync(exception, request);
        return getErrorResponse(exception.getErrorCode());
    }

    /**
     * 로그인이 필요한 기능에 토큰 부재시 발생하는 예외
     */
    @ExceptionHandler(NotExistTokenException.class)
    public ResponseEntity<ErrorResponse> handleNotExistTokenException(final NotExistTokenException exception, final HttpServletRequest request) {

        createLogAndSendAsync(exception, request);
        return getErrorResponse(exception.getErrorCode());
    }

    /**
     * 콘솔 에러 로그 생성 및 스레드풀을 사용하여 예외 메시지를 비동기로 Slack에 전달
     */
    private void createLogAndSendAsync(final Exception exception, final HttpServletRequest request) {

        log.error("Exception:{}, {}, {} \n",
                exception.toString(),
                request.getRequestURI(),
                request.getMethod()
        );

        executor.execute(() -> slackService.sendErrorLog(exception, request));
    }

    private ResponseEntity<ErrorResponse> getErrorResponse(final ErrorCode errorCode) {
        ErrorResponse response = new ErrorResponse(errorCode);
        return new ResponseEntity<>(response, errorCode.getHttpStatus());
    }
}

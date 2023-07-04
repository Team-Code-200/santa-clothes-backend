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
     * 비즈니스 요구사항에서 발생하는 모든 예외
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(final BusinessException exception, final HttpServletRequest request) {

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

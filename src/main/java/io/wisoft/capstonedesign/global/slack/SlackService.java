package io.wisoft.capstonedesign.global.slack;

import com.slack.api.Slack;
import com.slack.api.model.Attachment;
import com.slack.api.model.Field;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.slack.api.webhook.WebhookPayloads.payload;

@Slf4j
@Service
public class SlackService {

    @Value("${slack.webhook.token}")
    private String webhookUrl;

    private final Slack slack = Slack.getInstance();

    public void sendErrorLog(final Exception e, final HttpServletRequest request) {

        try {
            slack.send(webhookUrl, payload(p -> p
                    .text("서버 에러 발생 ⚠️")
                    .attachments(List.of(generateAttachment(e, request)))));
        } catch (IOException slackError) {
            log.error("Slack과의 통신 예외 발생");
        }
    }

    public Attachment generateAttachment(final Exception e, final HttpServletRequest request) {

        String requestTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").format(LocalDateTime.now());
        String header = request.getHeader("X-FORWARDED-FOR");

        return Attachment.builder()
                .color("ff0000")
                .title(requestTime + " 발생 에러 로그")
                .fields(List.of(
                        generateField("Request IP", header == null ? request.getRemoteAddr() : header),
                        generateField("Request URL", request.getRequestURL() + " " + request.getMethod()),
                        generateField("Error Message", e.getMessage())))
                .build();
    }

    public Field generateField(final String title, final String value) {

        return Field.builder()
                .title(title)
                .value(value)
                .valueShortEnough(false)
                .build();
    }
}

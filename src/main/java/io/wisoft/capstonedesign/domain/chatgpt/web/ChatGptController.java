package io.wisoft.capstonedesign.domain.chatgpt.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.wisoft.capstonedesign.domain.chatgpt.application.ChatGptService;
import io.wisoft.capstonedesign.domain.chatgpt.web.dto.ChatGptRequest;
import io.wisoft.capstonedesign.domain.chatgpt.web.dto.ChatGptResponse;
import io.wisoft.capstonedesign.global.annotation.SwaggerApiNotFoundError;
import io.wisoft.capstonedesign.global.annotation.SwaggerApiSuccess;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "ChatGPT", description = "ChatGPT 검색 API")
@RestController
@RequiredArgsConstructor
public class ChatGptController {

    private final ChatGptService chatGptService;

    @SwaggerApiSuccess(summary = "ChatGPT 검색", implementation = ChatGptResponse.class)
    @SwaggerApiNotFoundError
    @GetMapping("/api/make/conversation")
    public ChatGptResponse makeConversation(@RequestBody @Valid final ChatGptRequest chatGptRequest) {

        final ChatGptResponse chatGptResponse = chatGptService.getConversation(chatGptRequest);

        return chatGptResponse;
    }
}

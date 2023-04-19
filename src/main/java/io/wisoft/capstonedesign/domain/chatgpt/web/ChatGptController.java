package io.wisoft.capstonedesign.domain.chatgpt.web;

import io.wisoft.capstonedesign.domain.chatgpt.application.ChatGptService;
import io.wisoft.capstonedesign.domain.chatgpt.web.dto.ChatGptRequest;
import io.wisoft.capstonedesign.domain.chatgpt.web.dto.ChatGptResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatGptController {

    private final ChatGptService chatGptService;

    @GetMapping("/gptAPI/make/conversation")
    public ChatGptResponse makeConversation(@RequestBody @Valid ChatGptRequest chatGptRequest) {

        ChatGptResponse chatGptResponse = chatGptService.getConversation(chatGptRequest);

        return chatGptResponse;
    }
}

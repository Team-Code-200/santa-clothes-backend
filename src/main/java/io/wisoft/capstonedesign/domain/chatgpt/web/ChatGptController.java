package io.wisoft.capstonedesign.domain.chatgpt.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.wisoft.capstonedesign.domain.chatgpt.application.ChatGptService;
import io.wisoft.capstonedesign.domain.chatgpt.web.dto.ChatGptRequest;
import io.wisoft.capstonedesign.domain.chatgpt.web.dto.ChatGptResponse;
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

    @Operation(summary = "ChatGPT 검색 사용")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = ChatGptResponse.class))),
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/api/make/conversation")
    public ChatGptResponse makeConversation(@RequestBody @Valid ChatGptRequest chatGptRequest) {

        ChatGptResponse chatGptResponse = chatGptService.getConversation(chatGptRequest);

        return chatGptResponse;
    }
}

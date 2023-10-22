package io.wisoft.capstonedesign.domain.chatgpt.application;

import io.wisoft.capstonedesign.domain.chatgpt.web.dto.ChatGptRequest;
import io.wisoft.capstonedesign.domain.chatgpt.web.dto.ChatGptResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChatGptService {

    @Value("${chatgpt.secret_key}")
    private String secretKey;

    @Value("${chatgpt.end_point}")
    private String endPoint;

    public String generateText(final String prompt, final float temperature, final int maxTokens) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(secretKey);

        final Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "text-davinci-003");
        requestBody.put("prompt", prompt);
        requestBody.put("temperature", temperature);
        requestBody.put("max_tokens", maxTokens);

        final HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        final RestTemplate restTemplate = new RestTemplate();
        final ResponseEntity<Map> response = restTemplate.postForEntity(endPoint, requestEntity, Map.class);
        final Map<String, Object> responseBody = response.getBody();
        System.out.println(responseBody.toString());

        final List<Map<String, Object>> choicesList = (List<Map<String, Object>>) responseBody.get("choices");
        final Map<String, Object> choiceMap = choicesList.get(0);
        final String answer = (String) choiceMap.get("text");

        return answer;
    }

    public ChatGptResponse getConversation(final ChatGptRequest chatGptRequest) {

        final ChatGptResponse chatGptResponse = new ChatGptResponse();

        final String answer = generateText(chatGptRequest.question(), 0.5f, 1000);

        final String answerFilter = answer.replaceAll("\n", "");
        String result = answerFilter.replaceAll("\\.", "");
        result = result.replaceAll("\\\\", "");
        result = result.replaceAll("\"", "");

        chatGptResponse.setResponse(result);

        return chatGptResponse;
    }
}

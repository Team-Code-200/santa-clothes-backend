package io.wisoft.capstonedesign.domain.chat.persistence;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatMessageRepository {

    private static final String CHAT_MESSAGE = "CHAT_MESSAGE";

    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, ChatMessage> opsHashMessage;

    @PostConstruct
    private void init() {
        opsHashMessage = redisTemplate.opsForHash();
    }

    public ChatMessage saveMessage(final ChatMessage message) {

        opsHashMessage.put(CHAT_MESSAGE, message.getSender(), message);
        return message;
    }

    public List<ChatMessage> getMessages() {

        return opsHashMessage.values(CHAT_MESSAGE);
    }
}

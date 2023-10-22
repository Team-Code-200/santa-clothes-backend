package io.wisoft.capstonedesign.domain.chat.web;

import io.wisoft.capstonedesign.domain.chat.persistence.ChatMessage;
import io.wisoft.capstonedesign.domain.chat.persistence.ChatMessageRepository;
import io.wisoft.capstonedesign.domain.chat.persistence.ChatRoomRepository;
import io.wisoft.capstonedesign.global.redis.RedisPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final RedisPublisher redisPublisher;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    /**
     * WebSocket : "/pub/chat/message"로 들어오는 메시징을 처리
     */
    @MessageMapping("/chat/message")
    public void message(final ChatMessage message) {

        if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
            chatRoomRepository.enterChatRoom(message.getRoomId());
            message.setMessage(message.getSender() + "님이 입장하셨습니다.");
        }

        // WebSocket에 발행된 메시지를 Redis로 발행(Publish)
        redisPublisher.publish(chatRoomRepository.getTopic(message.getRoomId()), message);

        chatMessageRepository.saveMessage(message);
    }

    @ResponseBody
    @GetMapping("/chat/messages")
    public List<ChatMessage> getChatMessages() {
        return chatMessageRepository.getMessages();
    }
}

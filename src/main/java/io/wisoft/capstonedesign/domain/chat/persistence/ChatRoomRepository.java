package io.wisoft.capstonedesign.domain.chat.persistence;

import io.wisoft.capstonedesign.global.redis.RedisSubscriber;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepository {

    private static final String CHAT_ROOM = "CHAT_ROOM";

    private final RedisMessageListenerContainer redisMessageListener;
    private final RedisSubscriber redisSubscriber;
    private final RedisTemplate<String, Object> redisTemplate;

    private HashOperations<String, String, ChatRoom> opsHashChatRoom;
    private Map<String, ChannelTopic> topicMap;

    @PostConstruct
    private void init() {
        opsHashChatRoom = redisTemplate.opsForHash();
        topicMap = new HashMap<>();
    }

    public List<ChatRoom> findAllRoom() {
        return opsHashChatRoom.values(CHAT_ROOM);
    }

    public ChatRoom findRoomById(String id) {
        return opsHashChatRoom.get(CHAT_ROOM, id);
    }

    /**
     * 채팅방 생성 : 서버 간 채팅방 공유 및 저장을 위해 redis hash에 저장
     */
    public ChatRoom createChatRoom(String name) {

        ChatRoom chatRoom = ChatRoom.create(name);
        opsHashChatRoom.put(CHAT_ROOM, chatRoom.getRoomId(), chatRoom);

        return chatRoom;
    }

    /**
     * 채팅방 입장 : redis에 topic을 만들고, pub/sub 통신을 하기 위한 리스너 설정
     */
    public void enterChatRoom(String roomId) {

        ChannelTopic topic = topicMap.get(roomId);

        if (topic == null) {
            topic = new ChannelTopic(roomId);
        }

        redisMessageListener.addMessageListener(redisSubscriber, topic);
        topicMap.put(roomId, topic);
    }

    public ChannelTopic getTopic(String roomId) {
        return topicMap.get(roomId);
    }
}

package io.wisoft.capstonedesign.domain.chat.persistence;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter @Setter
public class ChatRoom implements Serializable {

    private String roomId;
    private String name;

    public static ChatRoom create(final String name) {
        final ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.name = name;
        return chatRoom;
    }
}

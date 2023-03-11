package io.wisoft.capstonedesign.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

import static jakarta.persistence.FetchType.*;
import static lombok.AccessLevel.*;

@Entity
@Table(name = "message")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Message {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdDate;

    @Column(name = "body", nullable = false, columnDefinition = "TEXT")
    private String body;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat;

    /**
     * 정적 생성자 메소드
     */
    public static Message createMessage(
            LocalDateTime createdDate,
            String body,
            User sender,
            Chat chat
    ) {
        Message message = new Message();
        message.createdDate = createdDate;
        message.body = body;
        message.setSender(sender);
        message.setChat(chat);
        return message;
    }

    /**
     * 연관관계 편의 메소드
     */
    public void setSender(User sender) {

        if (this.sender != null) this.sender.getMessages().remove(this);
        this.sender = sender;
        sender.getMessages().add(this);
    }

    public void setChat(Chat chat) {

        if (this.chat != null) this.chat.getMessages().remove(this);
        this.chat = chat;
        chat.getMessages().add(this);
    }
}

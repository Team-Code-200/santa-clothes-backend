package io.wisoft.capstonedesign.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;

@Entity
@Table(name = "message")
@Getter
public class Message {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "body", columnDefinition = "TEXT")
    private String body;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    public void setSender(User sender) { // 연관관계 편의 메소드
        this.sender = sender;
        sender.getMessages().add(this);
    }

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    public void setChat(Chat chat) { // 연관관계 편의 메소드
        this.chat = chat;
        chat.getMessages().add(this);
    }
}

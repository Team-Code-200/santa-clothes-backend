package io.wisoft.capstonedesign.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "user_chat")
@Getter
public class UserChat {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void setUser(User user) { // 연관관계 편의 메소드
        this.user = user;
        user.getUserChats().add(this);
    }

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    public void setChat(Chat chat) { // 연관관계 편의 메소드
        this.chat = chat;
        chat.getUserChats().add(this);
    }
}

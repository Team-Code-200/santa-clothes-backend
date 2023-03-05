package io.wisoft.capstonedesign.domain;

import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.FetchType.*;

@Entity
@Table(name = "user_chat")
@Getter
public class UserChat {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat;

    /**
     * 연관관계 편의 메소드
     */
    public void setUser(User user) {

        if (this.user != null) this.user.getUserChats().remove(this);
        this.user = user;
        user.getUserChats().add(this);
    }

    public void setChat(Chat chat) {

        if (this.chat != null) this.chat.getUserChats().remove(this);
        this.chat = chat;
        chat.getUserChats().add(this);
    }
}

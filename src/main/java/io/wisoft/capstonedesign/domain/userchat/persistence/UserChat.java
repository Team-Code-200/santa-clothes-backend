package io.wisoft.capstonedesign.domain.userchat.persistence;

import io.wisoft.capstonedesign.domain.chat.persistence.Chat;
import io.wisoft.capstonedesign.domain.user.persistence.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.*;
import static lombok.AccessLevel.*;

@Entity
@Table(name = "user_chat")
@NoArgsConstructor(access = PROTECTED)
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
     * 정적 생성자 메소드
     */
    public static UserChat createUserChat(
            final User user,
            final Chat chat
    ) {
        UserChat userChat = new UserChat();
        userChat.setUser(user);
        userChat.setChat(chat);
        return userChat;
    }

    /**
     * 연관관계 편의 메소드
     */
    public void setUser(final User user) {

        if (this.user != null) this.user.getUserChats().remove(this);
        this.user = user;
        user.getUserChats().add(this);
    }

    public void setChat(final Chat chat) {

        if (this.chat != null) this.chat.getUserChats().remove(this);
        this.chat = chat;
        chat.getUserChats().add(this);
    }
}

package io.wisoft.capstonedesign.domain.chat.persistence;

import io.wisoft.capstonedesign.domain.message.persistence.Message;
import io.wisoft.capstonedesign.domain.userchat.persistence.UserChat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static lombok.AccessLevel.*;

@Entity
@Table(name = "CHAT")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Chat {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdDate;

    @OneToMany(mappedBy = "chat", cascade = REMOVE)
    private List<Message> messages = new ArrayList<>();

    @OneToMany(mappedBy = "chat", cascade = REMOVE)
    private List<UserChat> userChats = new ArrayList<>();

    /**
     * 정적 생성자 메소드
     */
    public static Chat createChat(final LocalDateTime createdDate) {
        Chat chat = new Chat();
        chat.createdDate = createdDate;
        return chat;
    }
}

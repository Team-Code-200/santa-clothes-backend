package io.wisoft.capstonedesign.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private Date createdDate;

    @OneToMany(mappedBy = "chat")
    private List<Message> messages = new ArrayList<>();

    @OneToMany(mappedBy = "chat")
    private List<UserChat> userChats = new ArrayList<>();

    /**
     * 정적 생성자 메소드
     */
    public static Chat createChat(Date createdDate) {
        Chat chat = new Chat();
        chat.createdDate = createdDate;
        return chat;
    }
}

package io.wisoft.capstonedesign.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_chat")
@Getter @Setter
public class UserChat {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "chat_id")
    private Long chatId;
}

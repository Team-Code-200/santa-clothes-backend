package io.wisoft.capstonedesign.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "message")
@Getter @Setter
public class Message {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "sender_id")
    private Long senderId;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
}

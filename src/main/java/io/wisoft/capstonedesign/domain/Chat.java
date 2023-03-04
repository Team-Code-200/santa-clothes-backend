package io.wisoft.capstonedesign.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "CHAT")
@Getter
public class Chat {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @OneToMany(mappedBy = "chat")
    private List<Message> messages = new ArrayList<>();

    @OneToMany(mappedBy = "chat")
    private List<UserChat> userChats = new ArrayList<>();
}

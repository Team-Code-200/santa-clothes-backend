package io.wisoft.capstonedesign.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "FIND")
@Getter @Setter
public class Find {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "title")
    private String title;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "body", columnDefinition = "TEXT")
    private String text;

    @Column(name = "image")
    private String image;

    @Column(name = "view")
    private int view;
}

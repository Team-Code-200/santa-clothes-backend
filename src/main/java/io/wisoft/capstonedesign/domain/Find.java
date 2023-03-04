package io.wisoft.capstonedesign.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "FIND")
@Getter
public class Find {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void setUser(User user) { // 연관관계 편의 메소드
        this.user = user;
        user.getFinds().add(this);
    }

    @OneToMany(mappedBy = "find")
    private List<FindTag> findTags = new ArrayList<>();
}

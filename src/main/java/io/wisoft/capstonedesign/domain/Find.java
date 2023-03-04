package io.wisoft.capstonedesign.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static jakarta.persistence.FetchType.*;

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

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "find")
    private List<FindTag> findTags = new ArrayList<>();

    /**
     * 연관관계 편의 메소드
     */
    public void setUser(User user) {

        if (this.user != null) this.user.getFinds().remove(this);
        this.user = user;
        user.getFinds().add(this);
    }
}

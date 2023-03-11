package io.wisoft.capstonedesign.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static jakarta.persistence.FetchType.*;
import static lombok.AccessLevel.*;

@Entity
@Table(name = "FIND")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Find {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdDate;

    @Column(name = "body", nullable = false, columnDefinition = "TEXT")
    private String text;

    @Column(name = "image")
    private String image;

    @Column(name = "view", nullable = false, columnDefinition = "INTEGER default 0")
    private int view;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "find")
    private List<FindTag> findTags = new ArrayList<>();

    /**
     * 정적 생성자 메소드
     */
    public static Find createFind(
            String title,
            LocalDateTime createdDate,
            String text,
            String image,
            int view,
            User user
    ) {
        Find find = new Find();
        find.title = title;
        find.createdDate = createdDate;
        find.text = text;
        find.image = image;
        find.view = view;
        find.setUser(user);
        return find;
    }

    /**
     * 연관관계 편의 메소드
     */
    public void setUser(User user) {

        if (this.user != null) this.user.getFinds().remove(this);
        this.user = user;
        user.getFinds().add(this);
    }
}

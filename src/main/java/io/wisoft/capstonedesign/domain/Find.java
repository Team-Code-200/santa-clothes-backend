package io.wisoft.capstonedesign.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.*;
import static lombok.AccessLevel.*;

@Entity
@Table(name = "FIND")
@NoArgsConstructor(access = PROTECTED)
@Getter @Setter
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

    @Column(name = "tag", nullable = false)
    @Enumerated(EnumType.STRING)
    private Tag tag;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(mappedBy = "find")
    private FindOrder findOrder;

    /**
     * 정적 생성자 메소드
     */
    public static Find createFind(
            String title,
            String text,
            String image,
            int view,
            Tag tag,
            User user
    ) {
        Find find = new Find();
        find.title = title;
        find.createdDate = LocalDateTime.now();
        find.text = text;
        find.image = image;
        find.view = view;
        find.tag = tag;
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

    /**
     * 게시글 제목, 본문 및 태그 수정
     */
    public void update(String title, String image, String text, Tag tag) {
        this.title = title;
        this.image = image;
        this.text = text;
        this.tag = tag;
        this.createdDate = LocalDateTime.now();
        view += 1;
    }
}

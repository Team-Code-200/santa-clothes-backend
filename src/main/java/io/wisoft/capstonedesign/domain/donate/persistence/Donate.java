package io.wisoft.capstonedesign.domain.donate.persistence;

import io.wisoft.capstonedesign.domain.donateorder.persistence.DonateOrder;
import io.wisoft.capstonedesign.global.enumerated.Tag;
import io.wisoft.capstonedesign.domain.user.persistence.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.*;
import static lombok.AccessLevel.*;

@Entity
@Table(name = "DONATE")
@NoArgsConstructor(access = PROTECTED)
@Getter @Setter
public class Donate {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdDate;

    @Column(name = "body", columnDefinition = "TEXT", nullable = false)
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

    @OneToOne(mappedBy = "donate", fetch = LAZY, cascade = REMOVE)
    private DonateOrder donateOrder;

    /**
     * 정적 생성자 메소드
     */
    public static Donate createDonate(
            String title,
            String image,
            String text,
            int view,
            Tag tag,
            User user
    ) {
        Donate donate = new Donate();
        donate.title = title;
        donate.createdDate = LocalDateTime.now();
        donate.text = text;
        donate.image = image;
        donate.view = view;
        donate.tag = tag;
        donate.setUser(user);
        return donate;
    }

    /**
     * 연관관계 편의 메소드
     */
    public void setUser(User user) {

        if (this.user != null) this.user.getDonates().remove(this);
        this.user = user;
        user.getDonates().add(this);
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

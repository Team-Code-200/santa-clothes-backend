package io.wisoft.capstonedesign.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "DONATE_TAG")
@Getter
public class DonateTag {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "donate_id")
    private Donate donate;

    public void setDonate(Donate donate) { // 연관관계 편의 메소드
        this.donate = donate;
        donate.getDonateTags().add(this);
    }

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    public void setTag(Tag tag) { // 연관관계 편의 메소드
        this.tag = tag;
        tag.getDonateTags().add(this);
    }
}

package io.wisoft.capstonedesign.domain;

import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.FetchType.*;

@Entity
@Table(name = "DONATE_TAG")
@Getter
public class DonateTag {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "donate_id")
    private Donate donate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    /**
     * 연관관계 편의 메소드
     */
    public void setDonate(Donate donate) {

        if (this.donate != null) this.donate.getDonateTags().remove(this);
        this.donate = donate;
        donate.getDonateTags().add(this);
    }

    public void setTag(Tag tag) {

        if (this.tag != null) this.tag.getDonateTags().remove(this);
        this.tag = tag;
        tag.getDonateTags().add(this);
    }
}

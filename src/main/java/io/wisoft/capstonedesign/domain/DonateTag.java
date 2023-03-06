package io.wisoft.capstonedesign.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.*;
import static lombok.AccessLevel.*;

@Entity
@Table(name = "DONATE_TAG")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class DonateTag {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "donate_id", nullable = false)
    private Donate donate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;

    /**
     * 정적 생성자 메소드
     */
    public static DonateTag createDonateTag(
            Donate donate,
            Tag tag
    ) {
        DonateTag donateTag = new DonateTag();
        donateTag.donate = donate;
        donateTag.tag = tag;
        return donateTag;
    }

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

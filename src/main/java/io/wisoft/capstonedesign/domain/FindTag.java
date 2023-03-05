package io.wisoft.capstonedesign.domain;

import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.FetchType.*;

@Entity
@Table(name = "FIND_TAG")
@Getter
public class FindTag {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "find_id", nullable = false)
    private Find find;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;

    /**
     * 연관관계 편의 메소드
     */
    public void setFind(Find find) {

        if (this.find != null) this.find.getFindTags().remove(this);
        this.find = find;
        find.getFindTags().add(this);
    }

    public void setTag(Tag tag) {

        if (this.tag != null) this.tag.getFindTags().remove(this);
        this.tag = tag;
        tag.getFindTags().add(this);
    }
}

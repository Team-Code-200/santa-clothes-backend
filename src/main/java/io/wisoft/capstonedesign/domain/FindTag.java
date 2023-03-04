package io.wisoft.capstonedesign.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "FIND_TAG")
@Getter
public class FindTag {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "find_id")
    private Find find;

    public void setFind(Find find) { // 연관관계 편의 메소드
        this.find = find;
        find.getFindTags().add(this);
    }

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    public void setTag(Tag tag) { // 연관관계 편의 메소드
        this.tag = tag;
        tag.getFindTags().add(this);
    }
}

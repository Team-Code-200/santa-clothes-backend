package io.wisoft.capstonedesign.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.*;

@Entity
@Table(name = "TAG")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Tag {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "tag")
    private List<FindTag> findTags = new ArrayList<>();

    @OneToMany(mappedBy = "tag")
    private List<DonateTag> donateTags = new ArrayList<>();

    /**
     * 정적 생성자 메소드
     */
    public static Tag createTag(String name) {
        Tag tag = new Tag();
        tag.name = name;
        return tag;
    }
}

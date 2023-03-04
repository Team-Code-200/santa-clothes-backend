package io.wisoft.capstonedesign.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TAG")
@Getter
public class Tag {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "tag")
    private List<FindTag> findTags = new ArrayList<>();

    @OneToMany(mappedBy = "tag")
    private List<DonateTag> donateTags = new ArrayList<>();
}

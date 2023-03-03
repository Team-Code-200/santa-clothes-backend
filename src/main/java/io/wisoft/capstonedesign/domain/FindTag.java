package io.wisoft.capstonedesign.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "FIND_TAG")
@Getter @Setter
public class FindTag {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "find_id")
    private Long findId;

    @Column(name = "tag_id")
    private Long tagId;
}

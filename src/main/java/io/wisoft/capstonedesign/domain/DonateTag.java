package io.wisoft.capstonedesign.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "DONATE_TAG")
@Getter @Setter
public class DonateTag {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "donate_id")
    private Long donateId;

    @Column(name = "tag_id")
    private Long tagId;
}

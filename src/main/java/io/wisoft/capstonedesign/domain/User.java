package io.wisoft.capstonedesign.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "USERS")
@Getter @Setter
public class User {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "oauth_id")
    private String oauthId;

    @Column(name = "email")
    private String email;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "point")
    private int point;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "users_role")
    @Enumerated(EnumType.STRING)
    private Role userRole;
}

package io.wisoft.capstonedesign.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static lombok.AccessLevel.*;

@Entity
@Table(name = "USERS", uniqueConstraints = {@UniqueConstraint(
        name = "OAUTH_EMAIL_UNIQUE",
        columnNames = {"oauth_id", "email"})})
@NoArgsConstructor(access = PROTECTED)
@Getter @Setter
public class User {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "oauth_id", nullable = false)
    private String oauthId;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "profile_image", nullable = false)
    private String profileImage;

    @Column(name = "point", nullable = false, columnDefinition = "INTEGER default 0")
    private int point;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "users_role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role userRole;

    @OneToMany(mappedBy = "user")
    private List<Donate> donates = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Find> finds = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "sender")
    private List<Message> messages = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<UserShop> userShops = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<UserChat> userChats = new ArrayList<>();

    /**
     * 정적 생성자 메소드
     */
    public static User newInstance(
            String oauthId,
            String email,
            String profileImage,
            int point,
            String nickname,
            Date createdDate,
            Role userRole
    ) {
        User user = new User();
        user.oauthId = oauthId;
        user.email = email;
        user.profileImage = profileImage;
        user.point = point;
        user.nickname = nickname;
        user.createdDate = createdDate;
        user.userRole = userRole;
        return user;
    }
}

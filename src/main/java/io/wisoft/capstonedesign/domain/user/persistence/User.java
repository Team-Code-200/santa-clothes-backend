package io.wisoft.capstonedesign.domain.user.persistence;

import io.wisoft.capstonedesign.domain.donate.persistence.Donate;
import io.wisoft.capstonedesign.domain.donateorder.persistence.DonateOrder;
import io.wisoft.capstonedesign.domain.find.persistence.Find;
import io.wisoft.capstonedesign.domain.findorder.persistence.FindOrder;
import io.wisoft.capstonedesign.domain.information.persistence.Information;
import io.wisoft.capstonedesign.domain.message.persistence.Message;
import io.wisoft.capstonedesign.domain.userchat.persistence.UserChat;
import io.wisoft.capstonedesign.domain.usershop.persistence.UserShop;
import io.wisoft.capstonedesign.global.enumerated.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
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
    private LocalDateTime createdDate;

    @Column(name = "users_role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role userRole;

    @OneToMany(mappedBy = "user", cascade = REMOVE)
    private List<Donate> donates = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = REMOVE)
    private List<Find> finds = new ArrayList<>();

    @OneToMany(mappedBy = "sender", cascade = REMOVE)
    private List<Message> messages = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = REMOVE)
    private List<UserShop> userShops = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = REMOVE)
    private List<UserChat> userChats = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = REMOVE)
    private List<Information> information = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = REMOVE)
    private List<FindOrder> findOrders = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = REMOVE)
    private List<DonateOrder> donateOrders = new ArrayList<>();

    /**
     * 정적 생성자 메소드
     */
    public static User newInstance(
            String oauthId,
            String email,
            String profileImage,
            int point,
            String nickname,
            Role userRole
    ) {
        User user = new User();
        user.oauthId = oauthId;
        user.email = email;
        user.profileImage = profileImage;
        user.point = point;
        user.nickname = nickname;
        user.createdDate = LocalDateTime.now();
        user.userRole = userRole;
        return user;
    }

    /**
     * 닉네임 수정
     */
    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }
}

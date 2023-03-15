package io.wisoft.capstonedesign.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.*;
import static lombok.AccessLevel.*;

@Entity
@Table(name = "USER_SHOP")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class UserShop {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "info_id", nullable = false)
    private Information information;

    /**
     * 정적 생성자 메소드
     */
    public static UserShop createUserShop(User user, Shop shop) {
        UserShop userShop = new UserShop();
        userShop.setUser(user);
        userShop.setShop(shop);
        return userShop;
    }

    /**
     * 연관관계 편의 메소드
     */
    public void setUser(User user) {

        if (this.user != null) this.user.getUserShops().remove(this);
        this.user = user;
        user.getUserShops().add(this);
    }

    public void setShop(Shop shop) {

        if (this.shop != null) this.shop.getUserShops().remove(this);
        this.shop = shop;
        shop.getUserShops().add(this);
    }
}

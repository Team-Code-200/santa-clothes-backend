package io.wisoft.capstonedesign.domain;

import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.FetchType.*;

@Entity
@Table(name = "USER_SHOP")
@Getter
public class UserShop {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

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

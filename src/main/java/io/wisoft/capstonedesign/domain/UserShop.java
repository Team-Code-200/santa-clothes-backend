package io.wisoft.capstonedesign.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "USER_SHOP")
@Getter
public class UserShop {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void setUser(User user) { // 연관관계 편의 메소드
        this.user = user;
        user.getUserShops().add(this);
    }

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    public void setShop(Shop shop) { // 연관관계 편의 메소드
        this.shop = shop;
        shop.getUserShops().add(this);
    }
}

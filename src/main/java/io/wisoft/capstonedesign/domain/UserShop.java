package io.wisoft.capstonedesign.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "USER_SHOP")
@Getter @Setter
public class UserShop {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "shop_id")
    private Long shopId;
}

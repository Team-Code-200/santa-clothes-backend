package io.wisoft.capstonedesign.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.*;

@Entity
@Table(name = "SHOP")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Shop {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "body")
    private String body;

    @OneToMany(mappedBy = "shop")
    private List<UserShop> userShops = new ArrayList<>();

    /**
     * 정적 생성자 메소드
     */
    public static Shop createShop(
            String title,
            int price,
            String image,
            String body
    ) {
        Shop shop = new Shop();
        shop.title = title;
        shop.price = price;
        shop.image = image;
        shop.body = body;
        return shop;
    }
}

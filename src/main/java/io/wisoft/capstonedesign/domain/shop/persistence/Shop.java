package io.wisoft.capstonedesign.domain.shop.persistence;

import io.wisoft.capstonedesign.domain.usershop.persistence.UserShop;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
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

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdDate;

    @OneToMany(mappedBy = "shop", cascade = REMOVE)
    private List<UserShop> userShops = new ArrayList<>();

    /**
     * 정적 생성자 메소드
     */
    public static Shop createShop(
            final String title,
            final int price,
            final String image,
            final String body
    ) {
        Shop shop = new Shop();
        shop.title = title;
        shop.price = price;
        shop.image = image;
        shop.body = body;
        shop.createdDate = LocalDateTime.now();
        return shop;
    }

    /**
     * 산타샵 물품 정보 전체 수정
     */
    public void update(final String title, final int price, final String image, final String body) {
        this.title = title;
        this.price = price;
        this.image = image;
        this.body = body;
    }
}

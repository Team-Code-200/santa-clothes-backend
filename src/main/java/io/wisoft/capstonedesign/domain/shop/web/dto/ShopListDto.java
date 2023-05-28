package io.wisoft.capstonedesign.domain.shop.web.dto;

import io.wisoft.capstonedesign.domain.shop.persistence.Shop;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShopListDto {

    private String title;

    private int price;

    private String image;

    private LocalDateTime createdDate;

    public ShopListDto(Shop shop) {
        this.title = shop.getTitle();
        this.price = shop.getPrice();
        this.image = shop.getImage();
        this.createdDate = shop.getCreatedDate();
    }
}

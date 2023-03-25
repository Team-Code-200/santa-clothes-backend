package io.wisoft.capstonedesign.domain.shop.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShopDto {

    private String title;

    private int price;

    private String image;

    private String body;
}

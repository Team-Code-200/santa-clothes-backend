package io.wisoft.capstonedesign.domain.shop.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateShopResponse {

    private Long id;

    private String title;

    private int price;

    private String image;

    private String body;
}

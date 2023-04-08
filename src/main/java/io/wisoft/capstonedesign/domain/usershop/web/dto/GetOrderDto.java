package io.wisoft.capstonedesign.domain.usershop.web.dto;

import io.wisoft.capstonedesign.domain.usershop.persistence.UserShop;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class GetOrderDto {

    private Long id;

    private LocalDateTime createdDate;

    private String text;

    private String address;

    private String title;

    private String nickname;

    public GetOrderDto(UserShop userShop) {
        this.id = userShop.getId();
        this.createdDate = userShop.getCreatedDate();
        this.text = userShop.getText();
        this.address = userShop.getInformation().getAddress();
        this.title = userShop.getShop().getTitle();
        this.nickname = userShop.getUser().getNickname();
    }
}

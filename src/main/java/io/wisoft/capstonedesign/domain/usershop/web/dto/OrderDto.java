package io.wisoft.capstonedesign.domain.usershop.web.dto;

import io.wisoft.capstonedesign.domain.usershop.persistence.UserShop;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderDto {

    private String text;

    private String nickname;

    private String phoneNumber;

    private String title;

    public OrderDto(UserShop userShop) {
        this.text = userShop.getText();
        this.nickname = userShop.getUser().getNickname();
        this.phoneNumber = userShop.getInformation().getPhoneNumber();
        this.title = userShop.getShop().getTitle();
    }
}

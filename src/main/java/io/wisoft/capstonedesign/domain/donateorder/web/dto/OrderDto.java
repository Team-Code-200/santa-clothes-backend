package io.wisoft.capstonedesign.domain.donateorder.web.dto;

import io.wisoft.capstonedesign.domain.donateorder.persistence.DonateOrder;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderDto {

    private String text;

    private String nickname;

    private String phoneNumber;

    private String title;

    public OrderDto(DonateOrder donateOrder) {
        this.text = donateOrder.getText();
        this.nickname = donateOrder.getUser().getNickname();
        this.phoneNumber = donateOrder.getInformation().getPhoneNumber();
        this.title = donateOrder.getDonate().getTitle();
    }
}

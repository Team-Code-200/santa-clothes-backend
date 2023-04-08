package io.wisoft.capstonedesign.domain.donateorder.web.dto;

import io.wisoft.capstonedesign.domain.donateorder.persistence.DonateOrder;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class GetOrderDto {

    private Long id;

    private LocalDateTime sendDate;

    private String text;

    private String address;

    private String title;

    private String nickname;

    public GetOrderDto(DonateOrder donateOrder) {
        this.id = donateOrder.getId();
        this.sendDate = donateOrder.getSendDate();
        this.text = donateOrder.getText();
        this.address = donateOrder.getInformation().getAddress();
        this.title = donateOrder.getDonate().getTitle();
        this.nickname = donateOrder.getUser().getNickname();
    }
}

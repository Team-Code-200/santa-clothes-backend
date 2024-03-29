package io.wisoft.capstonedesign.domain.donateorder.web.dto;

import io.wisoft.capstonedesign.domain.donateorder.persistence.DonateOrder;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetDonateOrderDto {

    private Long id;

    private LocalDateTime sendDate;

    private String text;

    private String address;

    private String title;

    private String nickname;

    public GetDonateOrderDto(DonateOrder donateOrder) {
        this.id = donateOrder.getId();
        this.sendDate = donateOrder.getSendDate();
        this.text = donateOrder.getText();
        this.address = donateOrder.getInformation().getAddress();
        this.title = donateOrder.getDonate().getTitle();
        this.nickname = donateOrder.getUser().getNickname();
    }
}

package io.wisoft.capstonedesign.domain.donateorder.web.dto;

import io.wisoft.capstonedesign.domain.donateorder.persistence.DonateOrder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderListDto {

    private String user;

    private String title;

    private String address;

    private LocalDateTime sendDate;

    public OrderListDto(DonateOrder donateOrder) {
        this.user = donateOrder.getUser().getNickname();
        this.title = donateOrder.getDonate().getTitle();
        this.address = donateOrder.getInformation().getAddress();
        this.sendDate = donateOrder.getSendDate();
    }
}

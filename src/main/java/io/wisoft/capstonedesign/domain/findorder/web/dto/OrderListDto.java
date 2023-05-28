package io.wisoft.capstonedesign.domain.findorder.web.dto;

import io.wisoft.capstonedesign.domain.findorder.persistence.FindOrder;
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

    public OrderListDto(FindOrder findOrder) {
        this.user = findOrder.getUser().getNickname();
        this.title = findOrder.getFind().getTitle();
        this.address = findOrder.getInformation().getAddress();
        this.sendDate = findOrder.getSendDate();
    }
}

package io.wisoft.capstonedesign.domain.findorder.web.dto;

import io.wisoft.capstonedesign.domain.findorder.persistence.FindOrder;
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

    public GetOrderDto(FindOrder findOrder) {
        this.id = findOrder.getId();
        this.sendDate = findOrder.getSendDate();
        this.text = findOrder.getText();
        this.address = findOrder.getInformation().getAddress();
        this.title = findOrder.getFind().getTitle();
        this.nickname = findOrder.getUser().getNickname();
    }
}

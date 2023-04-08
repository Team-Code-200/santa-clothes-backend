package io.wisoft.capstonedesign.domain.findorder.web.dto;

import io.wisoft.capstonedesign.domain.findorder.persistence.FindOrder;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderDto {

    private String text;

    private String nickname;

    private String phoneNumber;

    private String title;

    public OrderDto(FindOrder findOrder) {
        this.text = findOrder.getText();
        this.nickname = findOrder.getUser().getNickname();
        this.phoneNumber = findOrder.getInformation().getPhoneNumber();
        this.title = findOrder.getFind().getTitle();
    }
}

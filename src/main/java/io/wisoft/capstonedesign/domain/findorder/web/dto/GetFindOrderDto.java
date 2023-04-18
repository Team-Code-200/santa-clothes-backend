package io.wisoft.capstonedesign.domain.findorder.web.dto;

import io.wisoft.capstonedesign.domain.findorder.persistence.FindOrder;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetFindOrderDto {

    private Long id;

    private LocalDateTime sendDate;

    private String text;

    private String address;

    private String title;

    private String nickname;

    public GetFindOrderDto(FindOrder findOrder) {
        this.id = findOrder.getId();
        this.sendDate = findOrder.getSendDate();
        this.text = findOrder.getText();
        this.address = findOrder.getInformation().getAddress();
        this.title = findOrder.getFind().getTitle();
        this.nickname = findOrder.getUser().getNickname();
    }
}

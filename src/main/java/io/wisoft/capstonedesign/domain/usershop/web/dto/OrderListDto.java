package io.wisoft.capstonedesign.domain.usershop.web.dto;

import io.wisoft.capstonedesign.domain.usershop.persistence.UserShop;
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

    private LocalDateTime createdDate;

    public OrderListDto(UserShop userShop) {
        this.user = userShop.getUser().getNickname();
        this.title = userShop.getShop().getTitle();
        this.address = userShop.getInformation().getAddress();
        this.createdDate = userShop.getCreatedDate();
    }
}

package io.wisoft.capstonedesign.domain.information.web.dto;

import io.wisoft.capstonedesign.domain.information.persistence.Information;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InfoListDto {

    private String username;

    private String address;

    private String phoneNumber;

    private LocalDateTime createdDate;

    private String user;

    public InfoListDto(Information information) {
        this.username = information.getUsername();
        this.address = information.getAddress();
        this.phoneNumber = information.getPhoneNumber();
        this.createdDate = information.getCreatedDate();
        this.user = information.getUser().getNickname();
    }
}

package io.wisoft.capstonedesign.domain.usershop.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderDto {

    private String text;

    private String nickname;

    private String phoneNumber;

    private String title;
}

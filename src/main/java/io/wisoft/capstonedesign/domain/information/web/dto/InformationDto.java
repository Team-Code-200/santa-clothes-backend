package io.wisoft.capstonedesign.domain.information.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InformationDto {

    private String username;

    private String address;

    private String phoneNumber;
}

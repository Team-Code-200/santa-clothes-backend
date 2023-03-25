package io.wisoft.capstonedesign.domain.information.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateInformationResponse {

    private Long id;

    private String username;

    private String address;

    private String phoneNumber;
}

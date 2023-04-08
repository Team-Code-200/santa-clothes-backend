package io.wisoft.capstonedesign.domain.information.web.dto;

import io.wisoft.capstonedesign.domain.information.persistence.Information;
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

    public UpdateInformationResponse(Information information) {
        this.id = information.getId();
        this.username = information.getUsername();
        this.address = information.getAddress();
        this.phoneNumber = information.getPhoneNumber();
    }
}

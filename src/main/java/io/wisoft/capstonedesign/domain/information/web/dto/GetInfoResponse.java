package io.wisoft.capstonedesign.domain.information.web.dto;

import io.wisoft.capstonedesign.domain.information.persistence.Information;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetInfoResponse {

    private String username;

    private String address;

    private String phoneNumber;

    public GetInfoResponse(Information information) {
        this.username = information.getUsername();
        this.address = information.getAddress();
        this.phoneNumber = information.getPhoneNumber();
    }
}

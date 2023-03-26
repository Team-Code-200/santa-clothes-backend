package io.wisoft.capstonedesign.domain.information.web.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateInformationRequest {

    @NotEmpty
    private String username;

    @NotEmpty
    private String address;

    @NotEmpty
    private String phoneNumber;

    private Long userId;

    public static CreateInformationRequest newInstance(
            String username,
            String address,
            String phoneNumber,
            Long userId
    ) {
        CreateInformationRequest request = new CreateInformationRequest();
        request.username = username;
        request.address = address;
        request.phoneNumber = phoneNumber;
        request.userId = userId;

        return request;
    }
}
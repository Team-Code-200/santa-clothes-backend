package io.wisoft.capstonedesign.domain.information.web.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateInformationRequest {

    @NotEmpty
    private String username;

    @NotEmpty
    private String address;

    @NotEmpty
    private String phoneNumber;

    private Long infoId;

    public static UpdateInformationRequest newInstance(
            final String username,
            final String address,
            final String phoneNumber,
            final Long infoId
    ) {
        UpdateInformationRequest request = new UpdateInformationRequest();
        request.username = username;
        request.address = address;
        request.phoneNumber = phoneNumber;
        request.infoId = infoId;

        return request;
    }
}

package io.wisoft.capstonedesign.domain.donate.web.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDonateRequest {

    @NotEmpty
    private String title;

    private String image;

    @NotEmpty
    private String text;

    private int view;

    private String tag;

    private Long donateId;

    public static UpdateDonateRequest newInstance(
            String title,
            String image,
            String text,
            String tag,
            Long donateId
    ) {
        UpdateDonateRequest request = new UpdateDonateRequest();
        request.title = title;
        request.image = image;
        request.text = text;
        request.tag = tag;
        request.donateId = donateId;

        return request;
    }
}

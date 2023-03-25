package io.wisoft.capstonedesign.domain.find.web.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFindRequest {

    @NotEmpty
    private String title;

    private String image;

    @NotEmpty
    private String text;

    private int view;

    private String tag;

    private Long findId;

    public static UpdateFindRequest newInstance(
            String title,
            String image,
            String text,
            String tag,
            Long findId
    ) {
        UpdateFindRequest request = new UpdateFindRequest();
        request.title = title;
        request.image = image;
        request.text = text;
        request.tag = tag;
        request.findId = findId;

        return request;
    }
}

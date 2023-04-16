package io.wisoft.capstonedesign.domain.find.web.dto;

import io.wisoft.capstonedesign.domain.find.persistence.Find;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UpdateFindResponse {

    private Long id;

    private String title;

    private String image;

    private String text;

    private int view;

    private String tag;

    public UpdateFindResponse(Find find) {
        this.id = find.getId();
        this.title = find.getTitle();
        this.image = find.getImage();
        this.text = find.getText();
        this.view = find.getView();
        this.tag = String.valueOf(find.getTag());
    }
}

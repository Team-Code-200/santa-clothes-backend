package io.wisoft.capstonedesign.domain.donate.web.dto;

import io.wisoft.capstonedesign.domain.donate.persistence.Donate;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UpdateDonateResponse {

    private Long id;

    private String title;

    private String image;

    private String text;

    private int view;

    private String tag;

    public UpdateDonateResponse(Donate donate) {
        this.id = donate.getId();
        this.title = donate.getTitle();
        this.image = donate.getImage();
        this.text = donate.getText();
        this.view = donate.getView();
        this.tag = String.valueOf(donate.getTag());
    }
}

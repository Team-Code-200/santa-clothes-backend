package io.wisoft.capstonedesign.domain.donate.web.dto;

import io.wisoft.capstonedesign.domain.donate.persistence.Donate;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class GetDonateResponse {

    private String tag;

    private int view;

    private String title;

    private String nickname;

    private LocalDateTime createdDate;

    private String text;

    private String image;

    public GetDonateResponse(Donate donate) {
        this.tag = String.valueOf(donate.getTag());
        this.view = donate.getView();
        this.title = donate.getTitle();
        this.nickname = donate.getUser().getNickname();
        this.createdDate = donate.getCreatedDate();
        this.text = donate.getText();
        this.image = donate.getImage();
    }
}

package io.wisoft.capstonedesign.domain.find.web.dto;

import io.wisoft.capstonedesign.domain.find.persistence.Find;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetFindResponse {

    private String tag;

    private int view;

    private String title;

    private String nickname;

    private LocalDateTime createdDate;

    private String text;

    private String image;

    public GetFindResponse(Find find) {
        this.tag = String.valueOf(find.getTag());
        this.view = find.getView();
        this.title = find.getTitle();
        this.nickname = find.getUser().getNickname();
        this.createdDate = find.getCreatedDate();
        this.text = find.getText();
        this.image = find.getImage();
    }
}

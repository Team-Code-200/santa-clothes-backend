package io.wisoft.capstonedesign.domain.find.web.dto;

import io.wisoft.capstonedesign.domain.find.persistence.Find;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FindListDto {

    private String title;

    private LocalDateTime createdDate;

    private int view;

    private String tag;

    private String user;

    public FindListDto(Find find) {
        this.title = find.getTitle();
        this.createdDate = find.getCreatedDate();
        this.view = find.getView();
        this.tag = String.valueOf(find.getTag());
        this.user = find.getUser().getNickname();
    }
}

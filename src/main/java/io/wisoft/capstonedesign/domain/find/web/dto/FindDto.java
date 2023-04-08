package io.wisoft.capstonedesign.domain.find.web.dto;

import io.wisoft.capstonedesign.domain.find.persistence.Find;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class FindDto {

    private String title;

    private String tag;

    private String nickname;

    private LocalDateTime createdDate;

    public FindDto(Find find) {
        this.title = find.getTitle();
        this.tag = String.valueOf(find.getTag());
        this.nickname = find.getUser().getNickname();
        this.createdDate = find.getCreatedDate();
    }
}

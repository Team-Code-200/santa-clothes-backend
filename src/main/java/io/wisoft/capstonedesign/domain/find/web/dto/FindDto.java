package io.wisoft.capstonedesign.domain.find.web.dto;

import io.wisoft.capstonedesign.domain.find.persistence.Find;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FindDto {
    private Long id;

    private String title;

    private String tag;

    private String nickname;

    private LocalDateTime createdDate;

    public FindDto(Find find) {
        this.id = find.getId();
        this.title = find.getTitle();
        this.tag = String.valueOf(find.getTag());
        this.nickname = find.getUser().getNickname();
        this.createdDate = find.getCreatedDate();
    }
}

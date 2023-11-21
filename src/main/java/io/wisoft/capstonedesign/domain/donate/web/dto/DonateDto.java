package io.wisoft.capstonedesign.domain.donate.web.dto;

import io.wisoft.capstonedesign.domain.donate.persistence.Donate;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DonateDto {
    private Long id;

    private String title;

    private String tag;

    private String nickname;

    private LocalDateTime createdDate;

    public DonateDto(final Donate donate) {
        this.id = donate.getId();
        this.title = donate.getTitle();
        this.tag = String.valueOf(donate.getTag());
        this.nickname = donate.getUser().getNickname();
        this.createdDate = donate.getCreatedDate();
    }
}

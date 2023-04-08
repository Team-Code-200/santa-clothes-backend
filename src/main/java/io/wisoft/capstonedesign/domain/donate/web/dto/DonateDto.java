package io.wisoft.capstonedesign.domain.donate.web.dto;

import io.wisoft.capstonedesign.domain.donate.persistence.Donate;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
public class DonateDto {

    private String title;

    private String tag;

    private String nickname;

    private LocalDateTime createdDate;

    public DonateDto(Donate donate) {
        this.title = donate.getTitle();
        this.tag = String.valueOf(donate.getTag());
        this.nickname = donate.getUser().getNickname();
        this.createdDate = donate.getCreatedDate();
    }
}

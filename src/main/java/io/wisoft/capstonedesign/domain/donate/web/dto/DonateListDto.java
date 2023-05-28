package io.wisoft.capstonedesign.domain.donate.web.dto;

import io.wisoft.capstonedesign.domain.donate.persistence.Donate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DonateListDto {

    private String title;

    private LocalDateTime createdDate;

    private int view;

    private String tag;

    private String user;

    public DonateListDto(Donate donate) {
        this.title = donate.getTitle();
        this.createdDate = donate.getCreatedDate();
        this.view = donate.getView();
        this.tag = String.valueOf(donate.getTag());
        this.user = donate.getUser().getNickname();
    }
}

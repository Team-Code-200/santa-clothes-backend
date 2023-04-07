package io.wisoft.capstonedesign.domain.donate.web.dto;

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
}

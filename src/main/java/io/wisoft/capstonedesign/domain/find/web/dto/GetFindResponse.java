package io.wisoft.capstonedesign.domain.find.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class GetFindResponse {

    private String tag;

    private int view;

    private String title;

    private String nickname;

    private LocalDateTime createdDate;

    private String text;

    private String image;
}

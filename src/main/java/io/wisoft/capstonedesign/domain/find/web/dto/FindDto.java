package io.wisoft.capstonedesign.domain.find.web.dto;

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
}

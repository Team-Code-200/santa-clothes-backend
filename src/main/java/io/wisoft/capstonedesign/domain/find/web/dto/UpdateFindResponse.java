package io.wisoft.capstonedesign.domain.find.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFindResponse {

    private Long id;

    private String title;

    private String image;

    private String text;

    private int view;

    private String tag;
}

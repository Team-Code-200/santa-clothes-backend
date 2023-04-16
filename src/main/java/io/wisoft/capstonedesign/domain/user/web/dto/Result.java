package io.wisoft.capstonedesign.domain.user.web.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Result<T> {

    private T data;
}

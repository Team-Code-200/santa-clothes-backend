package io.wisoft.capstonedesign.domain.usershop.web.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Result<T> {

    private T data;
}

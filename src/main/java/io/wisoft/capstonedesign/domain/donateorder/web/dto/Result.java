package io.wisoft.capstonedesign.domain.donateorder.web.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Result<T> {

    private T data;
}

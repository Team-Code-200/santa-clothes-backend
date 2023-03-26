package io.wisoft.capstonedesign.domain.usershop.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result<T> {

    private T data;
}

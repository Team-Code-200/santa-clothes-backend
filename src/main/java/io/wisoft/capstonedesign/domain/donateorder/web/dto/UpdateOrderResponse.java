package io.wisoft.capstonedesign.domain.donateorder.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderResponse {

    private Long id;

    private String text;
}

package io.wisoft.capstonedesign.setting.data;

import io.wisoft.capstonedesign.domain.donate.web.dto.CreateDonateRequest;
import io.wisoft.capstonedesign.global.enumerated.Tag;

public class DefaultDonateData {

    public static CreateDonateRequest createDefaultDonate(Long userId) {

        return CreateDonateRequest.builder()
                .title("패딩 나눔합니다.")
                .image("image.png")
                .text("안 입는 패딩 기부해요")
                .tag(String.valueOf(Tag.TOP))
                .userId(userId)
                .build();
    }
}

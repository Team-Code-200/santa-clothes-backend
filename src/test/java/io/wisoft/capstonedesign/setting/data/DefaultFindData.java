package io.wisoft.capstonedesign.setting.data;

import io.wisoft.capstonedesign.domain.find.web.dto.CreateFindRequest;
import io.wisoft.capstonedesign.global.enumerated.Tag;

public class DefaultFindData {

    public static CreateFindRequest createDefaultFind(Long userId) {

        return CreateFindRequest.builder()
                .title("패딩 찾아봅니다")
                .image("image.png")
                .text("안 입는 패딩 기부받아요")
                .tag(String.valueOf(Tag.TOP))
                .userId(userId)
                .build();
    }
}

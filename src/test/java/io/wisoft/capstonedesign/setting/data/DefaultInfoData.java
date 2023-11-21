package io.wisoft.capstonedesign.setting.data;

import io.wisoft.capstonedesign.domain.address.persistence.Address;
import io.wisoft.capstonedesign.domain.information.web.dto.CreateInformationRequest;

public class DefaultInfoData {

    public static CreateInformationRequest createDefaultInfo(Long userId) {

        return CreateInformationRequest.builder()
                .username("윤진원")
                .address(Address.translateAddressToString(Address.createAddress("34159","대전광역시 유성구 동서대로 125","한밭대학교 wisoft","N5-503")))
                .phoneNumber("010-0000-0000")
                .userId(userId)
                .build();
    }
}

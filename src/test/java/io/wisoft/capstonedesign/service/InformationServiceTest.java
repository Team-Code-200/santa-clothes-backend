package io.wisoft.capstonedesign.service;

import io.wisoft.capstonedesign.domain.information.application.InformationService;
import io.wisoft.capstonedesign.domain.information.persistence.Information;
import io.wisoft.capstonedesign.domain.information.web.dto.CreateInformationRequest;
import io.wisoft.capstonedesign.domain.information.web.dto.UpdateInformationRequest;
import io.wisoft.capstonedesign.domain.user.web.dto.CreateUserRequest;
import io.wisoft.capstonedesign.global.enumerated.Role;
import io.wisoft.capstonedesign.domain.user.application.UserService;
import io.wisoft.capstonedesign.global.exception.service.InfoNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class InformationServiceTest {

    @Autowired InformationService informationService;
    @Autowired UserService userService;

    @Test
    public void 배송정보_생성() throws Exception {

        // given
        CreateUserRequest request1 = new CreateUserRequest("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", String.valueOf(Role.GENERAL));
        CreateInformationRequest request2 = new CreateInformationRequest("윤진원", "대전광역시 유성구", "010-0000-0000", 1L);

        // when
        userService.join(request1);
        Long savedId = informationService.save(request2);

        // then
        assertEquals(request2.address(), informationService.findById(savedId).getAddress());
    }

    @Test
    public void 회원별_배송정보_조회() throws Exception {

        // given
        CreateUserRequest request1 = new CreateUserRequest("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", String.valueOf(Role.GENERAL));
        CreateUserRequest request2 = new CreateUserRequest("2", "donggwon@gmail.com", "profile.png", 1000, "donggwon", String.valueOf(Role.GENERAL));
        CreateInformationRequest request3 = new CreateInformationRequest("윤진원", "대전광역시 유성구", "010-0000-0000", 1L);
        CreateInformationRequest request4 = new CreateInformationRequest("윤진원", "대전광역시 유성구", "010-0000-0000", 1L);
        CreateInformationRequest request5 = new CreateInformationRequest("서동권", "대전광역시 덕명동", "010-1111-1111", 2L);

        // when
        Long userId = userService.join(request1);
        userService.join(request2);
        informationService.save(request3);
        informationService.save(request4);
        informationService.save(request5);
        List<Information> savedInfo = informationService.findByUser(userId);

        // then
        assertEquals(2, savedInfo.size());
    }

    @Test
    public void 전체_배송정보_조회() throws Exception {

        // given
        CreateUserRequest request1 = new CreateUserRequest("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", String.valueOf(Role.GENERAL));
        CreateUserRequest request2 = new CreateUserRequest("2", "donggwon@gmail.com", "profile.png", 1000, "donggwon", String.valueOf(Role.GENERAL));
        CreateInformationRequest request3 = new CreateInformationRequest("윤진원", "대전광역시 유성구", "010-0000-0000", 1L);
        CreateInformationRequest request4 = new CreateInformationRequest("윤진원", "대전광역시 유성구", "010-0000-0000", 1L);
        CreateInformationRequest request5 = new CreateInformationRequest("서동권", "대전광역시 덕명동", "010-1111-1111", 2L);

        // when
        userService.join(request1);
        userService.join(request2);
        informationService.save(request3);
        informationService.save(request4);
        informationService.save(request5);
        List<Information> informations = informationService.findInformations();

        // then
        assertEquals(3, informations.size());
    }

    @Test
    public void 배송정보_전체_수정() throws Exception {

        // given
        CreateUserRequest request1 = new CreateUserRequest("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", String.valueOf(Role.GENERAL));
        CreateInformationRequest request2 = new CreateInformationRequest("윤진원", "대전광역시 유성구", "010-0000-0000", 1L);

        // when
        userService.join(request1);
        Long savedId = informationService.save(request2);

        UpdateInformationRequest updateRequest = new UpdateInformationRequest("윤진원", "대전광역시 서구", "010-1111-1111", 1L);
        informationService.updateAll(savedId, updateRequest);
        Information updateInfo = informationService.findById(savedId);

        // then
        assertEquals("대전광역시 서구", updateInfo.getAddress());
    }

    @Test
    public void 배송정보_삭제() throws Exception {

        // given
        CreateUserRequest request1 = new CreateUserRequest("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", String.valueOf(Role.GENERAL));
        CreateInformationRequest request2 = new CreateInformationRequest("윤진원", "대전광역시 유성구", "010-0000-0000", 1L);

        // when
        userService.join(request1);
        Long savedId = informationService.save(request2);

        informationService.deleteInformation(savedId);

        // then
        assertThrows(InfoNotFoundException.class, () -> informationService.findById(savedId));
    }
}

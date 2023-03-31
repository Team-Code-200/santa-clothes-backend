package io.wisoft.capstonedesign.service;

import io.wisoft.capstonedesign.domain.information.application.InformationService;
import io.wisoft.capstonedesign.domain.information.persistence.Information;
import io.wisoft.capstonedesign.domain.information.web.dto.CreateInformationRequest;
import io.wisoft.capstonedesign.domain.information.web.dto.UpdateInformationRequest;
import io.wisoft.capstonedesign.global.enumerated.Role;
import io.wisoft.capstonedesign.domain.user.application.UserService;
import io.wisoft.capstonedesign.domain.user.persistence.User;
import io.wisoft.capstonedesign.global.exception.service.InfoNotFoundException;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.opentest4j.AssertionFailedError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class InformationServiceTest {

    @Autowired InformationService informationService;
    @Autowired UserService userService;

    @Test
    public void 배송정보_생성() throws Exception {

        // given
        User user = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", Role.GENERAL);
        CreateInformationRequest request = CreateInformationRequest.newInstance("윤진원", "대전광역시 유성구", "010-0000-0000", 1L);

        // when
        userService.join(user);
        Long savedId = informationService.save(request);

        // then
        assertEquals(request.getAddress(), informationService.findOne(savedId).getAddress());
    }

    @Test
    public void 회원별_배송정보_조회() throws Exception {

        // given
        User user1 = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", Role.GENERAL);
        User user2 = User.newInstance("2", "donggwon@gmail.com", "profile.png", 1000, "donggown", Role.GENERAL);
        CreateInformationRequest request1 = CreateInformationRequest.newInstance("윤진원", "대전광역시 관평동", "010-0000-0000", 1L);
        CreateInformationRequest request2 = CreateInformationRequest.newInstance("윤진원", "대전광역시 관평동", "010-0000-0000", 1L);
        CreateInformationRequest request3 = CreateInformationRequest.newInstance("서동권", "대전광역시 덕명동", "010-1111-1111", 2L);

        // when
        userService.join(user1);
        userService.join(user2);
        informationService.save(request1);
        informationService.save(request2);
        informationService.save(request3);
        List<Information> savedInfo = informationService.findInformationByUser(user1);

        // then
        assertEquals(2, savedInfo.size());
    }

    @Test(expected = AssertionFailedError.class)
    public void 전체_배송정보_조회() throws Exception {

        // given
        User user1 = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", Role.GENERAL);
        User user2 = User.newInstance("2", "donggwon@gmail.com", "profile.png", 1000, "donggown", Role.GENERAL);
        CreateInformationRequest request1 = CreateInformationRequest.newInstance("윤진원", "대전광역시 관평동", "010-0000-0000", 1L);
        CreateInformationRequest request2 = CreateInformationRequest.newInstance("윤진원", "대전광역시 관평동", "010-0000-0000", 1L);
        CreateInformationRequest request3 = CreateInformationRequest.newInstance("서동권", "대전광역시 덕명동", "010-1111-1111", 2L);

        // when
        userService.join(user1);
        userService.join(user2);
        informationService.save(request1);
        informationService.save(request2);
        informationService.save(request3);
        List<Information> informations = informationService.findInformations();

        // then
        assertEquals(2, informations.size());
        fail("예외가 발생해야 한다!");
    }

    @Test
    public void 배송정보_전체_수정() throws Exception {

        // given
        User user = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", Role.GENERAL);
        CreateInformationRequest request = CreateInformationRequest.newInstance("윤진원", "대전광역시 유성구", "010-0000-0000", 1L);

        // when
        userService.join(user);
        Long savedId = informationService.save(request);

        UpdateInformationRequest updateRequest = UpdateInformationRequest.newInstance("윤진원", "대전광역시 서구", "010-1111-1111", 1L);
        informationService.updateAll(updateRequest);
        Information updateInfo = informationService.findOne(savedId);

        // then
        assertEquals("대전광역시 서구", updateInfo.getAddress());
    }

    @Test(expected = InfoNotFoundException.class)
    public void 배송정보_삭제() throws Exception {

        // given
        User user = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", Role.GENERAL);
        CreateInformationRequest request = CreateInformationRequest.newInstance("윤진원", "대전광역시 유성구", "010-0000-0000", 1L);

        // when
        userService.join(user);
        Long savedId = informationService.save(request);

        informationService.deleteInformation(savedId);
        Information deleteInfo = informationService.findOne(savedId);

        // then
        assertEquals(request, deleteInfo);
    }
}

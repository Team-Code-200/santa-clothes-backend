package io.wisoft.capstonedesign.service;

import io.wisoft.capstonedesign.domain.Information;
import io.wisoft.capstonedesign.domain.Role;
import io.wisoft.capstonedesign.domain.User;
import io.wisoft.capstonedesign.repository.InformationRepository;
import io.wisoft.capstonedesign.repository.UserRepository;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.opentest4j.AssertionFailedError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback(value = false)
public class InformationServiceTest {

    @Autowired InformationRepository informationRepository;
    @Autowired UserRepository userRepository;
    @Autowired InformationService informationService;
    @Autowired UserService userService;

    @Test
    public void 배송정보_생성() throws Exception {

        // given
        User user = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", Role.GENERAL);
        Information information = Information.createInformation("윤진원", "대전광역시 유성구", "010-0000-0000", user);

        // when
        userService.join(user);
        Long savedId = informationService.save(information);

        // then
        assertEquals(information, informationService.findOne(savedId));
    }

    @Test
    public void 회원별_배송정보_조회() throws Exception {

        // given
        User user1 = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", Role.GENERAL);
        User user2 = User.newInstance("2", "donggwon@gmail.com", "profile.png", 1000, "donggown", Role.GENERAL);
        Information information1 = Information.createInformation("윤진원", "대전광역시 관평동", "010-0000-0000", user1);
        Information information2 = Information.createInformation("윤진원", "대전광역시 관평동", "010-0000-0000", user1);
        Information information3 = Information.createInformation("서동권", "대전광역시 덕명동", "010-0000-0000", user2);

        // when
        userService.join(user1);
        userService.join(user2);
        informationService.save(information1);
        informationService.save(information2);
        informationService.save(information3);
        List<Information> savedInfo = informationService.findInformationByUser(user1);

        // then
        assertEquals(2, savedInfo.size());
    }

    @Test(expected = AssertionFailedError.class)
    public void 전체_배송정보_조회() throws Exception {

        // given
        User user1 = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", Role.GENERAL);
        User user2 = User.newInstance("2", "donggwon@gmail.com", "profile.png", 1000, "donggown", Role.GENERAL);
        Information information1 = Information.createInformation("윤진원", "대전광역시 관평동", "010-0000-0000", user1);
        Information information2 = Information.createInformation("윤진원", "대전광역시 관평동", "010-0000-0000", user1);
        Information information3 = Information.createInformation("서동권", "대전광역시 덕명동", "010-0000-0000", user2);

        // when
        userService.join(user1);
        userService.join(user2);
        informationService.save(information1);
        informationService.save(information2);
        informationService.save(information3);
        List<Information> informations = informationService.findInformations();

        // then
        assertEquals(2, informations.size());
        fail("예외가 발생해야 한다!");
    }

    @Test
    public void 배송정보_전체_수정() throws Exception {

        // given
        User user = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", Role.GENERAL);
        Information information = Information.createInformation("윤진원", "대전광역시 관평동", "010-0000-0000", user);

        // when
        userService.join(user);
        Long savedId = informationService.save(information);

        informationService.updateAll(savedId, "윤진원", "대전광역시 덕명동", "010-0000-0000");
        Information updateInfo = informationService.findOne(savedId);

        // then
        assertEquals("대전광역시 덕명동", updateInfo.getAddress());
    }

    @Test(expected = AssertionFailedError.class)
    public void 배송정보_삭제() throws Exception {

        // given
        User user = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", Role.GENERAL);
        Information information = Information.createInformation("윤진원", "대전광역시 관평동", "010-0000-0000", user);

        // when
        userService.join(user);
        Long savedId = informationService.save(information);

        informationService.deleteInformation(savedId);
        Information deleteInfo = informationService.findOne(savedId);

        // then
        assertEquals(information, deleteInfo);
    }
}

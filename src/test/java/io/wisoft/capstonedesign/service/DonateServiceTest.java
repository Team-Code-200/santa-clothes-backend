package io.wisoft.capstonedesign.service;

import io.wisoft.capstonedesign.domain.Donate;
import io.wisoft.capstonedesign.domain.Role;
import io.wisoft.capstonedesign.domain.Tag;
import io.wisoft.capstonedesign.domain.User;
import io.wisoft.capstonedesign.repository.DonateRepository;
import io.wisoft.capstonedesign.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opentest4j.AssertionFailedError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class DonateServiceTest {

    @Autowired DonateRepository donateRepository;
    @Autowired UserRepository userRepository;
    @Autowired DonateService donateService;
    @Autowired UserService userService;

    @Test
    public void 게시글_작성() throws Exception {

        // given
        User user = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", LocalDateTime.now(), Role.GENERAL);
        Donate donate = Donate.createDonate("패딩 나눔합니다", LocalDateTime.now(), "image.png", "안 입는 패딩 기부해요", 0, Tag.TOP, user);

        // when
        userService.join(user);
        Long savedId = donateService.join(donate);

        // then
        assertEquals(donate, donateRepository.findOne(savedId));
    }

    @Test
    public void 게시글_조회() throws Exception {

        // given
        User user = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", LocalDateTime.now(), Role.GENERAL);
        Donate donate = Donate.createDonate("패딩 나눔합니다", LocalDateTime.now(), "image.png", "안 입는 패딩 기부해요", 0, Tag.TOP, user);

        // when
        userService.join(user);
        Long savedId = donateService.join(donate);
        Donate savedDonate = donateService.findOne(savedId);

        // then
        assertEquals(donate, savedDonate);
    }

    @Test(expected = AssertionFailedError.class)
    public void 전체_게시글_조회() throws Exception {

        // given
        User user = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", LocalDateTime.now(), Role.GENERAL);
        Donate donate1 = Donate.createDonate("패딩 나눔합니다", LocalDateTime.now(), "image1.png", "안 입는 패딩 기부해요", 0, Tag.TOP, user);
        Donate donate2 = Donate.createDonate("바지 나눔합니다", LocalDateTime.now(), "image2.png", "안 입는 바지 기부해요", 0, Tag.PANTS, user);

        // when
        userService.join(user);
        donateService.join(donate1);
        donateService.join(donate2);
        List<Donate> donates = donateService.findDonates();

        // then
        assertEquals(3, donates.size());
        fail("예외가 발생해야 한다!");
    }
}

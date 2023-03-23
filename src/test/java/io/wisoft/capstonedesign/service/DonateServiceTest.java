package io.wisoft.capstonedesign.service;

import io.wisoft.capstonedesign.domain.donate.application.DonateService;
import io.wisoft.capstonedesign.domain.donate.persistence.Donate;
import io.wisoft.capstonedesign.global.enumerated.Role;
import io.wisoft.capstonedesign.global.enumerated.Tag;
import io.wisoft.capstonedesign.domain.user.application.UserService;
import io.wisoft.capstonedesign.domain.user.persistence.User;
import io.wisoft.capstonedesign.domain.donate.persistence.DonateRepository;
import io.wisoft.capstonedesign.domain.user.persistence.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
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
@Rollback(value = false)
public class DonateServiceTest {

    @Autowired DonateRepository donateRepository;
    @Autowired UserRepository userRepository;
    @Autowired
    DonateService donateService;
    @Autowired
    UserService userService;

    @Test
    public void 게시글_작성() throws Exception {

        // given
        User user = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", Role.GENERAL);
        Donate donate = Donate.createDonate("패딩 나눔합니다", "image.png", "안 입는 패딩 기부해요", 0, Tag.TOP, user);

        // when
        userService.join(user);
        Long savedId = donateService.join(donate);

        // then
        assertEquals(donate, donateRepository.findOne(savedId));
    }

    @Test
    public void 게시글_조회() throws Exception {

        // given
        User user = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", Role.GENERAL);
        Donate donate = Donate.createDonate("패딩 나눔합니다", "image.png", "안 입는 패딩 기부해요", 0, Tag.TOP, user);

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
        User user = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", Role.GENERAL);
        Donate donate1 = Donate.createDonate("패딩 나눔합니다", "image1.png", "안 입는 패딩 기부해요", 0, Tag.TOP, user);
        Donate donate2 = Donate.createDonate("바지 나눔합니다", "image2.png", "안 입는 바지 기부해요", 0, Tag.PANTS, user);

        // when
        userService.join(user);
        donateService.join(donate1);
        donateService.join(donate2);
        List<Donate> donates = donateService.findDonates();

        // then
        assertEquals(3, donates.size());
        fail("예외가 발생해야 한다!");
    }

    @Test
    public void 전체_게시글_최근순_조회() throws Exception {

        // given
        User user1 = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", Role.GENERAL);
        User user2 = User.newInstance("2", "donggwon@gmail.com", "profile.png", 2000, "donggwon", Role.GENERAL);
        Donate donate1 = Donate.createDonate("패딩 나눔합니다", "image1.png", "안 입는 패딩 기부해요", 0, Tag.TOP, user1);
        Donate donate2 = Donate.createDonate("바지 나눔합니다", "image2.png", "안 입는 바지 기부해요", 0, Tag.PANTS, user2);

        // when
        userService.join(user1);
        userService.join(user2);
        donateService.join(donate1);
        donateService.join(donate2);
        List<Donate> donateListDESC = donateService.findByCreatedDateDESC();

        // then
        assertEquals(donate2, donateListDESC.get(0));
    }

    @Test
    public void 전체_게시글_태그별_조회() throws Exception {

        // given
        User user = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", Role.GENERAL);
        Donate donate1 = Donate.createDonate("패딩 나눔합니다", "image1.png", "안 입는 패딩 기부해요", 0, Tag.TOP, user);
        Donate donate2 = Donate.createDonate("후드티 나눔합니다", "image2.png", "안 입는 후드티 기부해요", 0, Tag.TOP, user);
        Donate donate3 = Donate.createDonate("바지 나눔합니다", "image3.png", "안 입는 바지 기부해요", 0, Tag.PANTS, user);

        // when
        userService.join(user);
        donateService.join(donate1);
        donateService.join(donate2);
        donateService.join(donate3);
        List<Donate> byTag = donateService.findByTag(Tag.TOP);

        // then
        assertEquals(2, byTag.size());
    }

    @Test
    public void 게시글_전체_수정() throws Exception {

        // given
        User user = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", Role.GENERAL);
        Donate donate = Donate.createDonate("패딩 나눔합니다", "image1.png", "안 입는 패딩 기부해요", 0, Tag.TOP, user);

        // when
        userService.join(user);
        Long donateId = donateService.join(donate);

        donateService.updateAll(donateId, "바지 나눔합니다", "image2.png", "안 입는 바지 기부해요", Tag.PANTS);
        Donate updateDonate = donateService.findOne(donateId);

        // then
        assertEquals("바지 나눔합니다", updateDonate.getTitle());
        System.out.println(updateDonate.getView()); // 조회수 확인
    }

    @Test(expected = AssertionFailedError.class)
    public void 게시글_삭제() throws Exception {

        // given
        User user = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", Role.GENERAL);
        Donate donate = Donate.createDonate("패딩 나눔합니다", "image1.png", "안 입는 패딩 기부해요", 0, Tag.TOP, user);

        // when
        userService.join(user);
        Long donateId = donateService.join(donate);

        donateService.deleteDonate(donateId);
        Donate deletedDonate = donateService.findOne(donateId);

        // then
        assertEquals(donate, deletedDonate);
    }
}

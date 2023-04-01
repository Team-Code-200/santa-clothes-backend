package io.wisoft.capstonedesign.service;

import io.wisoft.capstonedesign.domain.donate.application.DonateService;
import io.wisoft.capstonedesign.domain.donate.persistence.Donate;
import io.wisoft.capstonedesign.domain.donate.web.dto.CreateDonateRequest;
import io.wisoft.capstonedesign.domain.donate.web.dto.UpdateDonateRequest;
import io.wisoft.capstonedesign.global.enumerated.Role;
import io.wisoft.capstonedesign.global.enumerated.Tag;
import io.wisoft.capstonedesign.domain.user.application.UserService;
import io.wisoft.capstonedesign.domain.user.persistence.User;
import io.wisoft.capstonedesign.global.exception.service.PostNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opentest4j.AssertionFailedError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class DonateServiceTest {

    @Autowired DonateService donateService;
    @Autowired UserService userService;

    @Test
    public void 게시글_작성() throws Exception {

        // given
        User user = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", Role.GENERAL);
        CreateDonateRequest request = CreateDonateRequest.newInstance("패딩 나눔합니다", "image.png", "안 입는 패딩 기부해요", String.valueOf(Tag.TOP), 1L);

        // when
        userService.join(user);
        Long savedId = donateService.join(request);

        // then
        assertEquals(request.getTitle(), donateService.findById(savedId).getTitle());
    }

    @Test
    public void 게시글_조회() throws Exception {

        // given
        User user = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", Role.GENERAL);
        CreateDonateRequest request = CreateDonateRequest.newInstance("패딩 나눔합니다", "image.png", "안 입는 패딩 기부해요", String.valueOf(Tag.TOP), 1L);

        // when
        userService.join(user);
        Long savedId = donateService.join(request);
        Donate savedDonate = donateService.findById(savedId);

        // then
        assertEquals(request.getTitle(), savedDonate.getTitle());
    }

    @Test(expected = AssertionFailedError.class)
    public void 전체_게시글_조회() throws Exception {

        // given
        User user = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", Role.GENERAL);
        CreateDonateRequest request1 = CreateDonateRequest.newInstance("패딩 나눔합니다", "image.png", "안 입는 패딩 기부해요", String.valueOf(Tag.TOP), 1L);
        CreateDonateRequest request2 = CreateDonateRequest.newInstance("바지 나눔합니다", "image.png", "안 입는 바지 기부해요", String.valueOf(Tag.PANTS), 1L);

        // when
        userService.join(user);
        donateService.join(request1);
        donateService.join(request2);
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
        CreateDonateRequest request1 = CreateDonateRequest.newInstance("패딩 나눔합니다", "image.png", "안 입는 패딩 기부해요", String.valueOf(Tag.TOP), 1L);
        CreateDonateRequest request2 = CreateDonateRequest.newInstance("바지 나눔합니다", "image.png", "안 입는 바지 기부해요", String.valueOf(Tag.PANTS), 2L);

        // when
        userService.join(user1);
        userService.join(user2);
        donateService.join(request1);
        donateService.join(request2);
        List<Donate> donateListDESC = donateService.findByCreatedDateDESC();

        // then
        assertEquals(request2.getTitle(), donateListDESC.get(0).getTitle());
    }

    @Test
    public void 전체_게시글_태그별_조회() throws Exception {

        // given
        User user = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", Role.GENERAL);
        CreateDonateRequest request1 = CreateDonateRequest.newInstance("패딩 나눔합니다", "image.png", "안 입는 패딩 기부해요", String.valueOf(Tag.TOP), 1L);
        CreateDonateRequest request2 = CreateDonateRequest.newInstance("바지 나눔합니다", "image.png", "안 입는 바지 기부해요", String.valueOf(Tag.PANTS), 1L);
        CreateDonateRequest request3 = CreateDonateRequest.newInstance("후드티 나눔합니다", "image.png", "안 입는 후드티 기부해요", String.valueOf(Tag.TOP), 1L);

        // when
        userService.join(user);
        donateService.join(request1);
        donateService.join(request2);
        donateService.join(request3);
        List<Donate> byTag = donateService.findByTag(Tag.TOP);

        // then
        assertEquals(2, byTag.size());
    }

    @Test
    public void 게시글_전체_수정() throws Exception {

        // given
        User user = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", Role.GENERAL);
        CreateDonateRequest request = CreateDonateRequest.newInstance("패딩 나눔합니다", "image.png", "안 입는 패딩 기부해요", String.valueOf(Tag.TOP), 1L);

        // when
        userService.join(user);
        Long donateId = donateService.join(request);

        UpdateDonateRequest updateRequest = UpdateDonateRequest.newInstance("바지 나눔합니다", "image.png", "안 입은 바지 기부해요", String.valueOf(Tag.PANTS), 1L);
        donateService.updateAll(updateRequest);
        Donate updateDonate = donateService.findById(donateId);

        // then
        assertEquals("바지 나눔합니다", updateDonate.getTitle());
        System.out.println(updateDonate.getView()); // 조회수 확인
    }

    @Test(expected = PostNotFoundException.class)
    public void 게시글_삭제() throws Exception {

        // given
        User user = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", Role.GENERAL);
        CreateDonateRequest request = CreateDonateRequest.newInstance("패딩 나눔합니다", "image.png", "안 입는 패딩 기부해요", String.valueOf(Tag.TOP), 1L);

        // when
        userService.join(user);
        Long donateId = donateService.join(request);

        donateService.deleteDonate(donateId);
        Donate deletedDonate = donateService.findById(donateId);

        // then
        assertEquals(request, deletedDonate);
    }
}

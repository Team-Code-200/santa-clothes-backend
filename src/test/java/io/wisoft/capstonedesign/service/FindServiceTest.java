package io.wisoft.capstonedesign.service;

import io.wisoft.capstonedesign.domain.find.application.FindService;
import io.wisoft.capstonedesign.domain.find.persistence.Find;
import io.wisoft.capstonedesign.domain.find.web.dto.CreateFindRequest;
import io.wisoft.capstonedesign.domain.find.web.dto.UpdateFindRequest;
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
public class FindServiceTest {

    @Autowired FindService findService;
    @Autowired UserService userService;

    @Test
    public void 게시글_작성() throws Exception {

        // given
        User user = User.newInstance(1L, "jinwon@gmail.com", "profile.png", 1000, "jinwon", Role.GENERAL);
        CreateFindRequest request = CreateFindRequest.newInstance("패딩 찾아봅니다", "image.png", "안 입는 패딩 기부받아요", String.valueOf(Tag.TOP), 1L);

        // when
        userService.join(user);
        Long savedId = findService.join(request);

        // then
        assertEquals(request.getTitle(), findService.findById(savedId).getTitle());
    }

    @Test
    public void 게시글_조회() throws Exception {

        // given
        User user = User.newInstance(1L, "jinwon@gmail.com", "profile.png", 1000, "jinwon", Role.GENERAL);
        CreateFindRequest request = CreateFindRequest.newInstance("패딩 찾아봅니다", "image.png", "안 입는 패딩 기부받아요", String.valueOf(Tag.TOP), 1L);

        // when
        userService.join(user);
        Long savedId = findService.join(request);
        Find savedFind = findService.findById(savedId);

        // then
        assertEquals(request.getTitle(), savedFind.getTitle());
    }

    @Test(expected = AssertionFailedError.class)
    public void 전체_게시글_조회() throws Exception {

        // given
        User user = User.newInstance(1L, "jinwon@gmail.com", "profile.png", 1000, "jinwon", Role.GENERAL);
        CreateFindRequest request1 = CreateFindRequest.newInstance("패딩 찾아봅니다", "image.png", "안 입는 패딩 기부받아요", String.valueOf(Tag.TOP), 1L);
        CreateFindRequest request2 = CreateFindRequest.newInstance("바지 찾아봅니다", "image.png", "안 입는 바지 기부받아요", String.valueOf(Tag.PANTS), 1L);

        // when
        userService.join(user);
        findService.join(request1);
        findService.join(request2);
        List<Find> finds = findService.findFinds();

        // then
        assertEquals(3, finds.size());
        fail("예외가 발생해야 한다!");
    }

    @Test
    public void 전체_게시글_최근순_조회() throws Exception {

        // given
        User user1 = User.newInstance(1L, "jinwon@gmail.com", "profile.png", 1000, "jinwon", Role.GENERAL);
        User user2 = User.newInstance(2L, "donggwon@gmail.com", "profile.png", 2000, "donggwon", Role.GENERAL);
        CreateFindRequest request1 = CreateFindRequest.newInstance("패딩 찾아봅니다", "image.png", "안 입는 패딩 기부받아요", String.valueOf(Tag.TOP), 1L);
        CreateFindRequest request2 = CreateFindRequest.newInstance("바지 찾아봅니다", "image.png", "안 입는 바지 기부받아요", String.valueOf(Tag.PANTS), 2L);

        // when
        userService.join(user1);
        userService.join(user2);
        findService.join(request1);
        findService.join(request2);
        List<Find> findListDESC = findService.findByCreatedDateDESC();

        // then
        assertEquals(request2.getTitle(), findListDESC.get(0).getTitle());
    }

    @Test
    public void 전체_게시글_태그별_조회() throws Exception {

        // given
        User user = User.newInstance(1L, "jinwon@gmail.com", "profile.png", 1000, "jinwon", Role.GENERAL);
        CreateFindRequest request1 = CreateFindRequest.newInstance("패딩 찾아봅니다", "image.png", "안 입는 패딩 기부받아요", String.valueOf(Tag.TOP), 1L);
        CreateFindRequest request2 = CreateFindRequest.newInstance("청바지 찾아봅니다", "image.png", "안 입는 바지 기부받아요", String.valueOf(Tag.PANTS), 1L);
        CreateFindRequest request3 = CreateFindRequest.newInstance("트레이닝 바지 찾아봅니다", "image.png", "안 입는 바지 기부받아요", String.valueOf(Tag.PANTS), 1L);

        // when
        userService.join(user);
        findService.join(request1);
        findService.join(request2);
        findService.join(request3);
        List<Find> byTag = findService.findByTag(Tag.PANTS);

        // then
        assertEquals(2, byTag.size());
    }

    @Test
    public void 게시글_전체_수정() throws Exception {

        // given
        User user = User.newInstance(1L, "jinwon@gmail.com", "profile.png", 1000, "jinwon", Role.GENERAL);
        CreateFindRequest request = CreateFindRequest.newInstance("패딩 찾아봅니다", "image.png", "안 입는 패딩 기부받아요", String.valueOf(Tag.TOP), 1L);
        // when
        userService.join(user);
        Long findId = findService.join(request);

        UpdateFindRequest updateRequest = UpdateFindRequest.newInstance("바지 찾아봅니다", "image.png", "안 입는 바지 기부받아요", String.valueOf(Tag.PANTS), 1L);
        findService.updateAll(updateRequest);
        Find updateFind = findService.findById(findId);

        // then
        assertEquals("바지 찾아봅니다", updateFind.getTitle());
        System.out.println(updateFind.getView()); // 조회수 확인
    }

    @Test(expected = PostNotFoundException.class)
    public void 게시글_삭제() throws Exception {

        // given
        User user = User.newInstance(1L, "jinwon@gmail.com", "profile1.png", 1000, "jinwon", Role.GENERAL);
        CreateFindRequest request = CreateFindRequest.newInstance("패딩 찾아봅니다", "image.png", "안 입는 패딩 기부받아요", String.valueOf(Tag.TOP), 1L);

        // when
        userService.join(user);
        Long findId = findService.join(request);

        findService.deleteFind(findId);
        Find deletedFind = findService.findById(findId);

        // then
        assertEquals(request, deletedFind);
    }
}

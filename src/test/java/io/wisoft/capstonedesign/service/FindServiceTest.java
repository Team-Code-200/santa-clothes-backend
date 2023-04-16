package io.wisoft.capstonedesign.service;

import io.wisoft.capstonedesign.domain.find.application.FindService;
import io.wisoft.capstonedesign.domain.find.persistence.Find;
import io.wisoft.capstonedesign.domain.find.web.dto.CreateFindRequest;
import io.wisoft.capstonedesign.domain.find.web.dto.UpdateFindRequest;
import io.wisoft.capstonedesign.domain.user.web.dto.CreateUserRequest;
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
        CreateUserRequest request1 = new CreateUserRequest("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", String.valueOf(Role.GENERAL));
        CreateFindRequest request2 = new CreateFindRequest("패딩 찾아봅니다", "image.png", "안 입는 패딩 기부받아요", String.valueOf(Tag.TOP), 1L);

        // when
        userService.join(request1);
        Long savedId = findService.join(request2);

        // then
        assertEquals(request2.getTitle(), findService.findById(savedId).getTitle());
    }

    @Test
    public void 게시글_조회() throws Exception {

        // given
        CreateUserRequest request1 = new CreateUserRequest("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", String.valueOf(Role.GENERAL));
        CreateFindRequest request2 = new CreateFindRequest("패딩 찾아봅니다", "image.png", "안 입는 패딩 기부받아요", String.valueOf(Tag.TOP), 1L);

        // when
        userService.join(request1);
        Long savedId = findService.join(request2);
        Find savedFind = findService.findById(savedId);

        // then
        assertEquals(request2.getTitle(), savedFind.getTitle());
    }

    @Test(expected = AssertionFailedError.class)
    public void 전체_게시글_조회() throws Exception {

        // given
        CreateUserRequest request1 = new CreateUserRequest("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", String.valueOf(Role.GENERAL));
        CreateFindRequest request2 = new CreateFindRequest("패딩 찾아봅니다", "image.png", "안 입는 패딩 기부받아요", String.valueOf(Tag.TOP), 1L);
        CreateFindRequest request3 = new CreateFindRequest("바지 찾아봅니다", "image.png", "안 입는 바지 기부받아요", String.valueOf(Tag.PANTS), 1L);

        // when
        userService.join(request1);
        findService.join(request2);
        findService.join(request3);
        List<Find> finds = findService.findFinds();

        // then
        assertEquals(3, finds.size());
        fail("예외가 발생해야 한다!");
    }

    @Test
    public void 전체_게시글_최근순_조회() throws Exception {

        // given
        CreateUserRequest request1 = new CreateUserRequest("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", String.valueOf(Role.GENERAL));
        CreateUserRequest request2 = new CreateUserRequest("2", "donggwon@gmail.com", "profile.png", 2000, "donggwon", String.valueOf(Role.GENERAL));
        CreateFindRequest request3 = new CreateFindRequest("패딩 찾아봅니다", "image.png", "안 입는 패딩 기부받아요", String.valueOf(Tag.TOP), 1L);
        CreateFindRequest request4 = new CreateFindRequest("바지 찾아봅니다", "image.png", "안 입는 바지 기부받아요", String.valueOf(Tag.PANTS), 2L);

        // when
        userService.join(request1);
        userService.join(request2);
        findService.join(request3);
        findService.join(request4);
        List<Find> findListDESC = findService.findByCreatedDateDESC();

        // then
        assertEquals(request4.getTitle(), findListDESC.get(0).getTitle());
    }

    @Test
    public void 전체_게시글_태그별_조회() throws Exception {

        // given
        CreateUserRequest request1 = new CreateUserRequest("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", String.valueOf(Role.GENERAL));
        CreateFindRequest request2 = new CreateFindRequest("패딩 찾아봅니다", "image.png", "안 입는 패딩 기부받아요", String.valueOf(Tag.TOP), 1L);
        CreateFindRequest request3 = new CreateFindRequest("청바지 찾아봅니다", "image.png", "안 입는 바지 기부받아요", String.valueOf(Tag.PANTS), 1L);
        CreateFindRequest request4 = new CreateFindRequest("트레이닝 바지 찾아봅니다", "image.png", "안 입는 바지 기부받아요", String.valueOf(Tag.PANTS), 1L);

        // when
        userService.join(request1);
        findService.join(request2);
        findService.join(request3);
        findService.join(request4);
        List<Find> byTag = findService.findByTag(Tag.PANTS);

        // then
        assertEquals(2, byTag.size());
    }

    @Test
    public void 게시글_전체_수정() throws Exception {

        // given
        CreateUserRequest request1 = new CreateUserRequest("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", String.valueOf(Role.GENERAL));
        CreateFindRequest request2 = new CreateFindRequest("패딩 찾아봅니다", "image.png", "안 입는 패딩 기부받아요", String.valueOf(Tag.TOP), 1L);

        // when
        userService.join(request1);
        Long findId = findService.join(request2);

        UpdateFindRequest updateRequest = new UpdateFindRequest("바지 찾아봅니다", "image.png", "안 입는 바지 기부받아요", String.valueOf(Tag.PANTS), 1L);
        findService.updateAll(findId, updateRequest);
        Find updateFind = findService.findById(findId);

        // then
        assertEquals("바지 찾아봅니다", updateFind.getTitle());
        System.out.println(updateFind.getView()); // 조회수 확인
    }

    @Test(expected = PostNotFoundException.class)
    public void 게시글_삭제() throws Exception {

        // given
        CreateUserRequest request1 = new CreateUserRequest("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", String.valueOf(Role.GENERAL));
        CreateFindRequest request2 = new CreateFindRequest("패딩 찾아봅니다", "image.png", "안 입는 패딩 기부받아요", String.valueOf(Tag.TOP), 1L);

        // when
        userService.join(request1);
        Long findId = findService.join(request2);

        findService.deleteFind(findId);
        Find deletedFind = findService.findById(findId);

        // then
        assertEquals(request2, deletedFind);
    }
}

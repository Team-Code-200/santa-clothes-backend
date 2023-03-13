package io.wisoft.capstonedesign.service;

import io.wisoft.capstonedesign.domain.*;
import io.wisoft.capstonedesign.repository.FindRepository;
import io.wisoft.capstonedesign.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
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
public class FindServiceTest {

    @Autowired FindRepository findRepository;
    @Autowired UserRepository userRepository;
    @Autowired FindService findService;
    @Autowired UserService userService;

    @Test
    public void 게시글_작성() throws Exception {

        // given
        User user = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", LocalDateTime.now(), Role.GENERAL);
        Find find = Find.createFind("패딩 찾아봅니다", LocalDateTime.now(), "안 입는 패딩 기부받아요", "image.png", 0, Tag.TOP, user);

        // when
        userService.join(user);
        Long savedId = findService.join(find);

        // then
        assertEquals(find, findRepository.findOne(savedId));
    }

    @Test
    public void 게시글_조회() throws Exception {

        // given
        User user = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", LocalDateTime.now(), Role.GENERAL);
        Find find = Find.createFind("패딩 찾아봅니다", LocalDateTime.now(), "image.png", "안 입는 패딩 기부받아요", 0, Tag.TOP, user);

        // when
        userService.join(user);
        Long savedId = findService.join(find);
        Find savedFind = findService.findOne(savedId);

        // then
        assertEquals(find, savedFind);
    }

    @Test(expected = AssertionFailedError.class)
    public void 전체_게시글_조회() throws Exception {

        // given
        User user = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", LocalDateTime.now(), Role.GENERAL);
        Find find1 = Find.createFind("패딩 찾아봅니다", LocalDateTime.now(), "안 입는 패딩 기부받아요", "image1", 0, Tag.TOP, user);
        Find find2 = Find.createFind("바지 찾아봅니다", LocalDateTime.now(), "안 입는 바지 기부받아요", "image2", 0, Tag.PANTS, user);

        // when
        userService.join(user);
        findService.join(find1);
        findService.join(find2);
        List<Find> donates = findService.findFinds();

        // then
        assertEquals(3, donates.size());
        fail("예외가 발생해야 한다!");
    }

    @Test
    public void 게시글_전체_수정() throws Exception {

        // given
        User user = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", LocalDateTime.now(), Role.GENERAL);
        Find find = Find.createFind("패딩 찾아봅니다", LocalDateTime.now(), "image1.png", "안 입는 패딩 기부받아요", 0, Tag.TOP, user);

        // when
        userService.join(user);
        Long findId = findService.join(find);

        findService.updateAll(findId, "바지 찾아봅니다", "image2.png", "안 입는 바지 기부받아요", Tag.PANTS);
        Find updateFind = findService.findOne(findId);

        // then
        assertEquals("바지 찾아봅니다", updateFind.getTitle());
        System.out.println(updateFind.getView()); // 조회수 확인
    }
}

package io.wisoft.capstonedesign.service;

import io.wisoft.capstonedesign.global.enumerated.Role;
import io.wisoft.capstonedesign.domain.user.application.UserService;
import io.wisoft.capstonedesign.domain.user.persistence.User;
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
public class UserServiceTest {

    @Autowired UserService userService;

    @Test
    public void 회원가입() throws Exception {

        // given
        User user = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", Role.GENERAL);

        // when
        Long saveId = userService.join(user);

        // then
        assertEquals(user, userService.findOne(saveId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_검사() throws Exception {

        // given
        User user1 = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", Role.GENERAL);
        User user2 = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", Role.GENERAL);

        // when
        userService.join(user1);
        userService.join(user2);

        // then
        fail("예외가 발생해야 한다!");
    }

    @Test
    public void 회원_조회() throws Exception {

        // given
        User user = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", Role.GENERAL);

        // when
        Long userId = userService.join(user);
        User savedUser = userService.findOne(userId);

        // then
        assertEquals(user, savedUser);
    }

    @Test(expected = AssertionFailedError.class)
    public void 전체_회원_조회() throws Exception {

        // given
        User user1 = User.newInstance("1", "jinwon@gmail.com", "profile1.png", 1000, "jinwon", Role.GENERAL);
        User user2 = User.newInstance("2", "minseok@gmail.com", "profile2.png", 1000, "minseok", Role.GENERAL);

        // when
        userService.join(user1);
        userService.join(user2);
        List<User> users = userService.findUsers();

        // then
        assertEquals(3, users.size());

    }

    @Test
    public void 회원_닉네임_수정() throws Exception {

        // given
        User user1 = User.newInstance("1", "jinwon@gmail.com", "profile1.png", 1000, "jinwon", Role.GENERAL);

        // when
        Long userId = userService.join(user1);
        userService.updateNickname(userId, "jinony");

        // then
        assertEquals("jinony", user1.getNickname());
    }

    @Test(expected = AssertionFailedError.class)
    public void 회원_탈퇴() throws Exception {

        // given
        User user = User.newInstance("1", "jinwon@gmail.com", "profile1.png", 1000, "jinwon", Role.GENERAL);

        // when
        Long userId = userService.join(user);
        userService.deleteUser(userId);
        User deletedUser = userService.findOne(userId);

        // then
        assertEquals(user, deletedUser);
    }
}

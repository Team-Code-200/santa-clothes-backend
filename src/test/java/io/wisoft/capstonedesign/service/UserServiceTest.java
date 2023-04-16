package io.wisoft.capstonedesign.service;

import io.wisoft.capstonedesign.domain.user.web.dto.CreateUserRequest;
import io.wisoft.capstonedesign.domain.user.web.dto.UpdateUserRequest;
import io.wisoft.capstonedesign.global.enumerated.Role;
import io.wisoft.capstonedesign.domain.user.application.UserService;
import io.wisoft.capstonedesign.domain.user.persistence.User;
import io.wisoft.capstonedesign.global.exception.service.UserDuplicateException;
import io.wisoft.capstonedesign.global.exception.service.UserNotFoundException;
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
public class UserServiceTest {

    @Autowired UserService userService;

    @Test
    public void 회원가입() throws Exception {

        // given
        CreateUserRequest request = new CreateUserRequest("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", String.valueOf(Role.GENERAL));

        // when
        Long saveId = userService.join(request);

        // then
        assertEquals(request.getNickname(), userService.findById(saveId).getNickname());
    }

    @Test(expected = UserDuplicateException.class)
    public void 중복_회원_검사() throws Exception {

        // given
        CreateUserRequest request1 = new CreateUserRequest("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", String.valueOf(Role.GENERAL));
        CreateUserRequest request2 = new CreateUserRequest("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", String.valueOf(Role.GENERAL));

        // when
        userService.join(request1);
        userService.join(request2);

        // then
        fail("예외가 발생해야 한다!");
    }

    @Test
    public void 회원_조회() throws Exception {

        // given
        CreateUserRequest request = new CreateUserRequest("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", String.valueOf(Role.GENERAL));

        // when
        Long userId = userService.join(request);
        User savedUser = userService.findById(userId);

        // then
        assertEquals(request.getNickname(), savedUser.getNickname());
    }

    @Test(expected = AssertionFailedError.class)
    public void 전체_회원_조회() throws Exception {

        // given
        CreateUserRequest request1 = new CreateUserRequest("1", "jinwon@gmail.com", "profile1.png", 1000, "jinwon", String.valueOf(Role.GENERAL));
        CreateUserRequest request2 = new CreateUserRequest("2", "minseok@gmail.com", "profile2.png", 1000, "minseok", String.valueOf(Role.GENERAL));

        // when
        userService.join(request1);
        userService.join(request2);
        List<User> users = userService.findUsers();

        // then
        assertEquals(3, users.size());

    }

    @Test
    public void 회원_닉네임_수정() throws Exception {

        // given
        CreateUserRequest request = new CreateUserRequest("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", String.valueOf(Role.GENERAL));

        // when
        UpdateUserRequest request1 = new UpdateUserRequest("jinony");
        Long userId = userService.join(request);
        userService.updateNickname(userId, request1);
        User updateUser = userService.findById(userId);

        // then
        assertEquals("jinony", updateUser.getNickname());
    }

    @Test(expected = UserNotFoundException.class)
    public void 회원_탈퇴() throws Exception {

        // given
        CreateUserRequest request = new CreateUserRequest("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", String.valueOf(Role.GENERAL));

        // when
        Long userId = userService.join(request);
        userService.deleteUser(userId);
        User deletedUser = userService.findById(userId);

        // then
        assertEquals(request.getNickname(), deletedUser.getNickname());
    }
}

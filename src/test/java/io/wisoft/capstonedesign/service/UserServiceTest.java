package io.wisoft.capstonedesign.service;

import io.wisoft.capstonedesign.domain.user.application.UserService;
import io.wisoft.capstonedesign.domain.user.persistence.User;
import io.wisoft.capstonedesign.domain.user.web.dto.CreateUserRequest;
import io.wisoft.capstonedesign.domain.user.web.dto.UpdateUserRequest;
import io.wisoft.capstonedesign.global.enumerated.Role;
import io.wisoft.capstonedesign.global.exception.service.UserDuplicateException;
import io.wisoft.capstonedesign.global.exception.service.UserNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static io.wisoft.capstonedesign.setting.data.DefaultUserData.createDefaultUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired UserService userService;

    @Nested
    @DisplayName("회원 가입 테스트")
    class Join {

        @Test
        @DisplayName("회원가입시 정상적으로 가입이 되야 한다.")
        void save_user() {

            // given
            CreateUserRequest request = createDefaultUser();

            // when
            Long savedId = userService.join(request);

            // then
            User user = userService.findById(savedId);
            assertEquals("jinwon", user.getNickname());
        }

        @Test
        @DisplayName("이메일 중복시 회원 가입이 실패되어야 한다.")
        void duplicate_user() {

            // given
            CreateUserRequest request1 = createDefaultUser();
            userService.join(request1);

            CreateUserRequest request2 = CreateUserRequest.builder()
                    .oauthId("1")
                    .email("jinwon@gmail.com")
                    .profileImage("profile.png")
                    .point(1000)
                    .nickname("jinwon")
                    .userRole(String.valueOf(Role.GENERAL))
                    .build();

            // when

            // then
            assertThrows(UserDuplicateException.class, () -> userService.join(request2));
        }
    }

    @Nested
    @DisplayName("회원 조회 테스트")
    class FindUser {

        @Test
        @DisplayName("회원 단건 조회 요청시 한 명의 회원이 조회되어야 한다.")
        void find_single_user() {

            // given
            CreateUserRequest request = createDefaultUser();
            Long savedId = userService.join(request);

            // when
            User user = userService.findById(savedId);

            // then
            assertEquals("jinwon@gmail.com", user.getEmail());
            assertEquals("jinwon", user.getNickname());
        }

        @Test
        @DisplayName("존재하지 않는 회원을 조회시 예외가 발생해야 한다.")
        void find_single_user_fail() {

            // given
            CreateUserRequest request = createDefaultUser();
            userService.join(request);

            // when

            // then
            assertThrows(UserNotFoundException.class, () -> userService.findById(100L));
        }

        @Test
        @DisplayName("전체 회원 조회시 전체 회원 목록을 반환해야 한다.")
        void find_users() {

            // given
            CreateUserRequest request = createDefaultUser();
            userService.join(request);

            CreateUserRequest request2 = CreateUserRequest.builder()
                    .oauthId("2")
                    .email("donggwon@gmail.com")
                    .profileImage("profile.png")
                    .point(1000)
                    .nickname("donggwon")
                    .userRole(String.valueOf(Role.GENERAL))
                    .build();
            userService.join(request2);

            // when
            List<User> users = userService.findUsers();

            // then
            assertEquals(2, users.size());
            assertEquals("jinwon", users.get(0).getNickname());
        }
    }

    @Nested
    @DisplayName("회원 수정 테스트")
    class UpdateUser {

        @Test
        @DisplayName("회원 닉네임 수정 요청시 정상적으로 수정되어야 한다.")
        void update_user_nickname() {

            // given
            CreateUserRequest request = createDefaultUser();
            Long userId = userService.join(request);

            UpdateUserRequest updateRequest = new UpdateUserRequest("jinony");

            // when
            userService.updateNickname(userId, updateRequest);
            User updateUser = userService.findById(userId);

            // then
            assertEquals("jinony", updateUser.getNickname());
        }

        @Test
        @DisplayName("자신이 아닌 회원의 닉네임을 수정시 예외가 발생해야 한다.")
        void update_user_nickname_fail() {

            // given
            CreateUserRequest request = createDefaultUser();
            userService.join(request);

            UpdateUserRequest updateRequest = new UpdateUserRequest("jinony");

            // when

            // then
            assertThrows(UserNotFoundException.class, () -> userService.updateNickname(100L, updateRequest));
        }
    }

    @Nested
    @DisplayName("회원 탈퇴 테스트")
    class DeleteUser {

        @Test
        @DisplayName("회원 탈퇴시 회원이 삭제되어야 한다.")
        void 한delete_user() {

            // given
            CreateUserRequest request = createDefaultUser();
            Long userId = userService.join(request);

            // when
            userService.deleteUser(userId);

            // then
            assertThrows(UserNotFoundException.class, () -> userService.findById(userId));
        }

        @Test
        @DisplayName("자신이 아닌 회원을 탈퇴하려 했을시 예외가 발생해야 한다.")
        void delete_user_fail() {

            // given
            CreateUserRequest request = createDefaultUser();
            userService.join(request);

            // when

            // then
            assertThrows(UserNotFoundException.class, () -> userService.deleteUser(100L));
        }
    }
}

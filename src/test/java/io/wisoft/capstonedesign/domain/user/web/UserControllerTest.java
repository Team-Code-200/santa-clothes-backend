package io.wisoft.capstonedesign.domain.user.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.wisoft.capstonedesign.domain.donate.application.DonateService;
import io.wisoft.capstonedesign.domain.donate.web.dto.CreateDonateRequest;
import io.wisoft.capstonedesign.domain.user.application.UserService;
import io.wisoft.capstonedesign.domain.user.web.dto.CreateUserRequest;
import io.wisoft.capstonedesign.domain.user.web.dto.UpdateUserRequest;
import io.wisoft.capstonedesign.global.enumerated.Role;
import io.wisoft.capstonedesign.global.jwt.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static io.wisoft.capstonedesign.setting.data.DefaultDonateData.createDefaultDonate;
import static io.wisoft.capstonedesign.setting.data.DefaultUserData.createDefaultUser;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerTest {

    @Autowired private UserService userService;
    @Autowired private DonateService donateService;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private MockMvc mockMvc;
    @Autowired private JwtTokenProvider jwtTokenProvider;

    @Nested
    @DisplayName("회원 가입 API 테스트")
    class JoinUser {

        @Test
        @DisplayName("회원가입 요청시 DB에 저장이 되어야 한다.")
        void user_join() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();

            String json = objectMapper.writeValueAsString(userRequest);

            // expected
            mockMvc.perform(post("/api/auth/signup")
                            .contentType(APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").exists())
                    .andDo(print());
        }
    }

    @Nested
    @DisplayName("회원 수정 API 테스트")
    class UpdateUser {

        @Test
        @DisplayName("회원 닉네임 수정 요청시 정상적으로 수정되어 반영되어야 한다.")
        void update_user() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(userId));

            UpdateUserRequest updateRequest = new UpdateUserRequest("jinony");
            String json = objectMapper.writeValueAsString(updateRequest);

            // expected
            mockMvc.perform(patch("/api/users/{id}", userId)
                            .contentType(APPLICATION_JSON)
                            .header("Authorization", "bearer " + accessToken)
                            .content(json))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.nickname").value("jinony"))
                    .andDo(print());
        }

        @Test
        @DisplayName("로그인 하지 않은 사용자가 닉네임 수정 요청시 예외가 발생해야 한다.")
        void update_user_fail1() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            UpdateUserRequest updateRequest = new UpdateUserRequest("jinony");
            String json = objectMapper.writeValueAsString(updateRequest);

            // expected
            mockMvc.perform(patch("/api/users/{id}", userId)
                            .contentType(APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isUnauthorized())
                    .andDo(print());
        }

        @Test
        @DisplayName("회원 닉네임 수정 요청시 닉네임 입력은 필수다.")
        void update_user_fail2() throws Exception {

            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(userId));

            UpdateUserRequest updateRequest = new UpdateUserRequest("   ");
            String json = objectMapper.writeValueAsString(updateRequest);

            // expected
            mockMvc.perform(patch("/api/users/{id}", userId)
                            .contentType(APPLICATION_JSON)
                            .header("Authorization", "bearer " + accessToken)
                            .content(json))
                    .andExpect(status().isBadRequest())
                    .andDo(print());
        }
    }

    @Nested
    @DisplayName("회원 조회 API 테스트")
    class FindUser {

        @Test
        @DisplayName("전체 회원 조회시 전체 회원 목록을 반환한다.")
        void find_users() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(userId));

            CreateUserRequest request2 = CreateUserRequest.builder()
                    .oauthId("2")
                    .email("donggwon@gmail.com")
                    .profileImage("profile.png")
                    .point(1000)
                    .nickname("donggwon")
                    .userRole(String.valueOf(Role.GENERAL))
                    .build();
            userService.join(request2);

            // expected
            mockMvc.perform(get("/api/users")
                            .contentType(APPLICATION_JSON)
                            .header("Authorization", "bearer " + accessToken))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.length()", is(2)))
                    .andDo(print());
        }

        @Test
        @DisplayName("로그인 하지 않은 사용자가 전체 회원 조회시 예외가 발생해야 한다.")
        void find_users_fail() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            userService.join(userRequest);

            CreateUserRequest request2 = CreateUserRequest.builder()
                    .oauthId("2")
                    .email("donggwon@gmail.com")
                    .profileImage("profile.png")
                    .point(1000)
                    .nickname("donggwon")
                    .userRole(String.valueOf(Role.GENERAL))
                    .build();
            userService.join(request2);

            // expected
            mockMvc.perform(get("/api/users")
                            .contentType(APPLICATION_JSON))
                    .andExpect(status().isUnauthorized())
                    .andDo(print());
        }

        @Test
        @DisplayName("회원 단건 조회 요청시 요청한 한 건의 회원 조회되어야 한다.")
        void find_single_user() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            // expected
            mockMvc.perform(get("/api/users/details/{id}", userId)
                            .contentType(APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.nickname").value("jinwon"))
                    .andDo(print());
        }

        @Test
        @DisplayName("존재하지 않는 회원 정보를 조회시 예외가 발생해야 한다.")
        void find_single_user_fail() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            userService.join(userRequest);

            // expected
            mockMvc.perform(get("/api/users/details/{id}", 1000L)
                            .contentType(APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andDo(print());
        }
    }

    @Nested
    @DisplayName("회원 탈퇴 API 테스트")
    class DeleteUser {

        @Test
        @DisplayName("회원 탈퇴 요청시 정상적으로 DB에서 삭제되어야 한다.")
        void delete_user() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(userId));

            // expected
            mockMvc.perform(delete("/api/users/{id}", userId)
                            .contentType(APPLICATION_JSON)
                            .header("Authorization", "bearer " + accessToken))
                    .andExpect(status().isOk())
                    .andDo(print());
        }

        @Test
        @DisplayName("회원이 아닌 사용자가 회원 탈퇴 요청시 에외가 발생해야 한다.")
        void delete_users_fail1() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            // expected
            mockMvc.perform(delete("/api/users/{id}", userId)
                            .contentType(APPLICATION_JSON))
                    .andExpect(status().isUnauthorized())
                    .andDo(print());
        }

        @Test
        @DisplayName("존재하지 않는 회원을 탈퇴하려 할시 예외가 발생해야 한다.")
        void delete_user_fail2() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(userId));

            // expected
            mockMvc.perform(delete("/api/users/{id}", 1000L)
                            .contentType(APPLICATION_JSON)
                            .header("Authorization", "bearer " + accessToken))
                    .andExpect(status().isNotFound())
                    .andDo(print());
        }
    }

    @Nested
    @DisplayName("회원 개인 정보(마이 페이지) 조회 API 테스트")
    class UserMyInfo {

        @Test
        @DisplayName("마이 페이지에서 회원이 자신의 게시글을 조회했을 때 자신이 작성한 게시글이 조회되어야 한다.")
        void find_my_info_post() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(userId));

            CreateDonateRequest donateRequest = createDefaultDonate(userId);
            donateService.join(donateRequest);

            // expected
            mockMvc.perform(get("/api/users/{id}/myinfo/donates", userId)
                            .contentType(APPLICATION_JSON)
                            .header("Authorization", "bearer " + accessToken))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content.length()", is(1)))
                    .andDo(print());
        }
    }
}
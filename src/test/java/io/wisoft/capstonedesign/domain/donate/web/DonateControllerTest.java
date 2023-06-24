package io.wisoft.capstonedesign.domain.donate.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.wisoft.capstonedesign.domain.donate.application.DonateService;
import io.wisoft.capstonedesign.domain.donate.web.dto.CreateDonateRequest;
import io.wisoft.capstonedesign.domain.donate.web.dto.UpdateDonateRequest;
import io.wisoft.capstonedesign.domain.user.application.UserService;
import io.wisoft.capstonedesign.domain.user.web.dto.CreateUserRequest;
import io.wisoft.capstonedesign.global.enumerated.Role;
import io.wisoft.capstonedesign.global.enumerated.Tag;
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
class DonateControllerTest {

    @Autowired private UserService userService;
    @Autowired private DonateService donateService;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private MockMvc mockMvc;
    @Autowired private JwtTokenProvider jwtTokenProvider;

    @Nested
    @DisplayName("게시글 작성 API 테스트")
    class CreatePost {

        @Test
        @DisplayName("게시글 작성 요청시 정상적으로 DB에 저장되어야 한다.")
        void create_post() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(userId));

            CreateDonateRequest donateRequest = createDefaultDonate(userId);
            String json = objectMapper.writeValueAsString(donateRequest);

            // expected
            mockMvc.perform(post("/api/donates/new")
                            .contentType(APPLICATION_JSON)
                            .header("Authorization", "bearer " + accessToken)
                            .content(json))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").exists())
                    .andDo(print());
        }

        @Test
        @DisplayName("회원이 아닌 사용자가 글 작성시 예외가 발생해야 한다.")
        void create_post_fail1() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateDonateRequest donateRequest = createDefaultDonate(userId);
            String json = objectMapper.writeValueAsString(donateRequest);

            // expected
            mockMvc.perform(post("/api/donates/new")
                            .contentType(APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isUnauthorized())
                    .andDo(print());
        }

        @Test
        @DisplayName("게시글 작성 요청시 제목은 필수다.")
        void create_post_fail2() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(userId));

            CreateDonateRequest donateRequest = CreateDonateRequest.builder()
                    .title(null)
                    .image("image.png")
                    .text("안 입은 패딩 기부해요")
                    .tag(String.valueOf(Tag.TOP))
                    .userId(userId)
                    .build();

            String json = objectMapper.writeValueAsString(donateRequest);

            // expected
            mockMvc.perform(post("/api/donates/new")
                            .contentType(APPLICATION_JSON)
                            .header("Authorization", "bearer " + accessToken)
                            .content(json))
                    .andExpect(status().isBadRequest())
                    .andDo(print());
        }
    }

    @Nested
    @DisplayName("게시글 조회 API 테스트")
    class FindPost {

        @Test
        @DisplayName("게시글 전체 조회시 전체 게시글을 페이징된 목록으로 반환해야 한다.")
        void find_posts() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateDonateRequest donateRequest1 = createDefaultDonate(userId);
            donateService.join(donateRequest1);

            CreateDonateRequest donateRequest2 = createDefaultDonate(userId);
            donateService.join(donateRequest2);

            // expected
            mockMvc.perform(get("/api/donates?page=0&size=5")
                            .contentType(APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content.length()", is(2)))
                    .andDo(print());

        }

        @Test
        @DisplayName("게시글 단건 조회 요청시 요청한 게시글이 조회되어야 한다.")
        void find_single_post() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateDonateRequest donateRequest = createDefaultDonate(userId);
            Long donateId = donateService.join(donateRequest);

            // expected
            mockMvc.perform(get("/api/donates/details/{id}", donateId)
                            .contentType(APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.title").value("패딩 나눔합니다."))
                    .andExpect(jsonPath("$.nickname").value(userRequest.nickname()))
                    .andDo(print());
        }

        @Test
        @DisplayName("존재하지 않는 게시글을 조회시 예외가 발생해야 한다.")
        void find_single_post_fail() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateDonateRequest donateRequest = createDefaultDonate(userId);
            donateService.join(donateRequest);

            // expected
            mockMvc.perform(get("/api/donates/details/{id}", 1000L)
                            .contentType(APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andDo(print());
        }

        @Test
        @DisplayName("특정 작성자의 게시글을 조회시 작성자의 게시글만 조회되어야 한다.")
        void find_posts_by_user() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateDonateRequest donateRequest = createDefaultDonate(userId);
            donateService.join(donateRequest);

            CreateUserRequest request2 = CreateUserRequest.builder()
                    .oauthId("2")
                    .email("donggwon@gmail.com")
                    .profileImage("profile.png")
                    .point(1000)
                    .nickname("donggwon")
                    .userRole(String.valueOf(Role.GENERAL))
                    .build();
            userService.join(request2);

            CreateDonateRequest donateRequest2 = CreateDonateRequest.builder()
                    .title("바지 나눔합니다.")
                    .image("image.png")
                    .text("안 입는 바지 기부해요")
                    .tag(String.valueOf(Tag.PANTS))
                    .userId(userId)
                    .build();
            donateService.join(donateRequest2);

            // expected
            mockMvc.perform(get("/api/donates/author/{id}", userId)
                            .contentType(APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.length()", is(2)))
                    .andDo(print());
        }

        @Test
        @DisplayName("존재하지 않는 회원의 게시글을 조회 요청시 예외가 발생해야 한다.")
        void find_posts_by_user_fail() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateDonateRequest donateRequest = createDefaultDonate(userId);
            donateService.join(donateRequest);

            // expected
            mockMvc.perform(get("/api/donates/author/{id}", 1000L)
                            .contentType(APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andDo(print());
        }

        @Test
        @DisplayName("전체 게시글을 태그별로 조회시 태그에 맞는 게시글만 조회되야 한다.")
        void find_posts_by_tag() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateDonateRequest donateRequest = createDefaultDonate(userId);
            donateService.join(donateRequest);

            // expected
            mockMvc.perform(get("/api/donates/top")
                            .contentType(APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.length()", is(1)))
                    .andDo(print());
        }
    }

    @Nested
    @DisplayName("게시글 수정 API 테스트")
    class UpdatePost {

        @Test
        @DisplayName("게시글 수정시 게시글이 수정되어 DB에 반영되어야 한다.")
        void update_post() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(userId));

            CreateDonateRequest donateRequest = createDefaultDonate(userId);
            Long donateId = donateService.join(donateRequest);

            UpdateDonateRequest updateRequest = UpdateDonateRequest.builder()
                    .title("바지 나눔합니다")
                    .image("image.png")
                    .text("안 입는 바지 기부해요")
                    .tag(String.valueOf(Tag.PANTS))
                    .donateId(donateId)
                    .build();

            String json = objectMapper.writeValueAsString(updateRequest);

            // expected
            mockMvc.perform(patch("/api/donates/{id}", donateId)
                            .contentType(APPLICATION_JSON)
                            .header("Authorization", "bearer " + accessToken)
                            .content(json))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.title").value("바지 나눔합니다"))
                    .andDo(print());
        }

        @Test
        @DisplayName("존재하지 않는 게시글을 수정시 예외가 발생해야 한다.")
        void update_post_fail1() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(userId));

            CreateDonateRequest donateRequest = createDefaultDonate(userId);
            donateService.join(donateRequest);

            UpdateDonateRequest updateRequest = UpdateDonateRequest.builder()
                    .title("바지 나눔합니다")
                    .image("image.png")
                    .text("안 입는 바지 기부해요")
                    .tag(String.valueOf(Tag.PANTS))
                    .donateId(1000L)
                    .build();

            String json = objectMapper.writeValueAsString(updateRequest);

            // expected
            mockMvc.perform(patch("/api/donates/{id}", 1000L)
                            .contentType(APPLICATION_JSON)
                            .header("Authorization", "bearer " + accessToken)
                            .content(json))
                    .andExpect(status().isNotFound())
                    .andDo(print());
        }

        @Test
        @DisplayName("게시글 수정 요청시 제목은 필수다.")
        void update_post_fail2() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(userId));

            CreateDonateRequest donateRequest = createDefaultDonate(userId);
            Long donateId = donateService.join(donateRequest);

            UpdateDonateRequest updateRequest = UpdateDonateRequest.builder()
                    .title("")
                    .image("image.png")
                    .text("안 입는 바지 기부해요")
                    .tag(String.valueOf(Tag.PANTS))
                    .donateId(donateId)
                    .build();

            String json = objectMapper.writeValueAsString(updateRequest);

            // expected
            mockMvc.perform(patch("/api/donates/{id}", donateId)
                            .contentType(APPLICATION_JSON)
                            .header("Authorization", "bearer " + accessToken)
                            .content(json))
                    .andExpect(status().isBadRequest())
                    .andDo(print());
        }

        @Test
        @DisplayName("로그인하지 않은 사용자가 수정 요청시 예외가 발생해야 한다.")
        void update_post_fail3() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateDonateRequest donateRequest = createDefaultDonate(userId);
            Long donateId = donateService.join(donateRequest);

            UpdateDonateRequest updateRequest = UpdateDonateRequest.builder()
                    .title("바지 나눔합니다")
                    .image("image.png")
                    .text("안 입는 바지 기부해요")
                    .tag(String.valueOf(Tag.PANTS))
                    .donateId(donateId)
                    .build();

            String json = objectMapper.writeValueAsString(updateRequest);

            // expected
            mockMvc.perform(patch("/api/donates/{id}", donateId)
                            .contentType(APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isUnauthorized())
                    .andDo(print());
        }
    }

    @Nested
    @DisplayName("게시글 삭제 API 테스트")
    class DeletePost {

        @Test
        @DisplayName("게시글 삭제 요청시 정상적으로 삭제되어야 한다.")
        void delete_post() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(userId));

            CreateDonateRequest donateRequest = createDefaultDonate(userId);
            Long donateId = donateService.join(donateRequest);

            // expected
            mockMvc.perform(delete("/api/donates/{id}", donateId)
                            .contentType(APPLICATION_JSON)
                            .header("Authorization", "bearer " + accessToken))
                    .andExpect(status().isOk())
                    .andDo(print());
        }

        @Test
        @DisplayName("회원이 아닌 사용자가 글 삭제 요청시 예외가 발생해야 한다.")
        void delete_post_fail1() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateDonateRequest donateRequest = createDefaultDonate(userId);
            Long donateId = donateService.join(donateRequest);

            // expected
            mockMvc.perform(delete("/api/donates/{id}", donateId)
                            .contentType(APPLICATION_JSON))
                    .andExpect(status().isUnauthorized())
                    .andDo(print());
        }

        @Test
        @DisplayName("존재하지 않는 게시글을 삭제 요청시 예외가 발생해야 한다.")
        void delete_post_fail2() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(userId));

            CreateDonateRequest donateRequest = createDefaultDonate(userId);
            donateService.join(donateRequest);

            // expected
            mockMvc.perform(delete("/api/donates/{id}", 1000L)
                            .contentType(APPLICATION_JSON)
                            .header("Authorization", "bearer " + accessToken))
                    .andExpect(status().isNotFound())
                    .andDo(print());
        }
    }
}
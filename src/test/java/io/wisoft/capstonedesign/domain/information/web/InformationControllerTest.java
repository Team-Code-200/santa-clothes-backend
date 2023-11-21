package io.wisoft.capstonedesign.domain.information.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.wisoft.capstonedesign.domain.address.persistence.Address;
import io.wisoft.capstonedesign.domain.information.application.InformationService;
import io.wisoft.capstonedesign.domain.information.web.dto.CreateInformationRequest;
import io.wisoft.capstonedesign.domain.information.web.dto.UpdateInformationRequest;
import io.wisoft.capstonedesign.domain.user.application.UserService;
import io.wisoft.capstonedesign.domain.user.web.dto.CreateUserRequest;
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

import static io.wisoft.capstonedesign.setting.data.DefaultInfoData.createDefaultInfo;
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
class InformationControllerTest {

    @Autowired private UserService userService;
    @Autowired private InformationService informationService;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private MockMvc mockMvc;
    @Autowired private JwtTokenProvider jwtTokenProvider;

    @Nested
    @DisplayName("배송정보 생성 API 테스트")
    class CreateInfo {

        @Test
        @DisplayName("배송정보 생성 요청시 정상적으로 DB에 저장되어야 한다.")
        void create_info() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(userId));

            CreateInformationRequest infoRequest = createDefaultInfo(userId);
            String json = objectMapper.writeValueAsString(infoRequest);

            // expected
            mockMvc.perform(post("/api/informations/new")
                            .contentType(APPLICATION_JSON)
                            .header("Authorization", "bearer " + accessToken)
                            .content(json))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").exists())
                    .andDo(print());
        }

        @Test
        @DisplayName("회원이 아닌 사용자가 배송정보 입력시 예외가 발생해야 한다.")
        void create_info_fail1() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);


            CreateInformationRequest infoRequest = createDefaultInfo(userId);
            informationService.save(infoRequest);

            // expected
            mockMvc.perform(post("/api/informations/new")
                            .contentType(APPLICATION_JSON))
                    .andExpect(status().isUnauthorized())
                    .andDo(print());
        }

        @Test
        @DisplayName("배송정보 생성시 모든 필드는 필수다.")
        void create_info_fail2() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(userId));

            CreateInformationRequest infoRequest = CreateInformationRequest.builder()
                    .username("윤진원")
                    .phoneNumber(null)
                    .build();
            String json = objectMapper.writeValueAsString(infoRequest);

            // expected
            mockMvc.perform(post("/api/informations/new")
                            .contentType(APPLICATION_JSON)
                            .header("Authorization", "bearer " + accessToken)
                            .content(json))
                    .andExpect(status().isBadRequest())
                    .andDo(print());
        }
    }

    @Nested
    @DisplayName("배송정보 조회 API 테스트")
    class FindInfo {

        @Test
        @DisplayName("배송정보 단건 조회 요청시 요청한 배송정보가 조회되어야 한다.")
        void find_single_info() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(userId));

            CreateInformationRequest infoRequest = createDefaultInfo(userId);
            Long infoId = informationService.save(infoRequest);

            // expected
            mockMvc.perform(get("/api/informations/{id}", infoId)
                            .contentType(APPLICATION_JSON)
                            .header("Authorization", "bearer " + accessToken))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.username").value("윤진원"))
                    .andExpect(jsonPath("$.address").value("한밭대학교 wisoftN5-503대전광역시 유성구 동서대로 12534159"))
                    .andDo(print());
        }

        @Test
        @DisplayName("존재하지 않는 배송정보를 조회시 예외가 발생해야 한다.")
        void find_single_info_fail1() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(userId));

            CreateInformationRequest infoRequest = createDefaultInfo(userId);
            informationService.save(infoRequest);

            // expected
            mockMvc.perform(get("/api/informations/{id}", 1000L)
                            .contentType(APPLICATION_JSON)
                            .header("Authorization", "bearer " + accessToken))
                    .andExpect(status().isNotFound())
                    .andDo(print());
        }

        @Test
        @DisplayName("회원이 아닌 사용자가 배송정보 조회시 예외가 발생해야 한다.")
        void find_single_info_fail2() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);


            CreateInformationRequest infoRequest = createDefaultInfo(userId);
            Long infoId = informationService.save(infoRequest);

            // expected
            mockMvc.perform(get("/api/informations/{id}", infoId)
                            .contentType(APPLICATION_JSON))
                    .andExpect(status().isUnauthorized())
                    .andDo(print());
        }

        @Test
        @DisplayName("특정 사용자의 배송정보를 조회시 그 사용자의 배송정보만 조회되어야 한다.")
        void find_infos_by_user() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateUserRequest request2 = CreateUserRequest.builder()
                    .oauthId("2")
                    .email("donggwon@gmail.com")
                    .profileImage("profile.png")
                    .point(1000)
                    .nickname("donggwon")
                    .userRole(String.valueOf(Role.GENERAL))
                    .build();
            Long userId2 = userService.join(request2);

            String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(userId));

            CreateInformationRequest infoRequest = createDefaultInfo(userId);
            informationService.save(infoRequest);

            CreateInformationRequest infoRequest2 = CreateInformationRequest.builder()
                    .username("윤진원")
                    .address(Address.translateAddressToString(Address.createAddress("34159","대전광역시 유성구 동서대로 125","한밭대학교 wisoft","N5-503")))
                    .phoneNumber("010-0000-0000")
                    .userId(userId)
                    .build();
            informationService.save(infoRequest2);

            CreateInformationRequest infoRequest3 = CreateInformationRequest.builder()
                    .username("서동권")
                    .address(Address.translateAddressToString(Address.createAddress("34159","대전광역시 유성구 동서대로 125","한밭대학교 wisoft","N5-503")))
                    .phoneNumber("010-0000-0000")
                    .userId(userId2)
                    .build();
            informationService.save(infoRequest3);

            // expected
            mockMvc.perform(get("/api/informations/user/{id}", userId)
                            .contentType(APPLICATION_JSON)
                            .header("Authorization", "bearer " + accessToken))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.length()", is(2)))
                    .andDo(print());
        }

        @Test
        @DisplayName("존재하지 않는 회원의 배송정보를 조회 요청시 예외가 발생해야 한다.")
        void find_infos_by_user_fail() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(userId));

            CreateInformationRequest infoRequest = createDefaultInfo(userId);
            informationService.save(infoRequest);

            // expected
            mockMvc.perform(get("/api/informations/user/{id}", 1000L)
                            .contentType(APPLICATION_JSON)
                            .header("Authorization", "bearer " + accessToken))
                    .andExpect(status().isNotFound())
                    .andDo(print());
        }

        @Test
        @DisplayName("배송정보 전체 조회시 전체 배송정보를 페이징하여 반환해야 한다.")
        void find_infos() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(userId));

            CreateInformationRequest infoRequest1 = createDefaultInfo(userId);
            informationService.save(infoRequest1);

            CreateInformationRequest infoRequest2 = createDefaultInfo(userId);
            informationService.save(infoRequest2);

            // expected
            mockMvc.perform(get("/api/informations?page=0&size=5")
                            .contentType(APPLICATION_JSON)
                            .header("Authorization", "bearer " + accessToken))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content.length()", is(2)))
                    .andDo(print());
        }
    }

    @Nested
    @DisplayName("배송정보 수정 API 테스트")
    class UpdateInfo {

        @Test
        @DisplayName("배송정보 수정시 배송정보가 수정되어 DB에 반영되어야 한다.")
        void update_info() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(userId));

            CreateInformationRequest infoRequest = createDefaultInfo(userId);
            Long infoId = informationService.save(infoRequest);

            UpdateInformationRequest updateRequest = UpdateInformationRequest.builder()
                    .username("지노니")
                    .address(Address.translateAddressToString(Address.createAddress("34159","대전광역시 유성구 동서대로 125","한밭대학교 wisoft","N5-503")))
                    .phoneNumber("010-0000-0000")
                    .build();

            String json = objectMapper.writeValueAsString(updateRequest);

            // expected
            mockMvc.perform(patch("/api/informations/{id}", infoId)
                            .contentType(APPLICATION_JSON)
                            .header("Authorization", "bearer " + accessToken)
                            .content(json))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.address").value("한밭대학교 wisoftN5-503대전광역시 유성구 동서대로 12534159"))
                    .andDo(print());
        }

        @Test
        @DisplayName("존재하지 않는 배송정보를 수정시 예외가 발생해야 한다.")
        void update_info_fail1() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(userId));

            CreateInformationRequest infoRequest = createDefaultInfo(userId);
            informationService.save(infoRequest);

            UpdateInformationRequest updateRequest = UpdateInformationRequest.builder()
                    .username("지노니")
                    .address(String.valueOf(Address.createAddress("34159","대전광역시 유성구 동서대로 125","한밭대학교 wisoft","N5-503")))
                    .phoneNumber("010-0000-0000")
                    .build();

            String json = objectMapper.writeValueAsString(updateRequest);

            // expected
            mockMvc.perform(patch("/api/informations/{id}", 1000L)
                            .contentType(APPLICATION_JSON)
                            .header("Authorization", "bearer " + accessToken)
                            .content(json))
                    .andExpect(status().isNotFound())
                    .andDo(print());
        }

        @Test
        @DisplayName("배송정보 수정 요청시 모든 필드는 필수다.")
        void update_info_fail2() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(userId));

            CreateInformationRequest infoRequest = createDefaultInfo(userId);
            Long infoId = informationService.save(infoRequest);

            UpdateInformationRequest updateRequest = UpdateInformationRequest.builder()
                    .username("   ")
                    .address(null)
                    .phoneNumber("010-0000-0000")
                    .build();

            String json = objectMapper.writeValueAsString(updateRequest);

            // expected
            mockMvc.perform(patch("/api/informations/{id}", infoId)
                            .contentType(APPLICATION_JSON)
                            .header("Authorization", "bearer " + accessToken)
                            .content(json))
                    .andExpect(status().isBadRequest())
                    .andDo(print());
        }

        @Test
        @DisplayName("로그인하지 않은 사용자가 수정 요청시 예외가 발생해야 한다.")
        void update_info_fail3() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateInformationRequest infoRequest = createDefaultInfo(userId);
            informationService.save(infoRequest);

            UpdateInformationRequest updateRequest = UpdateInformationRequest.builder()
                    .username("지노니")
                    .address(String.valueOf(Address.createAddress("34159","대전광역시 유성구 동서대로 125","한밭대학교 wisoft","N5-503")))
                    .phoneNumber("010-0000-0000")
                    .build();

            String json = objectMapper.writeValueAsString(updateRequest);

            // expected
            mockMvc.perform(patch("/api/informations/{id}", 1000L)
                            .contentType(APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isUnauthorized())
                    .andDo(print());
        }
    }

    @Nested
    @DisplayName("배송정보 삭제 API 테스트")
    class DeleteInfo {

        @Test
        @DisplayName("배송정보 삭제 요청시 정상적으로 삭제되어야 한다.")
        void delete_info() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(userId));

            CreateInformationRequest infoRequest = createDefaultInfo(userId);
            Long infoId = informationService.save(infoRequest);

            // expected
            mockMvc.perform(delete("/api/informations/{id}", infoId)
                            .contentType(APPLICATION_JSON)
                            .header("Authorization", "bearer " + accessToken))
                    .andExpect(status().isOk())
                    .andDo(print());
        }

        @Test
        @DisplayName("회원이 아닌 사용자가 배송정보 삭제 요청시 예외가 발생해야 한다.")
        void delete_info_fail1() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);


            CreateInformationRequest infoRequest = createDefaultInfo(userId);
            Long infoId = informationService.save(infoRequest);

            // expected
            mockMvc.perform(delete("/api/informations/{id}", infoId)
                            .contentType(APPLICATION_JSON))
                    .andExpect(status().isUnauthorized())
                    .andDo(print());
        }

        @Test
        @DisplayName("존재하지 않는 배송정보를 삭제 요청시 에외가 발생해야 한다.")
        void delete_info_fail2() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(userId));

            CreateInformationRequest infoRequest = createDefaultInfo(userId);
            informationService.save(infoRequest);

            // expected
            mockMvc.perform(delete("/api/informations/{id}", 1000L)
                            .contentType(APPLICATION_JSON)
                            .header("Authorization", "bearer " + accessToken))
                    .andExpect(status().isNotFound())
                    .andDo(print());
        }
    }
}
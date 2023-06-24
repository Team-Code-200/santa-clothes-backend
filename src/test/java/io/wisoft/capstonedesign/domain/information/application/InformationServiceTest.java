package io.wisoft.capstonedesign.domain.information.application;

import io.wisoft.capstonedesign.domain.information.persistence.Information;
import io.wisoft.capstonedesign.domain.information.web.dto.CreateInformationRequest;
import io.wisoft.capstonedesign.domain.information.web.dto.UpdateInformationRequest;
import io.wisoft.capstonedesign.domain.user.application.UserService;
import io.wisoft.capstonedesign.domain.user.web.dto.CreateUserRequest;
import io.wisoft.capstonedesign.global.enumerated.Role;
import io.wisoft.capstonedesign.global.exception.service.InfoNotFoundException;
import io.wisoft.capstonedesign.global.exception.service.UserNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static io.wisoft.capstonedesign.setting.data.DefaultInfoData.createDefaultInfo;
import static io.wisoft.capstonedesign.setting.data.DefaultUserData.createDefaultUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class InformationServiceTest {

    @Autowired InformationService informationService;
    @Autowired UserService userService;

    @Nested
    @DisplayName("배송정보 생성 테스트")
    class CreateInfo {

        @Test
        @DisplayName("배송정보 입력시 정상적으로 저장되어야 한다.")
        void create_info() {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateInformationRequest infoRequest = createDefaultInfo(userId);

            // when
            Long savedId = informationService.save(infoRequest);

            // then
            Information information = informationService.findById(savedId);
            assertEquals("대전광역시 유성구", information.getAddress());
        }

        @Test
        @DisplayName("회원이 아닌 사용자가 배송정보 입력시 예외가 발생해야 한다.")
        void create_info_fail() {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            userService.join(userRequest);

            CreateInformationRequest infoRequest = createDefaultInfo(100L);

            // when

            // then
            assertThrows(UserNotFoundException.class, () -> informationService.save(infoRequest));
        }
    }

    @Nested
    @DisplayName("배송정보 조회 테스트")
    class FindInfo {

        @Test
        @DisplayName("배송정보 단건 조회 요청시 한 게시글이 조회되어야 한다.")
        void find_single_info() {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateInformationRequest infoRequest = createDefaultInfo(userId);
            Long savedId = informationService.save(infoRequest);

            // when
            Information savedInfo = informationService.findById(savedId);

            // then
            assertEquals("윤진원", savedInfo.getUsername());
            assertEquals("대전광역시 유성구", savedInfo.getAddress());
        }

        @Test
        @DisplayName("존재하지 않는 배송정보를 조회시 예외가 발생해야 한다.")
        void find_single_info_fail() {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateInformationRequest infoRequest = createDefaultInfo(userId);
            informationService.save(infoRequest);

            // when

            // then
            assertThrows(InfoNotFoundException.class, () -> informationService.findById(100L));
        }

        @Test
        @DisplayName("회원별 배송정보 조회시 회원에 맞는 배송정보가 반환되어야 한다.")
        void find_info_by_user() {

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

            CreateInformationRequest infoRequest1 = createDefaultInfo(userId);
            informationService.save(infoRequest1);

            CreateInformationRequest infoRequest2 = CreateInformationRequest.builder()
                    .username("윤진원")
                    .address("대전광역시 서구")
                    .phoneNumber("010-0000-0000")
                    .userId(userId)
                    .build();
            informationService.save(infoRequest2);

            CreateInformationRequest infoRequest3 = CreateInformationRequest.builder()
                    .username("서동권")
                    .address("대전광역시 서구")
                    .phoneNumber("010-0000-0000")
                    .userId(userId2)
                    .build();
            informationService.save(infoRequest3);

            // when
            List<Information> byUser = informationService.findByUser(userId);

            // then
            assertEquals(2L, byUser.size());
        }

        @Test
        @DisplayName("전체 배송정보 조회시 전체 게시글 목록을 반환해야 한다.")
        void find_infos() {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateInformationRequest infoRequest1 = createDefaultInfo(userId);
            informationService.save(infoRequest1);

            CreateInformationRequest infoRequest2 = CreateInformationRequest.builder()
                    .username("윤진원")
                    .address("대전광역시 서구")
                    .phoneNumber("010-0000-0000")
                    .userId(userId)
                    .build();
            informationService.save(infoRequest2);

            // when
            List<Information> informations = informationService.findInformations();

            // then
            assertEquals(2, informations.size());
            assertEquals("대전광역시 유성구", informations.get(0).getAddress());
        }

        @Test
        @DisplayName("전체 게시글을 페이징을 사용하여 조회시 페이지 번호와 내림차순으로 정렬된 페이지가 반환되어야 한다.")
        void find_infos_using_paging() {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateInformationRequest infoRequest1 = createDefaultInfo(userId);
            informationService.save(infoRequest1);

            CreateInformationRequest infoRequest2 = CreateInformationRequest.builder()
                    .username("윤진원")
                    .address("대전광역시 서구")
                    .phoneNumber("010-0000-0000")
                    .userId(userId)
                    .build();
            informationService.save(infoRequest2);
            PageRequest request = PageRequest.of(0, 5, Sort.by("createdDate").descending());

            // when
            List<Information> informationList = informationService.findByCreatedDateDescUsingPaging(request).getContent();

            // then
            assertEquals("대전광역시 서구", informationList.get(0).getAddress());
        }
    }

    @Nested
    @DisplayName("배송정보 수정 테스트")
    class UpdateInfo {

        @Test
        @DisplayName("배송정보 수정시 배송정보가 수정되어야 한다.")
        void update_info() {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateInformationRequest infoRequest = createDefaultInfo(userId);
            Long savedId = informationService.save(infoRequest);

            UpdateInformationRequest updateRequest = UpdateInformationRequest.builder()
                    .username("윤진원")
                    .address("대전광역시 서구")
                    .phoneNumber("010-0000-0000")
                    .infoId(savedId)
                    .build();

            // when
            informationService.updateAll(savedId, updateRequest);
            Information updateInfo = informationService.findById(savedId);

            // then
            assertEquals("대전광역시 서구", updateInfo.getAddress());
        }

        @Test
        @DisplayName("존재하지 않는 배송정보를 수정시 예외가 발생해야 한다.")
        void update_info_fail() {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateInformationRequest infoRequest = createDefaultInfo(userId);
            Long savedId = informationService.save(infoRequest);

            UpdateInformationRequest updateRequest = UpdateInformationRequest.builder()
                    .username("윤진원")
                    .address("대전광역시 서구")
                    .phoneNumber("010-0000-0000")
                    .infoId(savedId)
                    .build();

            // when

            // then
            assertThrows(InfoNotFoundException.class, () -> informationService.updateAll(100L, updateRequest));
        }
    }

    @Nested
    @DisplayName("배송정보 삭제 테스트")
    class DeleteInfo {

        @Test
        @DisplayName("배송정보 삭제시 배송정보가 삭제되어야 한다.")
        void delete_info() {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateInformationRequest infoRequest = createDefaultInfo(userId);
            Long savedId = informationService.save(infoRequest);

            // when
            informationService.deleteInformation(savedId);

            // then
            assertThrows(InfoNotFoundException.class, () -> informationService.findById(savedId));
        }

        @Test
        @DisplayName("존재하지 않는 배송정보를 삭제시 예외가 발생해야 한다.")
        void delete_info_fail() {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateInformationRequest infoRequest = createDefaultInfo(userId);
            informationService.save(infoRequest);

            // when

            // then
            assertThrows(InfoNotFoundException.class, () -> informationService.deleteInformation(100L));
        }
    }
}

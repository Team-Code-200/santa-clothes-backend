package io.wisoft.capstonedesign.domain.donateorder.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.wisoft.capstonedesign.domain.address.persistence.Address;
import io.wisoft.capstonedesign.domain.donate.application.DonateService;
import io.wisoft.capstonedesign.domain.donate.web.dto.CreateDonateRequest;
import io.wisoft.capstonedesign.domain.donateorder.application.DonateOrderService;
import io.wisoft.capstonedesign.domain.donateorder.web.dto.CreateOrderRequest;
import io.wisoft.capstonedesign.domain.donateorder.web.dto.UpdateOrderRequest;
import io.wisoft.capstonedesign.domain.information.application.InformationService;
import io.wisoft.capstonedesign.domain.information.web.dto.CreateInformationRequest;
import io.wisoft.capstonedesign.domain.user.application.UserService;
import io.wisoft.capstonedesign.domain.user.web.dto.CreateUserRequest;
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
import static io.wisoft.capstonedesign.setting.data.DefaultDonateOrderData.createDefaultOrder;
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
class DonateOrderControllerTest {

    @Autowired DonateOrderService donateOrderService;
    @Autowired UserService userService;
    @Autowired DonateService donateService;
    @Autowired InformationService informationService;
    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;
    @Autowired JwtTokenProvider jwtTokenProvider;

    @Nested
    @DisplayName("주문내역 생성 API 테스트")
    class CreateOrder {

        @Test
        @DisplayName("주문내역 생성시 정상적으로 주문이 되어야 한다.")
        void create_order() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(userId));

            CreateDonateRequest donateRequest = createDefaultDonate(userId);
            Long donateId = donateService.join(donateRequest);

            CreateInformationRequest infoRequest = createDefaultInfo(userId);
            Long infoId = informationService.save(infoRequest);

            CreateOrderRequest orderRequest = createDefaultOrder(infoId, donateId, userId);
            String json = objectMapper.writeValueAsString(orderRequest);

            // expected
            mockMvc.perform(post("/api/donate-orders/new")
                            .contentType(APPLICATION_JSON)
                            .header("Authorization", "bearer " + accessToken)
                            .content(json))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").exists())
                    .andDo(print());
        }

        @Test
        @DisplayName("회원이 아닌 사용자가 거래시 예외가 발생해야 한다.")
        void create_order_fail1() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateDonateRequest donateRequest = createDefaultDonate(userId);
            Long donateId = donateService.join(donateRequest);

            CreateInformationRequest infoRequest = createDefaultInfo(userId);
            Long infoId = informationService.save(infoRequest);

            CreateOrderRequest orderRequest = createDefaultOrder(infoId, donateId, userId);
            String json = objectMapper.writeValueAsString(orderRequest);

            // expected
            mockMvc.perform(post("/api/donate-orders/new")
                            .contentType(APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isUnauthorized())
                    .andDo(print());
        }

        @Test
        @DisplayName("주문시 요청사항은 필수다.")
        void create_order_fail2() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(userId));

            CreateDonateRequest donateRequest = createDefaultDonate(userId);
            Long donateId = donateService.join(donateRequest);

            CreateInformationRequest infoRequest = createDefaultInfo(userId);
            Long infoId = informationService.save(infoRequest);

            CreateOrderRequest orderRequest = CreateOrderRequest.builder()
                    .text(null)
                    .infoId(infoId)
                    .donateId(donateId)
                    .userId(userId)
                    .build();

            String json = objectMapper.writeValueAsString(orderRequest);

            // expected
            mockMvc.perform(post("/api/donate-orders/new")
                            .contentType(APPLICATION_JSON)
                            .header("Authorization", "bearer " + accessToken)
                            .content(json))
                    .andExpect(status().isBadRequest())
                    .andDo(print());
        }
    }

    @Nested
    @DisplayName("주문내역 조회 API 테스트")
    class FindOrder {

        @Test
        @DisplayName("주문내역 단건 조회 요청시 요청한 주문내역이 조회되어야 한다.")
        void find_single_order() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(userId));

            CreateDonateRequest donateRequest = createDefaultDonate(userId);
            Long donateId = donateService.join(donateRequest);

            CreateInformationRequest infoRequest = createDefaultInfo(userId);
            Long infoId = informationService.save(infoRequest);

            CreateOrderRequest orderRequest = createDefaultOrder(infoId, donateId, userId);
            Long savedId = donateOrderService.save(orderRequest);

            System.out.println("dddd"+jsonPath("$.address"));
            // expected
            mockMvc.perform(get("/api/donate-orders/{id}", savedId)
                            .contentType(APPLICATION_JSON)
                            .header("Authorization", "bearer " + accessToken))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.text").value("배송전 문자주세요"))
                    .andExpect(jsonPath("$.address").value("한밭대학교 wisoftN5-503대전광역시 유성구 동서대로 12534159"))
                    .andDo(print());
        }

        @Test
        @DisplayName("존재하지 않는 주문내역을 조회시 예외가 발생해야 한다.")
        void find_single_order_fail1() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(userId));

            CreateDonateRequest donateRequest = createDefaultDonate(userId);
            Long donateId = donateService.join(donateRequest);

            CreateInformationRequest infoRequest = createDefaultInfo(userId);
            Long infoId = informationService.save(infoRequest);

            CreateOrderRequest orderRequest = createDefaultOrder(infoId, donateId, userId);
            donateOrderService.save(orderRequest);

            // expected
            mockMvc.perform(get("/api/donate-orders/{id}", 1000L)
                            .contentType(APPLICATION_JSON)
                            .header("Authorization", "bearer " + accessToken))
                    .andExpect(status().isNotFound())
                    .andDo(print());
        }

        @Test
        @DisplayName("회원이 아닌 사용자가 주문내역 조회시 예외가 발생해야 한다.")
        void find_single_order_fail2() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateDonateRequest donateRequest = createDefaultDonate(userId);
            Long donateId = donateService.join(donateRequest);

            CreateInformationRequest infoRequest = createDefaultInfo(userId);
            Long infoId = informationService.save(infoRequest);

            CreateOrderRequest orderRequest = createDefaultOrder(infoId, donateId, userId);
            Long orderId = donateOrderService.save(orderRequest);

            // expected
            mockMvc.perform(get("/api/donate-orders/{id}", orderId)
                            .contentType(APPLICATION_JSON))
                    .andExpect(status().isUnauthorized())
                    .andDo(print());
        }

        @Test
        @DisplayName("주문내역 전체 조회시 전체 주문내역을 페이징하여 반환해야 한다.")
        void find_orders() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(userId));

            CreateDonateRequest donateRequest = createDefaultDonate(userId);
            Long donateId = donateService.join(donateRequest);

            CreateInformationRequest infoRequest = createDefaultInfo(userId);
            Long infoId = informationService.save(infoRequest);

            CreateOrderRequest orderRequest1 = createDefaultOrder(infoId, donateId, userId);
            donateOrderService.save(orderRequest1);

            CreateOrderRequest orderRequest2 = createDefaultOrder(infoId, donateId, userId);
            donateOrderService.save(orderRequest2);

            // expected
            mockMvc.perform(get("/api/donate-orders?page=0&size=5")
                            .contentType(APPLICATION_JSON)
                            .header("Authorization", "bearer " + accessToken))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content.length()", is(2)))
                    .andDo(print());
        }
    }

    @Nested
    @DisplayName("주문내역 수정 API 테스트")
    class UpdateOrder {

        @Test
        @DisplayName("주문 요청사항 수정시 요청사항이 수정되어 DB에 반영되어야 한다.")
        void update_order() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(userId));

            CreateDonateRequest donateRequest = createDefaultDonate(userId);
            Long donateId = donateService.join(donateRequest);

            CreateInformationRequest infoRequest = createDefaultInfo(userId);
            Long infoId = informationService.save(infoRequest);

            CreateOrderRequest orderRequest = createDefaultOrder(infoId, donateId, userId);
            Long orderId = donateOrderService.save(orderRequest);

            UpdateOrderRequest updateRequest = UpdateOrderRequest.builder()
                    .text("경비실에 맡겨주세요")
                    .orderId(orderId)
                    .build();

            String json = objectMapper.writeValueAsString(updateRequest);

            // expected
            mockMvc.perform(patch("/api/donate-orders/{id}", orderId)
                            .contentType(APPLICATION_JSON)
                            .header("Authorization", "bearer " + accessToken)
                            .content(json))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.text").value("경비실에 맡겨주세요"))
                    .andDo(print());
        }

        @Test
        @DisplayName("존재하지 않는 주문내역을 수정시 예외가 발생해야 한다.")
        void update_order_fail1() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(userId));

            CreateDonateRequest donateRequest = createDefaultDonate(userId);
            Long donateId = donateService.join(donateRequest);

            CreateInformationRequest infoRequest = createDefaultInfo(userId);
            Long infoId = informationService.save(infoRequest);

            CreateOrderRequest orderRequest = createDefaultOrder(infoId, donateId, userId);
            Long orderId = donateOrderService.save(orderRequest);

            UpdateOrderRequest updateRequest = UpdateOrderRequest.builder()
                    .text("경비실에 맡겨주세요")
                    .orderId(orderId)
                    .build();

            String json = objectMapper.writeValueAsString(updateRequest);

            // expected
            mockMvc.perform(patch("/api/donate-orders/{id}", 1000L)
                            .contentType(APPLICATION_JSON)
                            .header("Authorization", "bearer " + accessToken)
                            .content(json))
                    .andExpect(status().isNotFound())
                    .andDo(print());
        }

        @Test
        @DisplayName("주문내역 수정 요청시 요청사항은 필수다.")
        void update_order_fail2() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(userId));

            CreateDonateRequest donateRequest = createDefaultDonate(userId);
            Long donateId = donateService.join(donateRequest);

            CreateInformationRequest infoRequest = createDefaultInfo(userId);
            Long infoId = informationService.save(infoRequest);

            CreateOrderRequest orderRequest = createDefaultOrder(infoId, donateId, userId);
            Long orderId = donateOrderService.save(orderRequest);

            UpdateOrderRequest updateRequest = UpdateOrderRequest.builder()
                    .text(null)
                    .orderId(orderId)
                    .build();

            String json = objectMapper.writeValueAsString(updateRequest);

            // expected
            mockMvc.perform(patch("/api/donate-orders/{id}", orderId)
                            .contentType(APPLICATION_JSON)
                            .header("Authorization", "bearer " + accessToken)
                            .content(json))
                    .andExpect(status().isBadRequest())
                    .andDo(print());
        }

        @Test
        @DisplayName("로그인하지 않은 사용자가 수정 요청시 예외가 발생해야 한다.")
        void update_order_fail3() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateDonateRequest donateRequest = createDefaultDonate(userId);
            Long donateId = donateService.join(donateRequest);

            CreateInformationRequest infoRequest = createDefaultInfo(userId);
            Long infoId = informationService.save(infoRequest);

            CreateOrderRequest orderRequest = createDefaultOrder(infoId, donateId, userId);
            Long orderId = donateOrderService.save(orderRequest);

            UpdateOrderRequest updateRequest = UpdateOrderRequest.builder()
                    .text("경비실에 맡겨주세요")
                    .orderId(orderId)
                    .build();

            String json = objectMapper.writeValueAsString(updateRequest);

            // expected
            mockMvc.perform(patch("/api/donate-orders/{id}", orderId)
                            .contentType(APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isUnauthorized())
                    .andDo(print());
        }
    }

    @Nested
    @DisplayName("주문내역 삭제 API 테스트")
    class DeleteOrder {

        @Test
        @DisplayName("주문내역 삭제 요청시 정상적으로 삭제되어야 한다.")
        void delete_order() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(userId));

            CreateDonateRequest donateRequest = createDefaultDonate(userId);
            Long donateId = donateService.join(donateRequest);

            CreateInformationRequest infoRequest = createDefaultInfo(userId);
            Long infoId = informationService.save(infoRequest);

            CreateOrderRequest orderRequest = createDefaultOrder(infoId, donateId, userId);
            Long savedId = donateOrderService.save(orderRequest);

            // expected
            mockMvc.perform(delete("/api/donate-orders/{id}", savedId)
                            .contentType(APPLICATION_JSON)
                            .header("Authorization", "bearer " + accessToken))
                    .andExpect(status().isOk())
                    .andDo(print());
        }

        @Test
        @DisplayName("회원이 아닌 사용자가 주문내역 삭제 요청시 예외가 발생해야 한다.")
        void delete_order_fail1() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateDonateRequest donateRequest = createDefaultDonate(userId);
            Long donateId = donateService.join(donateRequest);

            CreateInformationRequest infoRequest = createDefaultInfo(userId);
            Long infoId = informationService.save(infoRequest);

            CreateOrderRequest orderRequest = createDefaultOrder(infoId, donateId, userId);
            Long savedId = donateOrderService.save(orderRequest);

            // expected
            mockMvc.perform(delete("/api/donate-orders/{id}", savedId)
                            .contentType(APPLICATION_JSON))
                    .andExpect(status().isUnauthorized())
                    .andDo(print());
        }

        @Test
        @DisplayName("존재하지 않은 주문내역을 삭제 요청시 예외가 발생해야 한다.")
        void delete_order_fail2() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(userId));

            CreateDonateRequest donateRequest = createDefaultDonate(userId);
            Long donateId = donateService.join(donateRequest);

            CreateInformationRequest infoRequest = createDefaultInfo(userId);
            Long infoId = informationService.save(infoRequest);

            CreateOrderRequest orderRequest = createDefaultOrder(infoId, donateId, userId);
            donateOrderService.save(orderRequest);

            // expected
            mockMvc.perform(delete("/api/donate-orders/{id}", 1000L)
                            .contentType(APPLICATION_JSON)
                            .header("Authorization", "bearer " + accessToken))
                    .andExpect(status().isNotFound())
                    .andDo(print());
        }
    }
}

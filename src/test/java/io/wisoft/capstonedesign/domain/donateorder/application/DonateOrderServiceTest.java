package io.wisoft.capstonedesign.domain.donateorder.application;

import io.wisoft.capstonedesign.domain.donate.application.DonateService;
import io.wisoft.capstonedesign.domain.donate.web.dto.CreateDonateRequest;
import io.wisoft.capstonedesign.domain.donateorder.persistence.DonateOrder;
import io.wisoft.capstonedesign.domain.donateorder.web.dto.CreateOrderRequest;
import io.wisoft.capstonedesign.domain.donateorder.web.dto.UpdateOrderRequest;
import io.wisoft.capstonedesign.domain.information.application.InformationService;
import io.wisoft.capstonedesign.domain.information.web.dto.CreateInformationRequest;
import io.wisoft.capstonedesign.domain.user.application.UserService;
import io.wisoft.capstonedesign.domain.user.web.dto.CreateUserRequest;
import io.wisoft.capstonedesign.global.exception.service.OrderNotFoundException;
import io.wisoft.capstonedesign.global.exception.service.UserNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static io.wisoft.capstonedesign.setting.data.DefaultDonateData.createDefaultDonate;
import static io.wisoft.capstonedesign.setting.data.DefaultDonateOrderData.createDefaultOrder;
import static io.wisoft.capstonedesign.setting.data.DefaultInfoData.createDefaultInfo;
import static io.wisoft.capstonedesign.setting.data.DefaultUserData.createDefaultUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class DonateOrderServiceTest {

    @Autowired DonateOrderService donateOrderService;
    @Autowired UserService userService;
    @Autowired DonateService donateService;
    @Autowired InformationService informationService;

    @Nested
    @DisplayName("주문내역 생성 테스트")
    class CreateOrder {

        @Test
        @DisplayName("주문내역 생성시 정상적으로 주문이 되어야 한다.")
        void create_order() {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateDonateRequest donateRequest = createDefaultDonate(userId);
            Long donateId = donateService.join(donateRequest);

            CreateInformationRequest infoRequest = createDefaultInfo(userId);
            Long infoId = informationService.save(infoRequest);

            CreateOrderRequest orderRequest = createDefaultOrder(infoId, donateId, userId);

            // when
            Long savedId = donateOrderService.save(orderRequest);

            // then
            DonateOrder order = donateOrderService.findById(savedId);
            assertEquals("배송전 문자주세요", order.getText());
        }

        @Test
        @DisplayName("회원이 아닌 사용자가 주문할시 예외가 발생해야 한다.")
        void create_order_fail() {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateDonateRequest donateRequest = createDefaultDonate(userId);
            Long donateId = donateService.join(donateRequest);

            CreateInformationRequest infoRequest = createDefaultInfo(userId);
            Long infoId = informationService.save(infoRequest);

            CreateOrderRequest orderRequest = createDefaultOrder(infoId, donateId, 100L);

            // when

            // then
            assertThrows(UserNotFoundException.class, () -> donateOrderService.save(orderRequest));
        }

        @Test
        @DisplayName("배송정보 없이 주문할시 예외가 발생해야 한다.")
        void create_order_fail2() {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateDonateRequest donateRequest = createDefaultDonate(userId);
            Long donateId = donateService.join(donateRequest);

            CreateInformationRequest infoRequest = createDefaultInfo(userId);
            informationService.save(infoRequest);

            CreateOrderRequest orderRequest = createDefaultOrder(null, donateId, userId);

            // when

            // then
            assertThrows(InvalidDataAccessApiUsageException.class, () -> donateOrderService.save(orderRequest));
        }
    }

    @Nested
    @DisplayName("주문내역 조회 테스트")
    class FindOrder {

        @Test
        @DisplayName("주문내역 단건 조회 요청시 한 주문내역이 조회되어야 한다.")
        void find_single_order() {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateDonateRequest donateRequest = createDefaultDonate(userId);
            Long donateId = donateService.join(donateRequest);

            CreateInformationRequest infoRequest = createDefaultInfo(userId);
            Long infoId = informationService.save(infoRequest);

            CreateOrderRequest orderRequest = createDefaultOrder(infoId, donateId, userId);
            Long savedId = donateOrderService.save(orderRequest);

            // when
            DonateOrder savedOrder = donateOrderService.findById(savedId);

            // then
            assertEquals("배송전 문자주세요", savedOrder.getText());
            assertEquals(infoId, savedOrder.getInformation().getId());
        }

        @Test
        @DisplayName("존재하지 않는 주문내역을 조회시 예외가 발생해야 한다.")
        void find_single_order_fail() {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateDonateRequest donateRequest = createDefaultDonate(userId);
            Long donateId = donateService.join(donateRequest);

            CreateInformationRequest infoRequest = createDefaultInfo(userId);
            Long infoId = informationService.save(infoRequest);

            CreateOrderRequest orderRequest = createDefaultOrder(infoId, donateId, userId);
            donateOrderService.save(orderRequest);

            // when

            // then
            assertThrows(OrderNotFoundException.class, () -> donateOrderService.findById(100L));
        }

        @Test
        @DisplayName("전체 주문내역 조회시 전체 주문내역 목록을 반환해야 한다.")
        void find_orders() {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateDonateRequest donateRequest = createDefaultDonate(userId);
            Long donateId = donateService.join(donateRequest);

            CreateInformationRequest infoRequest = createDefaultInfo(userId);
            Long infoId = informationService.save(infoRequest);

            CreateOrderRequest orderRequest1 = createDefaultOrder(infoId, donateId, userId);
            donateOrderService.save(orderRequest1);

            CreateOrderRequest orderRequest2 = CreateOrderRequest.builder()
                    .text("경비실에 맡겨주세요")
                    .infoId(infoId)
                    .donateId(donateId)
                    .userId(userId)
                    .build();
            donateOrderService.save(orderRequest2);

            // when
            List<DonateOrder> orders = donateOrderService.findDonateOrders();

            // then
            assertEquals(2, orders.size());
            assertEquals("경비실에 맡겨주세요", orders.get(1).getText());
        }

        @Test
        @DisplayName("전체 주문내역을 페이징을 사용하여 조회시 페이지 번호와 내림차순으로 정렬된 페이지가 반환되어야 한다.")
        void find_orders_using_paging() {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateDonateRequest donateRequest = createDefaultDonate(userId);
            Long donateId = donateService.join(donateRequest);

            CreateInformationRequest infoRequest = createDefaultInfo(userId);
            Long infoId = informationService.save(infoRequest);

            CreateOrderRequest orderRequest1 = createDefaultOrder(infoId, donateId, userId);
            donateOrderService.save(orderRequest1);

            CreateOrderRequest orderRequest2 = CreateOrderRequest.builder()
                    .text("경비실에 맡겨주세요")
                    .infoId(infoId)
                    .donateId(donateId)
                    .userId(userId)
                    .build();
            donateOrderService.save(orderRequest2);
            PageRequest request = PageRequest.of(0, 5, Sort.by("sendDate").descending());

            // when
            List<DonateOrder> donateOrders = donateOrderService.findByCreatedDateDescUsingPaging(request).getContent();

            // then
            assertEquals("경비실에 맡겨주세요", donateOrders.get(0).getText());
        }
    }

    @Nested
    @DisplayName("주문내역 수정 테스트")
    class UpdateOrder {

        @Test
        @DisplayName("주문내역 수정시 주문내역 기타사항이 수정되어야 한다.")
        void update_order() {

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

            // when
            donateOrderService.updateBody(orderId, updateRequest);
            DonateOrder updateOrder = donateOrderService.findById(orderId);

            // then
            assertEquals("경비실에 맡겨주세요", updateOrder.getText());
        }

        @Test
        @DisplayName("존재하지 않는 주문내역을 수정시 예외가 발생해야 한다.")
        void update_order_fail() {

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

            // when

            // then
            assertThrows(OrderNotFoundException.class, () -> donateOrderService.updateBody(100L, updateRequest));
        }
    }

    @Nested
    @DisplayName("주문내역 삭제 테스트")
    class DeleteOrder {

        @Test
        @DisplayName("주문내역 삭제시 주문내역이 삭제되어야 한다.")
        void delete_order() {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateDonateRequest donateRequest = createDefaultDonate(userId);
            Long donateId = donateService.join(donateRequest);

            CreateInformationRequest infoRequest = createDefaultInfo(userId);
            Long infoId = informationService.save(infoRequest);

            CreateOrderRequest orderRequest = createDefaultOrder(infoId, donateId, userId);
            Long orderId = donateOrderService.save(orderRequest);

            // when
            donateOrderService.deleteOrder(orderId);

            // then
            assertThrows(OrderNotFoundException.class, () -> donateOrderService.findById(orderId));
        }

        @Test
        @DisplayName("존재하지 않는 주문내역을 삭제시 예외가 발생해야 한다.")
        void delete_order_fail() {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateDonateRequest donateRequest = createDefaultDonate(userId);
            Long donateId = donateService.join(donateRequest);

            CreateInformationRequest infoRequest = createDefaultInfo(userId);
            Long infoId = informationService.save(infoRequest);

            CreateOrderRequest orderRequest = createDefaultOrder(infoId, donateId, userId);
            donateOrderService.save(orderRequest);

            // when

            // then
            assertThrows(OrderNotFoundException.class, () -> donateOrderService.deleteOrder(100L));
        }
    }
}

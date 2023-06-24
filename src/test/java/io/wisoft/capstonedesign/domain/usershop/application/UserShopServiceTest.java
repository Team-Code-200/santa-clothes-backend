package io.wisoft.capstonedesign.service;

import io.wisoft.capstonedesign.domain.information.application.InformationService;
import io.wisoft.capstonedesign.domain.information.web.dto.CreateInformationRequest;
import io.wisoft.capstonedesign.domain.shop.application.ShopService;
import io.wisoft.capstonedesign.domain.shop.web.dto.CreateShopRequest;
import io.wisoft.capstonedesign.domain.user.application.UserService;
import io.wisoft.capstonedesign.domain.user.web.dto.CreateUserRequest;
import io.wisoft.capstonedesign.domain.usershop.application.UserShopService;
import io.wisoft.capstonedesign.domain.usershop.persistence.UserShop;
import io.wisoft.capstonedesign.domain.usershop.web.dto.CreateOrderRequest;
import io.wisoft.capstonedesign.domain.usershop.web.dto.UpdateOrderRequest;
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

import static io.wisoft.capstonedesign.setting.data.DefaultInfoData.createDefaultInfo;
import static io.wisoft.capstonedesign.setting.data.DefaultShopData.createDefaultShop;
import static io.wisoft.capstonedesign.setting.data.DefaultShopOrderData.createDefaultOrder;
import static io.wisoft.capstonedesign.setting.data.DefaultUserData.createDefaultUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class UserShopServiceTest {

    @Autowired UserShopService userShopService;
    @Autowired UserService userService;
    @Autowired ShopService shopService;
    @Autowired InformationService informationService;

    @Nested
    @DisplayName("산타샵 주문내역 생성 테스트")
    class CreateOrder {

        @Test
        @DisplayName("산타샵 주문내역 생성시 정상적으로 주문이 되어야 한다.")
        void create_order() {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateShopRequest shopRequest = createDefaultShop(userId);
            Long shopId = shopService.save(shopRequest);

            CreateInformationRequest infoRequest = createDefaultInfo(userId);
            Long infoId = informationService.save(infoRequest);

            CreateOrderRequest orderRequest = createDefaultOrder(infoId, shopId, userId);

            // when
            Long savedId = userShopService.save(orderRequest);

            // then
            UserShop order = userShopService.findById(savedId);
            assertEquals("배송 전 문자 부탁드립니다", order.getText());
        }

        @Test
        @DisplayName("회원이 아닌 사용자가 주문할시 예외가 발생해야 한다.")
        void create_order_fail() {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateShopRequest shopRequest = createDefaultShop(userId);
            Long shopId = shopService.save(shopRequest);

            CreateInformationRequest infoRequest = createDefaultInfo(userId);
            Long infoId = informationService.save(infoRequest);

            CreateOrderRequest orderRequest = createDefaultOrder(infoId, shopId, 100L);

            // when

            // then
            assertThrows(UserNotFoundException.class, () -> userShopService.save(orderRequest));
        }

        @Test
        @DisplayName("배송정보 없이 주문할시 예외가 발생해야 한다.")
        void create_order_fail2() {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateShopRequest shopRequest = createDefaultShop(userId);
            Long shopId = shopService.save(shopRequest);

            CreateInformationRequest infoRequest = createDefaultInfo(userId);
            informationService.save(infoRequest);

            CreateOrderRequest orderRequest = createDefaultOrder(null, shopId, userId);

            // when

            // then
            assertThrows(InvalidDataAccessApiUsageException.class, () -> userShopService.save(orderRequest));
        }
    }

    @Nested
    @DisplayName("산타샵 주문내역 조회 테스트")
    class FindOrder {

        @Test
        @DisplayName("주문내역 단건 조회 요청시 한 주문내역이 조회되어야 한다.")
        void find_single_order() {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateShopRequest shopRequest = createDefaultShop(userId);
            Long shopId = shopService.save(shopRequest);

            CreateInformationRequest infoRequest = createDefaultInfo(userId);
            Long infoId = informationService.save(infoRequest);

            CreateOrderRequest orderRequest = createDefaultOrder(infoId, shopId, userId);
            Long savedId = userShopService.save(orderRequest);

            // when
            UserShop savedOrder = userShopService.findById(savedId);

            // then
            assertEquals("배송 전 문자 부탁드립니다", savedOrder.getText());
            assertEquals(infoId, savedOrder.getInformation().getId());
        }

        @Test
        @DisplayName("존재하지 않는 주문내역을 조회시 예외가 발생해야 한다.")
        void find_single_order_fail() {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateShopRequest shopRequest = createDefaultShop(userId);
            Long shopId = shopService.save(shopRequest);

            CreateInformationRequest infoRequest = createDefaultInfo(userId);
            Long infoId = informationService.save(infoRequest);

            CreateOrderRequest orderRequest = createDefaultOrder(infoId, shopId, userId);
            userShopService.save(orderRequest);

            // when

            // then
            assertThrows(OrderNotFoundException.class, () -> userShopService.findById(100L));
        }

        @Test
        @DisplayName("전체 주문내역 조회시 전체 주문내역 목록을 반환해야 한다.")
        void find_orders() {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateShopRequest shopRequest = createDefaultShop(userId);
            Long shopId = shopService.save(shopRequest);

            CreateInformationRequest infoRequest = createDefaultInfo(userId);
            Long infoId = informationService.save(infoRequest);

            CreateOrderRequest orderRequest1 = createDefaultOrder(infoId, shopId, userId);
            userShopService.save(orderRequest1);

            CreateOrderRequest orderRequest2 = CreateOrderRequest.builder()
                    .text("경비실에 맡겨주세요")
                    .infoId(infoId)
                    .shopId(shopId)
                    .userId(userId)
                    .build();
            userShopService.save(orderRequest2);

            // when
            List<UserShop> shopOrders = userShopService.findShopOrders();

            // then
            assertEquals(2, shopOrders.size());
            assertEquals("경비실에 맡겨주세요", shopOrders.get(1).getText());
        }

        @Test
        @DisplayName("전체 주문내역을 페이징을 사용하여 조회시 페이지 번호와 내림차순으로 정렬된 페이지가 반환되어야 한다.")
        void find_orders_using_paging() {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateShopRequest shopRequest = createDefaultShop(userId);
            Long shopId = shopService.save(shopRequest);

            CreateInformationRequest infoRequest = createDefaultInfo(userId);
            Long infoId = informationService.save(infoRequest);

            CreateOrderRequest orderRequest1 = createDefaultOrder(infoId, shopId, userId);
            userShopService.save(orderRequest1);

            CreateOrderRequest orderRequest2 = CreateOrderRequest.builder()
                    .text("경비실에 맡겨주세요")
                    .infoId(infoId)
                    .shopId(shopId)
                    .userId(userId)
                    .build();
            userShopService.save(orderRequest2);
            PageRequest request = PageRequest.of(0, 5, Sort.by("createdDate").descending());

            // when
            List<UserShop> shops = userShopService.findByCreatedDateDescUsingPaging(request).getContent();

            // then
            assertEquals("경비실에 맡겨주세요", shops.get(0).getText());
        }
    }

    @Nested
    @DisplayName("산타샵 주문내역 수정 테스트")
    class UpdateOrder {

        @Test
        @DisplayName("주문내역 수정시 주문내역 기타사항이 수정되어야 한다.")
        void update_order() {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateShopRequest shopRequest = createDefaultShop(userId);
            Long shopId = shopService.save(shopRequest);

            CreateInformationRequest infoRequest = createDefaultInfo(userId);
            Long infoId = informationService.save(infoRequest);

            CreateOrderRequest orderRequest = createDefaultOrder(infoId, shopId, userId);
            Long savedId = userShopService.save(orderRequest);

            UpdateOrderRequest updateRequest = UpdateOrderRequest.builder()
                    .text("경비실에 맡겨주세요")
                    .orderId(savedId)
                    .build();

            // when
            userShopService.updateBody(savedId, updateRequest);
            UserShop updateOrder = userShopService.findById(savedId);

            // then
            assertEquals("경비실에 맡겨주세요", updateOrder.getText());
        }

        @Test
        @DisplayName("존재하지 않는 주문내역을 수정시 예외가 발생해야 한다.")
        void update_order_fail() {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateShopRequest shopRequest = createDefaultShop(userId);
            Long shopId = shopService.save(shopRequest);

            CreateInformationRequest infoRequest = createDefaultInfo(userId);
            Long infoId = informationService.save(infoRequest);

            CreateOrderRequest orderRequest = createDefaultOrder(infoId, shopId, userId);
            Long savedId = userShopService.save(orderRequest);

            UpdateOrderRequest updateRequest = UpdateOrderRequest.builder()
                    .text("경비실에 맡겨주세요")
                    .orderId(savedId)
                    .build();

            // when

            // then
            assertThrows(OrderNotFoundException.class, () -> userShopService.updateBody(100L, updateRequest));
        }
    }

    @Nested
    @DisplayName("산타샵 주문내역 삭제 테스트")
    class DeleteOrder {

        @Test
        @DisplayName("주문내역 삭제시 주문내역이 삭제되어야 한다.")
        void delete_order() {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateShopRequest shopRequest = createDefaultShop(userId);
            Long shopId = shopService.save(shopRequest);

            CreateInformationRequest infoRequest = createDefaultInfo(userId);
            Long infoId = informationService.save(infoRequest);

            CreateOrderRequest orderRequest = createDefaultOrder(infoId, shopId, userId);
            Long savedId = userShopService.save(orderRequest);

            // when
            userShopService.deleteOrder(savedId);

            // then
            assertThrows(OrderNotFoundException.class, () -> userShopService.findById(savedId));
        }

        @Test
        @DisplayName("존재하지 않는 주문내역을 삭제시 예외가 발생해야 한다.")
        void delete_order_fail() {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateShopRequest shopRequest = createDefaultShop(userId);
            Long shopId = shopService.save(shopRequest);

            CreateInformationRequest infoRequest = createDefaultInfo(userId);
            Long infoId = informationService.save(infoRequest);

            CreateOrderRequest orderRequest = createDefaultOrder(infoId, shopId, userId);
            userShopService.save(orderRequest);

            // when

            // then
            assertThrows(OrderNotFoundException.class, () -> userShopService.deleteOrder(100L));
        }
    }
}

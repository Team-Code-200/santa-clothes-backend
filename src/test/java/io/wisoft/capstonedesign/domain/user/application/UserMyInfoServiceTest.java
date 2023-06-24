package io.wisoft.capstonedesign.domain.user.application;

import io.wisoft.capstonedesign.domain.donate.application.DonateService;
import io.wisoft.capstonedesign.domain.donate.persistence.Donate;
import io.wisoft.capstonedesign.domain.donate.web.dto.CreateDonateRequest;
import io.wisoft.capstonedesign.domain.donateorder.application.DonateOrderService;
import io.wisoft.capstonedesign.domain.donateorder.persistence.DonateOrder;
import io.wisoft.capstonedesign.domain.donateorder.web.dto.CreateOrderRequest;
import io.wisoft.capstonedesign.domain.find.application.FindService;
import io.wisoft.capstonedesign.domain.find.persistence.Find;
import io.wisoft.capstonedesign.domain.find.web.dto.CreateFindRequest;
import io.wisoft.capstonedesign.domain.findorder.application.FindOrderService;
import io.wisoft.capstonedesign.domain.findorder.persistence.FindOrder;
import io.wisoft.capstonedesign.domain.information.application.InformationService;
import io.wisoft.capstonedesign.domain.information.persistence.Information;
import io.wisoft.capstonedesign.domain.information.web.dto.CreateInformationRequest;
import io.wisoft.capstonedesign.domain.shop.application.ShopService;
import io.wisoft.capstonedesign.domain.shop.web.dto.CreateShopRequest;
import io.wisoft.capstonedesign.domain.user.web.dto.CreateUserRequest;
import io.wisoft.capstonedesign.domain.usershop.application.UserShopService;
import io.wisoft.capstonedesign.domain.usershop.persistence.UserShop;
import io.wisoft.capstonedesign.global.enumerated.Role;
import io.wisoft.capstonedesign.global.enumerated.Tag;
import io.wisoft.capstonedesign.setting.data.DefaultFindOrderData;
import io.wisoft.capstonedesign.setting.data.DefaultShopOrderData;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static io.wisoft.capstonedesign.setting.data.DefaultDonateData.createDefaultDonate;
import static io.wisoft.capstonedesign.setting.data.DefaultDonateOrderData.createDefaultOrder;
import static io.wisoft.capstonedesign.setting.data.DefaultFindData.createDefaultFind;
import static io.wisoft.capstonedesign.setting.data.DefaultInfoData.createDefaultInfo;
import static io.wisoft.capstonedesign.setting.data.DefaultShopData.createDefaultShop;
import static io.wisoft.capstonedesign.setting.data.DefaultUserData.createDefaultUser;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class UserMyInfoServiceTest {

    @Autowired UserMyInfoService userMyInfoService;
    @Autowired UserService userService;
    @Autowired DonateService donateService;
    @Autowired FindService findService;
    @Autowired InformationService informationService;
    @Autowired DonateOrderService donateOrderService;
    @Autowired FindOrderService findOrderService;
    @Autowired ShopService shopService;
    @Autowired UserShopService userShopService;

    @Nested
    @DisplayName("마이페이지 정보 조회 테스트")
    class FindMyPageInfo {

        @Test
        @DisplayName("자신이 작성한 나눠줄래요 개시글 조회")
        void find_my_donate_posts() {

            // given
            CreateUserRequest userRequest1 = createDefaultUser();
            Long userId = userService.join(userRequest1);

            CreateUserRequest userRequest2 = CreateUserRequest.builder()
                    .oauthId("2")
                    .email("donggwon@gmail.com")
                    .profileImage("profile.png")
                    .point(1000)
                    .nickname("donggwon")
                    .userRole(String.valueOf(Role.GENERAL))
                    .build();
            Long userId2 = userService.join(userRequest2);

            CreateDonateRequest donateRequest1 = createDefaultDonate(userId);
            donateService.join(donateRequest1);

            CreateDonateRequest donateRequest2 = CreateDonateRequest.builder()
                    .title("바지 나눔합니다")
                    .image("image.png")
                    .text("안 입는 바지 기부해요")
                    .tag(String.valueOf(Tag.PANTS))
                    .userId(userId2)
                    .build();
            donateService.join(donateRequest2);

            PageRequest request = PageRequest.of(0, 5, Sort.by("createdDate").descending());

            // when
            List<Donate> donates = userMyInfoService.findDonatesByIdUsingPaging(userId, request).getContent();

            // then
            assertEquals("패딩 나눔합니다.", donates.get(0).getTitle());
        }

        @Test
        @DisplayName("자신이 작성한 찾아볼래요 게시글 조회")
        void find_my_find_posts() {

            // given
            CreateUserRequest userRequest1 = createDefaultUser();
            Long userId = userService.join(userRequest1);

            CreateUserRequest userRequest2 = CreateUserRequest.builder()
                    .oauthId("2")
                    .email("donggwon@gmail.com")
                    .profileImage("profile.png")
                    .point(1000)
                    .nickname("donggwon")
                    .userRole(String.valueOf(Role.GENERAL))
                    .build();
            Long userId2 = userService.join(userRequest2);

            CreateFindRequest findRequest1 = createDefaultFind(userId);
            findService.join(findRequest1);

            CreateFindRequest findRequest2 = CreateFindRequest.builder()
                    .title("바지 찾아봅니다")
                    .image("image.png")
                    .text("안 입는 바지 기부받아요")
                    .tag(String.valueOf(Tag.PANTS))
                    .userId(userId2)
                    .build();
            findService.join(findRequest2);

            PageRequest request = PageRequest.of(0, 5, Sort.by("createdDate").descending());

            // when
            List<Find> finds = userMyInfoService.findFindsByIdUsingPaging(userId, request).getContent();

            // then
            assertEquals("패딩 찾아봅니다", finds.get(0).getTitle());
        }

        @Test
        @DisplayName("자신이 작성한 배송정보 조회")
        void find_my_infos() {

            // given
            CreateUserRequest userRequest1 = createDefaultUser();
            Long userId = userService.join(userRequest1);

            CreateUserRequest userRequest2 = CreateUserRequest.builder()
                    .oauthId("2")
                    .email("donggwon@gmail.com")
                    .profileImage("profile.png")
                    .point(1000)
                    .nickname("donggwon")
                    .userRole(String.valueOf(Role.GENERAL))
                    .build();
            Long userId2 = userService.join(userRequest2);

            CreateInformationRequest infoRequest1 = createDefaultInfo(userId);
            informationService.save(infoRequest1);

            CreateInformationRequest infoRequest2 = CreateInformationRequest.builder()
                    .username("서동권")
                    .address("대전광역시 서구")
                    .phoneNumber("010-0000-0000")
                    .userId(userId2)
                    .build();
            informationService.save(infoRequest2);

            PageRequest request = PageRequest.of(0, 5, Sort.by("createdDate").descending());

            // when
            List<Information> infos = userMyInfoService.findInfosByIdUsingPaging(userId, request).getContent();

            // then
            assertEquals("대전광역시 유성구", infos.get(0).getAddress());
        }

        @Test
        @DisplayName("자신이 거래한 나눠줄래요 주문내역 조회")
        void find_my_donate_orders() {

            // given
            CreateUserRequest userRequest1 = createDefaultUser();
            Long userId = userService.join(userRequest1);

            CreateUserRequest userRequest2 = CreateUserRequest.builder()
                    .oauthId("2")
                    .email("donggwon@gmail.com")
                    .profileImage("profile.png")
                    .point(1000)
                    .nickname("donggwon")
                    .userRole(String.valueOf(Role.GENERAL))
                    .build();
            Long userId2 = userService.join(userRequest2);

            CreateDonateRequest donateRequest1 = createDefaultDonate(userId);
            Long donateId = donateService.join(donateRequest1);

            CreateDonateRequest donateRequest2 = CreateDonateRequest.builder()
                    .title("바지 나눔합니다.")
                    .image("image.png")
                    .text("안 입는 바지 기부해요")
                    .tag(String.valueOf(Tag.PANTS))
                    .userId(userId2)
                    .build();
            donateService.join(donateRequest2);

            CreateInformationRequest infoRequest1 = createDefaultInfo(userId);
            Long infoId = informationService.save(infoRequest1);

            CreateInformationRequest infoRequest2 = CreateInformationRequest.builder()
                    .username("서동권")
                    .address("대전광역시 서구")
                    .phoneNumber("010-0000-0000")
                    .userId(userId2)
                    .build();
            informationService.save(infoRequest2);


            CreateOrderRequest orderRequest1 = createDefaultOrder(infoId, donateId, userId);
            donateOrderService.save(orderRequest1);

            CreateOrderRequest orderRequest2 = CreateOrderRequest.builder()
                    .text("경비실에 맡겨주세요")
                    .infoId(infoId)
                    .donateId(donateId)
                    .userId(userId2)
                    .build();
            donateOrderService.save(orderRequest2);

            PageRequest request = PageRequest.of(0, 5, Sort.by("sendDate").descending());

            // when
            List<DonateOrder> orders = userMyInfoService.findDonateOrdersByIdUsingPaging(userId, request).getContent();

            // then
            assertEquals("배송전 문자주세요", orders.get(0).getText());
        }

        @Test
        @DisplayName("자신이 거래한 찾아볼래요 주문내역 조회")
        void find_my_find_orders() {

            // given
            CreateUserRequest userRequest1 = createDefaultUser();
            Long userId = userService.join(userRequest1);

            CreateUserRequest userRequest2 = CreateUserRequest.builder()
                    .oauthId("2")
                    .email("donggwon@gmail.com")
                    .profileImage("profile.png")
                    .point(1000)
                    .nickname("donggwon")
                    .userRole(String.valueOf(Role.GENERAL))
                    .build();
            Long userId2 = userService.join(userRequest2);

            CreateFindRequest findRequest1 = createDefaultFind(userId);
            Long findId = findService.join(findRequest1);

            CreateFindRequest findRequest2 = CreateFindRequest.builder()
                    .title("바지 찾아봅니다")
                    .image("image.png")
                    .text("안 입는 바지 기부받아요")
                    .tag(String.valueOf(Tag.PANTS))
                    .userId(userId2)
                    .build();
            findService.join(findRequest2);

            CreateInformationRequest infoRequest1 = createDefaultInfo(userId);
            Long infoId = informationService.save(infoRequest1);

            CreateInformationRequest infoRequest2 = CreateInformationRequest.builder()
                    .username("서동권")
                    .address("대전광역시 서구")
                    .phoneNumber("010-0000-0000")
                    .userId(userId2)
                    .build();
            informationService.save(infoRequest2);

            io.wisoft.capstonedesign.domain.findorder.web.dto.CreateOrderRequest orderRequest1 = DefaultFindOrderData.createDefaultOrder(infoId, findId, userId);
            findOrderService.save(orderRequest1);

            io.wisoft.capstonedesign.domain.findorder.web.dto.CreateOrderRequest orderRequest2 = io.wisoft.capstonedesign.domain.findorder.web.dto.CreateOrderRequest.builder()
                    .text("경비실에 맡겨주세요")
                    .infoId(infoId)
                    .findId(findId)
                    .userId(userId2)
                    .build();
            findOrderService.save(orderRequest2);

            PageRequest request = PageRequest.of(0, 5, Sort.by("sendDate").descending());

            // when
            List<FindOrder> orders = userMyInfoService.findFindOrdersByIdUsingPaging(userId, request).getContent();

            // then
            assertEquals("배송전 문자주세요", orders.get(0).getText());
        }

        @Test
        @DisplayName("자산이 주문한 산타샵 주문내역 조회")
        void find_my_shop_order() {

            // given
            CreateUserRequest userRequest1 = createDefaultUser();
            Long userId = userService.join(userRequest1);

            CreateUserRequest userRequest2 = CreateUserRequest.builder()
                    .oauthId("2")
                    .email("donggwon@gmail.com")
                    .profileImage("profile.png")
                    .point(1000)
                    .nickname("donggwon")
                    .userRole(String.valueOf(Role.GENERAL))
                    .build();
            Long userId2 = userService.join(userRequest2);

            CreateShopRequest shopRequest1 = createDefaultShop(userId);
            Long shopId = shopService.save(shopRequest1);

            CreateShopRequest shopRequest2 = CreateShopRequest.builder()
                    .title("쌀 10kg")
                    .price(2000)
                    .image("rice.jpg")
                    .body("포인트로 든든한 쌀 밥 가져가세요!")
                    .userId(userId2)
                    .build();
            shopService.save(shopRequest2);

            CreateInformationRequest infoRequest1 = createDefaultInfo(userId);
            Long infoId = informationService.save(infoRequest1);

            CreateInformationRequest infoRequest2 = CreateInformationRequest.builder()
                    .username("서동권")
                    .address("대전광역시 서구")
                    .phoneNumber("010-0000-0000")
                    .userId(userId2)
                    .build();
            informationService.save(infoRequest2);

            io.wisoft.capstonedesign.domain.usershop.web.dto.CreateOrderRequest orderRequest1 = DefaultShopOrderData.createDefaultOrder(infoId, shopId, userId);
            userShopService.save(orderRequest1);

            io.wisoft.capstonedesign.domain.usershop.web.dto.CreateOrderRequest orderRequest2 = io.wisoft.capstonedesign.domain.usershop.web.dto.CreateOrderRequest.builder()
                    .text("경비실에 맡겨주세요")
                    .infoId(infoId)
                    .shopId(shopId)
                    .userId(userId2)
                    .build();
            userShopService.save(orderRequest2);

            PageRequest request = PageRequest.of(0, 5, Sort.by("createdDate").descending());

            // when
            List<UserShop> shops = userMyInfoService.findShopOrdersByIdUsingPaging(userId, request).getContent();

            // then
            assertEquals("배송 전 문자 부탁드립니다", shops.get(0).getText());
        }
    }
}

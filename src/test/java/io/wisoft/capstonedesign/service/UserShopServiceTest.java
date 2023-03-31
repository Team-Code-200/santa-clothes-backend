package io.wisoft.capstonedesign.service;

import io.wisoft.capstonedesign.domain.information.web.dto.CreateInformationRequest;
import io.wisoft.capstonedesign.domain.shop.web.dto.CreateShopRequest;
import io.wisoft.capstonedesign.domain.usershop.web.dto.CreateOrderRequest;
import io.wisoft.capstonedesign.domain.usershop.web.dto.UpdateOrderRequest;
import io.wisoft.capstonedesign.global.enumerated.Role;
import io.wisoft.capstonedesign.domain.information.application.InformationService;
import io.wisoft.capstonedesign.domain.shop.application.ShopService;
import io.wisoft.capstonedesign.domain.user.application.UserService;
import io.wisoft.capstonedesign.domain.usershop.application.UserShopService;
import io.wisoft.capstonedesign.domain.user.persistence.User;
import io.wisoft.capstonedesign.domain.usershop.persistence.UserShop;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opentest4j.AssertionFailedError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback(value = false)
public class UserShopServiceTest {

    @Autowired UserShopService userShopService;
    @Autowired UserService userService;
    @Autowired ShopService shopService;
    @Autowired InformationService informationService;

    @Test
    public void 주문내역_생성() throws Exception {

        // given
        User user = User.newInstance("2", "donggwon@gmail.com", "profile.png", 1000, "donggwon", Role.GENERAL);
        CreateShopRequest request1 = CreateShopRequest.newInstance("라면 한 박스", 1000, "ramen.jpg", "포인트로 뜨끈한 라면 한 박스 가져가세요!", 1L);
        CreateInformationRequest request2 = CreateInformationRequest.newInstance("윤진원", "대전광역시 유성구", "010-0000-0000", 1L);
        CreateOrderRequest request3 = CreateOrderRequest.newInstance("배송 전 문자 부탁드립니다", 1L, 1L, 1L);

        // when
        userService.join(user);
        shopService.save(request1);
        informationService.save(request2);
        Long savedId = userShopService.save(request3);

        // then
        assertEquals(request3.getText(), userShopService.findOne(savedId).getText());
    }

    @Test
    public void 주문내역_조회() throws Exception {

        // given
        User user = User.newInstance("2", "donggwon@gmail.com", "profile.png", 1000, "donggwon", Role.GENERAL);
        CreateShopRequest request1 = CreateShopRequest.newInstance("라면 한 박스", 1000, "ramen.jpg", "포인트로 뜨끈한 라면 한 박스 가져가세요!", 1L);
        CreateInformationRequest request2 = CreateInformationRequest.newInstance("윤진원", "대전광역시 유성구", "010-0000-0000", 1L);
        CreateOrderRequest request3 = CreateOrderRequest.newInstance("배송 전 문자 부탁드립니다", 1L, 1L, 1L);

        // when
        userService.join(user);
        shopService.save(request1);
        informationService.save(request2);
        Long savedId = userShopService.save(request3);
        UserShop savedOrder = userShopService.findOne(savedId);

        // then
        assertEquals(request3.getText(), savedOrder.getText());
    }

    @Test(expected = AssertionFailedError.class)
    public void 전체_주문내역_조회() throws Exception {

        // given
        User user = User.newInstance("2", "donggwon@gmail.com", "profile.png", 1000, "donggwon", Role.GENERAL);
        CreateShopRequest request1 = CreateShopRequest.newInstance("라면 한 박스", 1000, "ramen.jpg", "포인트로 뜨끈한 라면 한 박스 가져가세요!", 1L);
        CreateInformationRequest request2 = CreateInformationRequest.newInstance("윤진원", "대전광역시 유성구", "010-0000-0000", 1L);
        CreateOrderRequest request3 = CreateOrderRequest.newInstance("배송 전 문자 부탁드립니다", 1L, 1L, 1L);
        CreateOrderRequest request4 = CreateOrderRequest.newInstance("경비실에 맡겨주세요", 1L, 1L, 1L);

        // when
        userService.join(user);
        shopService.save(request1);
        informationService.save(request2);
        userShopService.save(request3);
        userShopService.save(request4);
        List<UserShop> shopOrders = userShopService.findShopOrders();

        // then
        assertEquals(3, shopOrders.size());
    }

    @Test
    public void 개별_주문내역_최근순_조회() throws Exception {

        // given
        User user = User.newInstance("2", "donggwon@gmail.com", "profile.png", 1000, "donggwon", Role.GENERAL);
        CreateShopRequest request1 = CreateShopRequest.newInstance("라면 한 박스", 1000, "ramen.jpg", "포인트로 뜨끈한 라면 한 박스 가져가세요!", 1L);
        CreateInformationRequest request2 = CreateInformationRequest.newInstance("윤진원", "대전광역시 유성구", "010-0000-0000", 1L);
        CreateOrderRequest request3 = CreateOrderRequest.newInstance("배송 전 문자 부탁드립니다", 1L, 1L, 1L);
        CreateOrderRequest request4 = CreateOrderRequest.newInstance("경비실에 맡겨주세요", 1L, 1L, 1L);

        // when
        userService.join(user);
        shopService.save(request1);
        informationService.save(request2);
        userShopService.save(request3);
        userShopService.save(request4);
        List<UserShop> orderDESC = userShopService.findByUserDESC(user);

        // then
        assertEquals(request4.getText(), orderDESC.get(0).getText());
    }

    @Test
    public void 주문내역_기타사항_수정() throws Exception {

        // given
        User user = User.newInstance("2", "donggwon@gmail.com", "profile.png", 1000, "donggwon", Role.GENERAL);
        CreateShopRequest request1 = CreateShopRequest.newInstance("라면 한 박스", 1000, "ramen.jpg", "포인트로 뜨끈한 라면 한 박스 가져가세요!", 1L);
        CreateInformationRequest request2 = CreateInformationRequest.newInstance("윤진원", "대전광역시 유성구", "010-0000-0000", 1L);
        CreateOrderRequest request3 = CreateOrderRequest.newInstance("배송 전 문자 부탁드립니다", 1L, 1L, 1L);

        // when
        userService.join(user);
        shopService.save(request1);
        informationService.save(request2);
        Long savedId = userShopService.save(request3);

        UpdateOrderRequest updateRequest = UpdateOrderRequest.newInstance("경비실에 맡겨주세요", 1L);
        userShopService.updateBody(updateRequest);
        UserShop updateOrder = userShopService.findOne(savedId);

        // then
        assertEquals("경비실에 맡겨주세요", updateOrder.getText());
    }

    @Test(expected = AssertionFailedError.class)
    public void 주문내역_삭제() throws Exception {

        // given
        User user = User.newInstance("2", "donggwon@gmail.com", "profile.png", 1000, "donggwon", Role.GENERAL);
        CreateShopRequest request1 = CreateShopRequest.newInstance("라면 한 박스", 1000, "ramen.jpg", "포인트로 뜨끈한 라면 한 박스 가져가세요!", 1L);
        CreateInformationRequest request2 = CreateInformationRequest.newInstance("윤진원", "대전광역시 유성구", "010-0000-0000", 1L);
        CreateOrderRequest request3 = CreateOrderRequest.newInstance("배송 전 문자 부탁드립니다", 1L, 1L, 1L);

        // when
        userService.join(user);
        shopService.save(request1);
        informationService.save(request2);
        Long savedId = userShopService.save(request3);

        userShopService.deleteOrder(savedId);
        UserShop deleteOrder = userShopService.findOne(savedId);

        // then
        assertEquals(request3, deleteOrder);
    }
}

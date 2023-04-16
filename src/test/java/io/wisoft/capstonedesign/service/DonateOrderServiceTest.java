package io.wisoft.capstonedesign.service;

import io.wisoft.capstonedesign.domain.donate.application.DonateService;
import io.wisoft.capstonedesign.domain.donate.web.dto.CreateDonateRequest;
import io.wisoft.capstonedesign.domain.donateorder.application.DonateOrderService;
import io.wisoft.capstonedesign.domain.donateorder.persistence.DonateOrder;
import io.wisoft.capstonedesign.domain.donateorder.web.dto.CreateOrderRequest;
import io.wisoft.capstonedesign.domain.donateorder.web.dto.UpdateOrderRequest;
import io.wisoft.capstonedesign.domain.information.web.dto.CreateInformationRequest;
import io.wisoft.capstonedesign.domain.user.web.dto.CreateUserRequest;
import io.wisoft.capstonedesign.global.enumerated.Role;
import io.wisoft.capstonedesign.global.enumerated.Tag;
import io.wisoft.capstonedesign.domain.information.application.InformationService;
import io.wisoft.capstonedesign.domain.user.application.UserService;
import io.wisoft.capstonedesign.domain.user.persistence.User;
import io.wisoft.capstonedesign.global.exception.service.OrderNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opentest4j.AssertionFailedError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class DonateOrderServiceTest {

    @Autowired DonateOrderService donateOrderService;
    @Autowired UserService userService;
    @Autowired DonateService donateService;
    @Autowired InformationService informationService;

    @Test
    public void 주문내역_생성() throws Exception {

        // given
        CreateUserRequest request1 = new CreateUserRequest("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", String.valueOf(Role.GENERAL));
        CreateDonateRequest request2 = new CreateDonateRequest("패딩 나눔합니다", "image.png", "안 입는 패딩 기부해요", String.valueOf(Tag.TOP), 1L);
        CreateInformationRequest request3 = new CreateInformationRequest("윤진원", "대전광역시 유성구", "010-0000-0000", 1L);
        CreateOrderRequest request4 = new CreateOrderRequest("배송전 문자주세요", 1L, 1L, 1L);

        // when
        userService.join(request1);
        donateService.join(request2);
        informationService.save(request3);
        Long savedId = donateOrderService.save(request4);

        // then
        assertEquals(request4.getText(), donateOrderService.findById(savedId).getText());
    }

    @Test
    public void 주문내역_조회() throws Exception {

        // given
        CreateUserRequest request1 = new CreateUserRequest("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", String.valueOf(Role.GENERAL));
        CreateDonateRequest request2 = new CreateDonateRequest("패딩 나눔합니다", "image.png", "안 입는 패딩 기부해요", String.valueOf(Tag.TOP), 1L);
        CreateInformationRequest request3 = new CreateInformationRequest("윤진원", "대전광역시 유성구", "010-0000-0000", 1L);
        CreateOrderRequest request4 = new CreateOrderRequest("배송전 문자주세요", 1L, 1L, 1L);

        // when
        userService.join(request1);
        donateService.join(request2);
        informationService.save(request3);
        Long savedId = donateOrderService.save(request4);
        DonateOrder savedOrder = donateOrderService.findById(savedId);

        // then
        assertEquals(request4.getText(), savedOrder.getText());
    }

    @Test(expected = AssertionFailedError.class)
    public void 전체_주문내역_조회() throws Exception {

        // given
        CreateUserRequest request1 = new CreateUserRequest("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", String.valueOf(Role.GENERAL));
        CreateDonateRequest request2 = new CreateDonateRequest("패딩 나눔합니다", "image.png", "안 입는 패딩 기부해요", String.valueOf(Tag.TOP), 1L);
        CreateInformationRequest request3 = new CreateInformationRequest("윤진원", "대전광역시 유성구", "010-0000-0000", 1L);
        CreateOrderRequest request4 = new CreateOrderRequest("배송전 문자주세요", 1L, 1L, 1L);
        CreateOrderRequest request5 = new CreateOrderRequest("경비실에 맡겨주세요", 1L, 1L, 1L);

        // when
        userService.join(request1);
        donateService.join(request2);
        informationService.save(request3);
        donateOrderService.save(request4);
        donateOrderService.save(request5);
        List<DonateOrder> donateOrders = donateOrderService.findDonateOrders();

        // then
        assertEquals(3, donateOrders.size());
    }

    @Test
    public void 개별_주문내역_최근순_조회() throws Exception {

        // given
        CreateUserRequest request1 = new CreateUserRequest("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", String.valueOf(Role.GENERAL));
        CreateDonateRequest request2 = new CreateDonateRequest("패딩 나눔합니다", "image.png", "안 입는 패딩 기부해요", String.valueOf(Tag.TOP), 1L);
        CreateInformationRequest request3 = new CreateInformationRequest("윤진원", "대전광역시 유성구", "010-0000-0000", 1L);
        CreateOrderRequest request4 = new CreateOrderRequest("배송전 문자주세요", 1L, 1L, 1L);
        CreateOrderRequest request5 = new CreateOrderRequest("경비실에 맡겨주세요", 1L, 1L, 1L);

        // when
        Long userId = userService.join(request1);
        donateService.join(request2);
        informationService.save(request3);
        donateOrderService.save(request4);
        donateOrderService.save(request5);
        List<DonateOrder> orderDESC = donateOrderService.findByUser(userId);

        // then
        assertEquals(request5.getText(), orderDESC.get(0).getText());
    }

    @Test
    public void 주문내역_기타사항_수정() throws Exception {

        // given
        CreateUserRequest request1 = new CreateUserRequest("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", String.valueOf(Role.GENERAL));
        CreateDonateRequest request2 = new CreateDonateRequest("패딩 나눔합니다", "image.png", "안 입는 패딩 기부해요", String.valueOf(Tag.TOP), 1L);
        CreateInformationRequest request3 = new CreateInformationRequest("윤진원", "대전광역시 유성구", "010-0000-0000", 1L);
        CreateOrderRequest request4 = new CreateOrderRequest("배송전 문자주세요", 1L, 1L, 1L);

        // when
        userService.join(request1);
        donateService.join(request2);
        informationService.save(request3);
        Long savedId = donateOrderService.save(request4);

        UpdateOrderRequest updateRequest = new UpdateOrderRequest("경비실에 맡겨주세요", 1L);
        donateOrderService.updateBody(savedId, updateRequest);
        DonateOrder updateOrder = donateOrderService.findById(savedId);

        // then
        assertEquals("경비실에 맡겨주세요", updateOrder.getText());
    }

    @Test(expected = OrderNotFoundException.class)
    public void 주문내역_삭제() throws Exception {

        // given
        CreateUserRequest request1 = new CreateUserRequest("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", String.valueOf(Role.GENERAL));
        CreateDonateRequest request2 = new CreateDonateRequest("패딩 나눔합니다", "image.png", "안 입는 패딩 기부해요", String.valueOf(Tag.TOP), 1L);
        CreateInformationRequest request3 = new CreateInformationRequest("윤진원", "대전광역시 유성구", "010-0000-0000", 1L);
        CreateOrderRequest request4 = new CreateOrderRequest("배송전 문자주세요", 1L, 1L, 1L);

        // when
        userService.join(request1);
        donateService.join(request2);
        informationService.save(request3);
        Long savedId = donateOrderService.save(request4);

        donateOrderService.deleteOrder(savedId);
        DonateOrder deleteOrder = donateOrderService.findById(savedId);

        // then
        assertEquals(request4, deleteOrder);
    }
}

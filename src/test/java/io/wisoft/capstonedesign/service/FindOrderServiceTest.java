package io.wisoft.capstonedesign.service;

import io.wisoft.capstonedesign.domain.find.application.FindService;
import io.wisoft.capstonedesign.domain.find.web.dto.CreateFindRequest;
import io.wisoft.capstonedesign.domain.findorder.application.FindOrderService;
import io.wisoft.capstonedesign.domain.findorder.persistence.FindOrder;
import io.wisoft.capstonedesign.domain.findorder.web.dto.CreateOrderRequest;
import io.wisoft.capstonedesign.domain.findorder.web.dto.UpdateOrderRequest;
import io.wisoft.capstonedesign.domain.information.web.dto.CreateInformationRequest;
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
public class FindOrderServiceTest {

    @Autowired FindOrderService findOrderService;
    @Autowired UserService userService;
    @Autowired FindService findService;
    @Autowired InformationService informationService;

    @Test
    public void 주문내역_생성() throws Exception {

        // given
        User user = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", Role.GENERAL);
        CreateFindRequest request1 = CreateFindRequest.newInstance("패딩 찾아봅니다", "image.png", "안 입는 패딩 기부받아요", String.valueOf(Tag.TOP), 1L);
        CreateInformationRequest request2 = CreateInformationRequest.newInstance("윤진원", "대전광역시 유성구", "010-0000-0000", 1L);
        CreateOrderRequest request3 = CreateOrderRequest.newInstance("배송전 문자주세요", 1L, 1L, 1L);

        // when
        userService.join(user);
        findService.join(request1);
        informationService.save(request2);
        Long savedId = findOrderService.save(request3);

        // then
        assertEquals(request3.getText(), findOrderService.findById(savedId).getText());
    }

    @Test
    public void 주문내역_조회() throws Exception {

        // given
        User user = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", Role.GENERAL);
        CreateFindRequest request1 = CreateFindRequest.newInstance("패딩 찾아봅니다", "image.png", "안 입는 패딩 기부받아요", String.valueOf(Tag.TOP), 1L);
        CreateInformationRequest request2 = CreateInformationRequest.newInstance("윤진원", "대전광역시 유성구", "010-0000-0000", 1L);
        CreateOrderRequest request3 = CreateOrderRequest.newInstance("배송전 문자주세요", 1L, 1L, 1L);

        // when
        userService.join(user);
        findService.join(request1);
        informationService.save(request2);
        Long savedId = findOrderService.save(request3);
        FindOrder savedOrder = findOrderService.findById(savedId);

        // then
        assertEquals(request3.getText(), savedOrder.getText());
    }

    @Test(expected = AssertionFailedError.class)
    public void 전체_주문내역_조회() throws Exception {

        // given
        User user = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", Role.GENERAL);
        CreateFindRequest request1 = CreateFindRequest.newInstance("패딩 찾아봅니다", "image.png", "안 입는 패딩 기부받아요", String.valueOf(Tag.TOP), 1L);
        CreateInformationRequest request2 = CreateInformationRequest.newInstance("윤진원", "대전광역시 유성구", "010-0000-0000", 1L);
        CreateOrderRequest request3 = CreateOrderRequest.newInstance("배송전 문자주세요", 1L, 1L, 1L);
        CreateOrderRequest request4 = CreateOrderRequest.newInstance("경비실에 맡겨주세요", 1L, 1L, 1L);

        // when
        userService.join(user);
        findService.join(request1);
        informationService.save(request2);
        findOrderService.save(request3);
        findOrderService.save(request4);
        List<FindOrder> findOrders = findOrderService.findFindOrders();

        // then
        assertEquals(3, findOrders.size());
    }

    @Test
    public void 개별_주문내역_최근순_조회() throws Exception {

        // given
        User user = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", Role.GENERAL);
        CreateFindRequest request1 = CreateFindRequest.newInstance("패딩 찾아봅니다", "image.png", "안 입는 패딩 기부받아요", String.valueOf(Tag.TOP), 1L);
        CreateInformationRequest request2 = CreateInformationRequest.newInstance("윤진원", "대전광역시 유성구", "010-0000-0000", 1L);
        CreateOrderRequest request3 = CreateOrderRequest.newInstance("배송전 문자주세요", 1L, 1L, 1L);
        CreateOrderRequest request4 = CreateOrderRequest.newInstance("경비실에 맡겨주세요", 1L, 1L, 1L);

        // when
        userService.join(user);
        findService.join(request1);
        informationService.save(request2);
        findOrderService.save(request3);
        findOrderService.save(request4);
        List<FindOrder> orderDESC = findOrderService.findByUserDESC(user);

        // then
        assertEquals(request4.getText(), orderDESC.get(0).getText());
    }

    @Test
    public void 주문내역_기타사항_수정() throws Exception {

        // given
        User user = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", Role.GENERAL);
        CreateFindRequest request1 = CreateFindRequest.newInstance("패딩 찾아봅니다", "image.png", "안 입는 패딩 기부받아요", String.valueOf(Tag.TOP), 1L);
        CreateInformationRequest request2 = CreateInformationRequest.newInstance("윤진원", "대전광역시 유성구", "010-0000-0000", 1L);
        CreateOrderRequest request3 = CreateOrderRequest.newInstance("배송전 문자주세요", 1L, 1L, 1L);

        // when
        userService.join(user);
        findService.join(request1);
        informationService.save(request2);
        Long savedId = findOrderService.save(request3);

        UpdateOrderRequest updateRequest = UpdateOrderRequest.newInstance("경비실에 맡겨주세요", 1L);
        findOrderService.updateBody(updateRequest);
        FindOrder updateOrder = findOrderService.findById(savedId);

        // then
        assertEquals("경비실에 맡겨주세요", updateOrder.getText());
    }

    @Test(expected = OrderNotFoundException.class)
    public void 주문내역_삭제() throws Exception {

        // given
        User user = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", Role.GENERAL);
        CreateFindRequest request1 = CreateFindRequest.newInstance("패딩 찾아봅니다", "image.png", "안 입는 패딩 기부받아요", String.valueOf(Tag.TOP), 1L);
        CreateInformationRequest request2 = CreateInformationRequest.newInstance("윤진원", "대전광역시 유성구", "010-0000-0000", 1L);
        CreateOrderRequest request3 = CreateOrderRequest.newInstance("배송전 문자주세요", 1L, 1L, 1L);

        // when
        userService.join(user);
        findService.join(request1);
        informationService.save(request2);
        Long savedId = findOrderService.save(request3);

        findOrderService.deleteOrder(savedId);
        FindOrder deleteOrder = findOrderService.findById(savedId);

        // then
        assertEquals(request3, deleteOrder);
    }
}

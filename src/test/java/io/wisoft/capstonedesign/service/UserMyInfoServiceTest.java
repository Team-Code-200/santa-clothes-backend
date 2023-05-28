package io.wisoft.capstonedesign.service;

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
import io.wisoft.capstonedesign.domain.user.application.UserMyInfoService;
import io.wisoft.capstonedesign.domain.user.application.UserService;
import io.wisoft.capstonedesign.domain.user.web.dto.CreateUserRequest;
import io.wisoft.capstonedesign.domain.usershop.application.UserShopService;
import io.wisoft.capstonedesign.domain.usershop.persistence.UserShop;
import io.wisoft.capstonedesign.global.enumerated.Role;
import io.wisoft.capstonedesign.global.enumerated.Tag;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    public void 마이페이지_나눠줄래요_게시글조회() throws Exception {

        // given
        CreateUserRequest userRequest = new CreateUserRequest("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", String.valueOf(Role.GENERAL));
        CreateDonateRequest donateRequest1 = new CreateDonateRequest("패딩 나눔합니다", "image.png", "안 입는 패딩 기부해요", String.valueOf(Tag.TOP), 1L);
        CreateDonateRequest donateRequest2 = new CreateDonateRequest("바지 나눔합니다", "image.png", "안 입는 바지 기부해요", String.valueOf(Tag.PANTS), 1L);
        PageRequest request = PageRequest.of(0, 5, Sort.by("createdDate").descending());

        // when
        userService.join(userRequest);
        donateService.join(donateRequest1);
        donateService.join(donateRequest2);
        List<Donate> donates = userMyInfoService.findDonatesByIdUsingPaging(1L, request).getContent();

        // then
        assertEquals(donates.get(0).getText(), donateRequest2.text());
    }

    @Test
    public void 마이페이지_찾아볼래요_게시글조회() throws Exception {

        // given
        CreateUserRequest userRequest = new CreateUserRequest("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", String.valueOf(Role.GENERAL));
        CreateFindRequest findRequest1 = new CreateFindRequest("패딩 찾아봅니다", "image.png", "안 입는 패딩 기부받아요", String.valueOf(Tag.TOP), 1L);
        CreateFindRequest findRequest2 = new CreateFindRequest("바지 찾아봅니다", "image.png", "안 입는 바지 기부받아요", String.valueOf(Tag.PANTS), 1L);
        PageRequest request = PageRequest.of(0, 5, Sort.by("createdDate").descending());

        // when
        userService.join(userRequest);
        findService.join(findRequest1);
        findService.join(findRequest2);
        List<Find> finds = userMyInfoService.findFindsByIdUsingPaging(1L, request).getContent();

        // then
        assertEquals(finds.get(0).getText(), findRequest2.text());
    }

    @Test
    public void 마이페이지_배송정보조회() throws Exception {

        // given
        CreateUserRequest userRequest = new CreateUserRequest("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", String.valueOf(Role.GENERAL));
        CreateInformationRequest infoRequest1 = new CreateInformationRequest("윤진원", "대전광역시 유성구", "010-0000-0000", 1L);
        CreateInformationRequest infoRequest2 = new CreateInformationRequest("윤진원", "대전광역시 유성구", "010-0000-0000", 1L);
        PageRequest request = PageRequest.of(0, 5, Sort.by("createdDate").descending());

        // when
        userService.join(userRequest);
        informationService.save(infoRequest1);
        informationService.save(infoRequest2);
        List<Information> infos = userMyInfoService.findInfosByIdUsingPaging(1L, request).getContent();

        // then
        assertEquals(infos.get(0).getAddress(), infoRequest2.address());
    }

    @Test
    public void 마이페이지_나눠줄래요_거래내역() throws Exception {

        // given
        CreateUserRequest userRequest = new CreateUserRequest("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", String.valueOf(Role.GENERAL));
        CreateDonateRequest donateRequest = new CreateDonateRequest("패딩 나눔합니다", "image.png", "안 입는 패딩 기부해요", String.valueOf(Tag.TOP), 1L);
        CreateInformationRequest infoRequest = new CreateInformationRequest("윤진원", "대전광역시 유성구", "010-0000-0000", 1L);
        CreateOrderRequest orderRequest1 = new CreateOrderRequest("배송전 문자주세요", 1L, 1L, 1L);
        CreateOrderRequest orderRequest2 = new CreateOrderRequest("경비실에 맡겨주세요", 1L, 1L, 1L);
        PageRequest request = PageRequest.of(0, 5, Sort.by("sendDate").descending());

        // when
        userService.join(userRequest);
        donateService.join(donateRequest);
        informationService.save(infoRequest);
        donateOrderService.save(orderRequest1);
        donateOrderService.save(orderRequest2);
        List<DonateOrder> orders = userMyInfoService.findDonateOrdersByIdUsingPaging(1L, request).getContent();

        // then
        assertEquals(orders.get(0).getText(), orderRequest2.text());
    }

    @Test
    public void 마이페이지_찾아볼래요_거래내역() throws Exception {

        // given
        CreateUserRequest userRequest = new CreateUserRequest("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", String.valueOf(Role.GENERAL));
        CreateFindRequest findRequest = new CreateFindRequest("패딩 찾아봅니다", "image.png", "안 입는 패딩 기부받아요", String.valueOf(Tag.TOP), 1L);
        CreateInformationRequest infoRequest = new CreateInformationRequest("윤진원", "대전광역시 유성구", "010-0000-0000", 1L);
        io.wisoft.capstonedesign.domain.findorder.web.dto.CreateOrderRequest orderRequest1 = new io.wisoft.capstonedesign.domain.findorder.web.dto.CreateOrderRequest("배송전 문자주세요", 1L, 1L, 1L);
        io.wisoft.capstonedesign.domain.findorder.web.dto.CreateOrderRequest orderRequest2 = new io.wisoft.capstonedesign.domain.findorder.web.dto.CreateOrderRequest("경비실에 맡겨주세요", 1L, 1L, 1L);
        PageRequest request = PageRequest.of(0, 5, Sort.by("sendDate").descending());

        // when
        userService.join(userRequest);
        findService.join(findRequest);
        informationService.save(infoRequest);
        findOrderService.save(orderRequest1);
        findOrderService.save(orderRequest2);
        List<FindOrder> orders = userMyInfoService.findFindOrdersByIdUsingPaging(1L, request).getContent();

        // then
        assertEquals(orders.get(0).getText(), orderRequest2.text());
    }

    @Test
    public void 마이페이지_산타샵_주문내역() throws Exception {

        // given
        CreateUserRequest userRequest = new CreateUserRequest("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", String.valueOf(Role.GENERAL));
        CreateShopRequest shopRequest = new CreateShopRequest("라면 한 박스", 1000, "ramen.jpg", "포인트로 뜨끈한 라면 한 박스 가져가세요!", 1L);
        CreateInformationRequest inforRequest = new CreateInformationRequest("윤진원", "대전광역시 유성구", "010-0000-0000", 1L);
        io.wisoft.capstonedesign.domain.usershop.web.dto.CreateOrderRequest orderRequest1 = new io.wisoft.capstonedesign.domain.usershop.web.dto.CreateOrderRequest("배송 전 문자 부탁드립니다", 1L, 1L, 1L);
        io.wisoft.capstonedesign.domain.usershop.web.dto.CreateOrderRequest orderRequest2 = new io.wisoft.capstonedesign.domain.usershop.web.dto.CreateOrderRequest("경비실에 맡겨주세요", 1L, 1L, 1L);
        PageRequest request = PageRequest.of(0, 5, Sort.by("createdDate").descending());

        // when
        userService.join(userRequest);
        shopService.save(shopRequest);
        informationService.save(inforRequest);
        userShopService.save(orderRequest1);
        userShopService.save(orderRequest2);
        List<UserShop> shops = userMyInfoService.findShopOrdersByIdUsingPaging(1L, request).getContent();

        // then
        assertEquals(shops.get(0).getText(), orderRequest2.text());
    }
}

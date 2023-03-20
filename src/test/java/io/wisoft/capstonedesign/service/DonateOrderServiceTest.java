package io.wisoft.capstonedesign.service;

import io.wisoft.capstonedesign.domain.*;
import io.wisoft.capstonedesign.repository.DonateOrderRepository;
import io.wisoft.capstonedesign.repository.DonateRepository;
import io.wisoft.capstonedesign.repository.InformationRepository;
import io.wisoft.capstonedesign.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opentest4j.AssertionFailedError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback(value = false)
public class DonateOrderServiceTest {

    @Autowired DonateOrderRepository donateOrderRepository;
    @Autowired UserRepository userRepository;
    @Autowired DonateRepository donateRepository;
    @Autowired InformationRepository informationRepository;
    @Autowired DonateOrderService donateOrderService;
    @Autowired UserService userService;
    @Autowired DonateService donateService;
    @Autowired InformationService informationService;

    @Test
    public void 주문내역_생성() throws Exception {

        // given
        User user = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", LocalDateTime.now(), Role.GENERAL);
        Donate donate = Donate.createDonate("패딩 나눔합니다", "image.png", "안 입는 패딩 기부해요", 0, Tag.TOP, user);
        Information information = Information.createInformation("윤진원", "대전광역시 관평동", "010-0000-0000", user);
        DonateOrder donateOrder = DonateOrder.createDonateOrder("배송전 문자 부탁드립니다", information, donate, user);

        // when
        userService.join(user);
        donateService.join(donate);
        informationService.save(information);
        Long savedId = donateOrderService.save(donateOrder);

        // then
        assertEquals(donateOrder, donateOrderRepository.findOne(savedId));
    }

    @Test
    public void 주문내역_조회() throws Exception {

        // given
        User user = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", LocalDateTime.now(), Role.GENERAL);
        Donate donate = Donate.createDonate("패딩 나눔합니다", "image.png", "안 입는 패딩 기부해요", 0, Tag.TOP, user);
        Information information = Information.createInformation("윤진원", "대전광역시 관평동", "010-0000-0000", user);
        DonateOrder donateOrder = DonateOrder.createDonateOrder("배송전 문자 부탁드립니다", information, donate, user);

        // when
        userService.join(user);
        donateService.join(donate);
        informationService.save(information);
        Long savedId = donateOrderService.save(donateOrder);
        DonateOrder savedOrder = donateOrderService.findOne(savedId);

        // then
        assertEquals(donateOrder, savedOrder);
    }

    @Test(expected = AssertionFailedError.class)
    public void 전체_주문내역_조회() throws Exception {

        // given
        User user = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", LocalDateTime.now(), Role.GENERAL);
        Donate donate = Donate.createDonate("패딩 나눔합니다", "image.png", "안 입는 패딩 기부해요", 0, Tag.TOP, user);
        Information information = Information.createInformation("윤진원", "대전광역시 관평동", "010-0000-0000", user);
        DonateOrder donateOrder1 = DonateOrder.createDonateOrder("배송전 문자 부탁드립니다", information, donate, user);
        DonateOrder donateOrder2 = DonateOrder.createDonateOrder("경비실에 맡겨주세요", information, donate, user);

        // when
        userService.join(user);
        donateService.join(donate);
        informationService.save(information);
        donateOrderService.save(donateOrder1);
        donateOrderService.save(donateOrder2);
        List<DonateOrder> donateOrders = donateOrderService.findDonateOrders();

        // then
        assertEquals(3, donateOrders.size());
    }

    @Test
    public void 개별_주문내역_최근순_조회() throws Exception {

        // given
        User user = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", LocalDateTime.now(), Role.GENERAL);
        Donate donate = Donate.createDonate("패딩 나눔합니다", "image.png", "안 입는 패딩 기부해요", 0, Tag.TOP, user);
        Information information = Information.createInformation("윤진원", "대전광역시 관평동", "010-0000-0000", user);
        DonateOrder donateOrder1 = DonateOrder.createDonateOrder("배송전 문자 부탁드립니다.", information, donate, user);
        DonateOrder donateOrder2 = DonateOrder.createDonateOrder("경비실에 맡겨주세요", information, donate, user);

        // when
        userService.join(user);
        donateService.join(donate);
        informationService.save(information);
        donateOrderService.save(donateOrder1);
        donateOrderService.save(donateOrder2);
        List<DonateOrder> orderDESC = donateOrderService.findByUserDESC(user);

        // then
        assertEquals(donateOrder2, orderDESC.get(0));
    }

    @Test
    public void 주문내역_기타사항_수정() throws Exception {

        // given
        User user = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", LocalDateTime.now(), Role.GENERAL);
        Donate donate = Donate.createDonate("패딩 나눔합니다", "image.png", "안 입는 패딩 기부해요", 0, Tag.TOP, user);
        Information information = Information.createInformation("윤진원", "대전광역시 관평동", "010-0000-0000", user);
        DonateOrder donateOrder = DonateOrder.createDonateOrder("배송전 문자 부탁드립니다.", information, donate, user);

        // when
        userService.join(user);
        donateService.join(donate);
        informationService.save(information);
        Long savedId = donateOrderService.save(donateOrder);

        donateOrderService.updateBody(savedId, "경비실에 맡겨주세요");
        DonateOrder updateOrder = donateOrderService.findOne(savedId);

        // then
        assertEquals("경비실에 맡겨주세요", updateOrder.getText());
    }

    @Test(expected = AssertionFailedError.class)
    public void 주문내역_삭제() throws Exception {

        // given
        User user = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", LocalDateTime.now(), Role.GENERAL);
        Donate donate = Donate.createDonate("패딩 나눔합니다", "image.png", "안 입는 패딩 기부해요", 0, Tag.TOP, user);
        Information information = Information.createInformation("윤진원", "대전광역시 관평동", "010-0000-0000", user);
        DonateOrder donateOrder = DonateOrder.createDonateOrder("배송전 문자 부탁드립니다.", information, donate, user);

        // when
        userService.join(user);
        donateService.join(donate);
        informationService.save(information);
        Long savedId = donateOrderService.save(donateOrder);

        donateOrderService.deleteOrder(savedId);
        DonateOrder deleteOrder = donateOrderService.findOne(savedId);

        // then
        assertEquals(donateOrder, deleteOrder);
    }
}

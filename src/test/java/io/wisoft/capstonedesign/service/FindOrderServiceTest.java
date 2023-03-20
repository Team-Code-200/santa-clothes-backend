package io.wisoft.capstonedesign.service;

import io.wisoft.capstonedesign.domain.*;
import io.wisoft.capstonedesign.repository.*;
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
public class FindOrderServiceTest {

    @Autowired FindOrderRepository findOrderRepository;
    @Autowired UserRepository userRepository;
    @Autowired FindRepository findRepository;
    @Autowired InformationRepository informationRepository;
    @Autowired FindOrderService findOrderService;
    @Autowired UserService userService;
    @Autowired FindService findService;
    @Autowired InformationService informationService;

    @Test
    public void 주문내역_생성() throws Exception {

        // given
        User user = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", LocalDateTime.now(), Role.GENERAL);
        Find find = Find.createFind("패딩 찾아봅니다", "안 입는 패딩 기부받아요", "image.png", 0, Tag.TOP, user);
        Information information = Information.createInformation("윤진원", "대전광역시 관평동", "010-0000-0000", user);
        FindOrder findOrder = FindOrder.createFindOrder("배송전 문자 부탁드립니다", information, find, user);

        // when
        userService.join(user);
        findService.join(find);
        informationService.save(information);
        Long savedId = findOrderService.save(findOrder);

        // then
        assertEquals(findOrder, findOrderRepository.findOne(savedId));
    }

    @Test
    public void 주문내역_조회() throws Exception {

        // given
        User user = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", LocalDateTime.now(), Role.GENERAL);
        Find find = Find.createFind("패딩 찾아봅니다", "안 입는 패딩 기부받아요", "image.png", 0, Tag.TOP, user);
        Information information = Information.createInformation("윤진원", "대전광역시 관평동", "010-0000-0000", user);
        FindOrder findOrder = FindOrder.createFindOrder("배송전 문자 부탁드립니다", information, find, user);

        // when
        userService.join(user);
        findService.join(find);
        informationService.save(information);
        Long savedId = findOrderService.save(findOrder);
        FindOrder savedOrder = findOrderService.findOne(savedId);

        // then
        assertEquals(findOrder, savedOrder);
    }

    @Test(expected = AssertionFailedError.class)
    public void 전체_주문내역_조회() throws Exception {

        // given
        User user = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", LocalDateTime.now(), Role.GENERAL);
        Find find = Find.createFind("패딩 찾아봅니다", "안 입는 패딩 기부받아요", "image.png", 0, Tag.TOP, user);
        Information information = Information.createInformation("윤진원", "대전광역시 관평동", "010-0000-0000", user);
        FindOrder findOrder1 = FindOrder.createFindOrder("배송전 문자 부탁드립니다", information, find, user);
        FindOrder findOrder2 = FindOrder.createFindOrder("경비실에 맡겨주세요", information, find, user);

        // when
        userService.join(user);
        findService.join(find);
        informationService.save(information);
        findOrderService.save(findOrder1);
        findOrderService.save(findOrder2);
        List<FindOrder> findOrders = findOrderService.findFindOrders();

        // then
        assertEquals(3, findOrders.size());
    }

    @Test
    public void 개별_주문내역_최근순_조회() throws Exception {

        // given
        User user = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", LocalDateTime.now(), Role.GENERAL);
        Find find = Find.createFind("패딩 찾아봅니다", "안 입는 패딩 기부받아요", "image.png", 0, Tag.TOP, user);
        Information information = Information.createInformation("윤진원", "대전광역시 관평동", "010-0000-0000", user);
        FindOrder findOrder1 = FindOrder.createFindOrder("배송전 문자 부탁드립니다", information, find, user);
        FindOrder findOrder2 = FindOrder.createFindOrder("경비실에 맡겨주세요", information, find, user);

        // when
        userService.join(user);
        findService.join(find);
        informationService.save(information);
        findOrderService.save(findOrder1);
        findOrderService.save(findOrder2);
        List<FindOrder> orderDESC = findOrderService.findByUserDESC(user);

        // then
        assertEquals(findOrder2, orderDESC.get(0));
    }

    @Test
    public void 주문내역_기타사항_수정() throws Exception {

        // given
        User user = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", LocalDateTime.now(), Role.GENERAL);
        Find find = Find.createFind("패딩 찾아봅니다", "안 입는 패딩 기부받아요", "image.png", 0, Tag.TOP, user);
        Information information = Information.createInformation("윤진원", "대전광역시 관평동", "010-0000-0000", user);
        FindOrder findOrder = FindOrder.createFindOrder("배송전 문자 부탁드립니다", information, find, user);

        // when
        userService.join(user);
        findService.join(find);
        informationService.save(information);
        Long savedId = findOrderService.save(findOrder);

        findOrderService.updateBody(savedId, "경비실에 맡겨주세요");
        FindOrder updateOrder = findOrderService.findOne(savedId);

        // then
        assertEquals("경비실에 맡겨주세요", updateOrder.getText());
    }

    @Test(expected = AssertionFailedError.class)
    public void 주문내역_삭제() throws Exception {

        // given
        User user = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", LocalDateTime.now(), Role.GENERAL);
        Find find = Find.createFind("패딩 찾아봅니다", "안 입는 패딩 기부받아요", "image.png", 0, Tag.TOP, user);
        Information information = Information.createInformation("윤진원", "대전광역시 관평동", "010-0000-0000", user);
        FindOrder findOrder = FindOrder.createFindOrder("배송전 문자 부탁드립니다", information, find, user);

        // when
        userService.join(user);
        findService.join(find);
        informationService.save(information);
        Long savedId = findOrderService.save(findOrder);

        findOrderService.deleteOrder(savedId);
        FindOrder deleteOrder = findOrderService.findOne(savedId);

        // then
        assertEquals(findOrder, deleteOrder);
    }
}

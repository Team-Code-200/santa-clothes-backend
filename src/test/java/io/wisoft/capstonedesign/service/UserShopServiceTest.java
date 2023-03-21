package io.wisoft.capstonedesign.service;

import io.wisoft.capstonedesign.domain.*;
import io.wisoft.capstonedesign.repository.InformationRepository;
import io.wisoft.capstonedesign.repository.ShopRepository;
import io.wisoft.capstonedesign.repository.UserRepository;
import io.wisoft.capstonedesign.repository.UserShopRepository;
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
public class UserShopServiceTest {

    @Autowired UserShopRepository userShopRepository;
    @Autowired UserRepository userRepository;
    @Autowired ShopRepository shopRepository;
    @Autowired InformationRepository informationRepository;
    @Autowired UserShopService userShopService;
    @Autowired UserService userService;
    @Autowired ShopService shopService;
    @Autowired InformationService informationService;

    @Test
    public void 주문내역_생성() throws Exception {

        // given
        User user = User.newInstance("2", "donggwon@gmail.com", "profile.png", 1000, "donggwon", LocalDateTime.now(), Role.GENERAL);
        Shop shop = Shop.createShop("라면 한 박스", 1000, "ramen.jpg", "포인트로 뜨끈한 라면 한 박스 가져가세요!");
        Information information = Information.createInformation("윤진원", "대전광역시 관평동", "010-0000-0000", user);
        UserShop userShop = UserShop.createUserShop("배송전 문자 부탁드립니다", user, shop, information);

        // when
        userService.join(user);
        shopService.save(shop);
        informationService.save(information);
        Long savedId = userShopService.save(userShop);

        // then
        assertEquals(userShop, userShopRepository.findOne(savedId));
    }

    @Test
    public void 주문내역_조회() throws Exception {

        // given
        User user = User.newInstance("2", "donggwon@gmail.com", "profile.png", 1000, "donggwon", LocalDateTime.now(), Role.GENERAL);
        Shop shop = Shop.createShop("라면 한 박스", 1000, "ramen.jpg", "포인트로 뜨끈한 라면 한 박스 가져가세요!");
        Information information = Information.createInformation("윤진원", "대전광역시 관평동", "010-0000-0000", user);
        UserShop userShop = UserShop.createUserShop("배송전 문자 부탁드립니다", user, shop, information);

        // when
        userService.join(user);
        shopService.save(shop);
        informationService.save(information);
        Long savedId = userShopService.save(userShop);
        UserShop savedOrder = userShopService.findOne(savedId);

        // then
        assertEquals(userShop, savedOrder);
    }

    @Test(expected = AssertionFailedError.class)
    public void 전체_주문내역_조회() throws Exception {

        // given
        User user = User.newInstance("2", "donggwon@gmail.com", "profile.png", 1000, "donggwon", LocalDateTime.now(), Role.GENERAL);
        Shop shop = Shop.createShop("라면 한 박스", 1000, "ramen.jpg", "포인트로 뜨끈한 라면 한 박스 가져가세요!");
        Information information = Information.createInformation("윤진원", "대전광역시 관평동", "010-0000-0000", user);
        UserShop userShop1 = UserShop.createUserShop("배송전 문자 부탁드립니다", user, shop, information);
        UserShop userShop2 = UserShop.createUserShop("경비실에 맡겨주세요", user, shop, information);

        // when
        userService.join(user);
        shopService.save(shop);
        informationService.save(information);
        userShopService.save(userShop1);
        userShopService.save(userShop2);
        List<UserShop> shopOrders = userShopService.findShopOrders();

        // then
        assertEquals(3, shopOrders.size());
    }

    @Test
    public void 개별_주문내역_최근순_조회() throws Exception {

        // given
        User user = User.newInstance("2", "donggwon@gmail.com", "profile.png", 1000, "donggwon", LocalDateTime.now(), Role.GENERAL);
        Shop shop = Shop.createShop("라면 한 박스", 1000, "ramen.jpg", "포인트로 뜨끈한 라면 한 박스 가져가세요!");
        Information information = Information.createInformation("윤진원", "대전광역시 관평동", "010-0000-0000", user);
        UserShop userShop1 = UserShop.createUserShop("배송전 문자 부탁드립니다", user, shop, information);
        UserShop userShop2 = UserShop.createUserShop("경비실에 맡겨주세요", user, shop, information);

        // when
        userService.join(user);
        shopService.save(shop);
        informationService.save(information);
        userShopService.save(userShop1);
        userShopService.save(userShop2);
        List<UserShop> orderDESC = userShopService.findByUserDESC(user);

        // then
        assertEquals(userShop2, orderDESC.get(0));
    }

    @Test
    public void 주문내역_기타사항_수정() throws Exception {

        // given
        User user = User.newInstance("2", "donggwon@gmail.com", "profile.png", 1000, "donggwon", LocalDateTime.now(), Role.GENERAL);
        Shop shop = Shop.createShop("라면 한 박스", 1000, "ramen.jpg", "포인트로 뜨끈한 라면 한 박스 가져가세요!");
        Information information = Information.createInformation("윤진원", "대전광역시 관평동", "010-0000-0000", user);
        UserShop userShop = UserShop.createUserShop("배송전 문자 부탁드립니다", user, shop, information);

        // when
        userService.join(user);
        shopService.save(shop);
        informationService.save(information);
        Long savedId = userShopService.save(userShop);

        userShopService.updateBody(savedId, "경비실에 맡겨주세요");
        UserShop updateOrder = userShopService.findOne(savedId);

        // then
        assertEquals("경비실에 맡겨주세요", updateOrder.getText());
    }

    @Test(expected = AssertionFailedError.class)
    public void 주문내역_삭제() throws Exception {

        // given
        User user = User.newInstance("2", "donggwon@gmail.com", "profile.png", 1000, "donggwon", LocalDateTime.now(), Role.GENERAL);
        Shop shop = Shop.createShop("라면 한 박스", 1000, "ramen.jpg", "포인트로 뜨끈한 라면 한 박스 가져가세요!");
        Information information = Information.createInformation("윤진원", "대전광역시 관평동", "010-0000-0000", user);
        UserShop userShop = UserShop.createUserShop("배송전 문자 부탁드립니다", user, shop, information);

        // when
        userService.join(user);
        shopService.save(shop);
        informationService.save(information);
        Long savedId = userShopService.save(userShop);

        userShopService.deleteOrder(savedId);
        UserShop deleteOrder = userShopService.findOne(savedId);

        // then
        assertEquals(userShop, deleteOrder);
    }
}

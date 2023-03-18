package io.wisoft.capstonedesign.service;

import io.wisoft.capstonedesign.domain.Role;
import io.wisoft.capstonedesign.domain.Shop;
import io.wisoft.capstonedesign.domain.User;
import io.wisoft.capstonedesign.repository.ShopRepository;
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
public class ShopServiceTest {

    @Autowired ShopRepository shopRepository;
    @Autowired UserRepository userRepository;
    @Autowired ShopService shopService;
    @Autowired UserService userService;

    @Test
    public void 산타샵_물품_생성() throws Exception {

        // given
        User user = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", LocalDateTime.now(), Role.ADMIN);
        Shop shop = Shop.createShop("라면 한 박스", 1000, "ramen.jpg", "포인트로 뜨끈한 라면 한 박스 가져가세요!");

        // when
        userService.join(user);
        Long savedId = shopService.save(shop);

        // then
        assertEquals(shop, shopService.findOne(savedId));
    }

    @Test
    public void 물품_이름으로_조회() throws Exception {

        // given
        User user = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", LocalDateTime.now(), Role.ADMIN);
        Shop shop = Shop.createShop("라면 한 박스", 1000, "ramen.jpg", "포인트로 뜨끈한 라면 한 박스 가져가세요!");

        // when
        userService.join(user);
        shopService.save(shop);

        // then
        assertEquals("라면 한 박스", shop.getTitle());
    }

    @Test
    public void 물품_내림차순_조회() throws Exception {

        // given
        User user = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", LocalDateTime.now(), Role.ADMIN);
        Shop shop1 = Shop.createShop("라면 한 박스", 1000, "ramen.jpg", "포인트로 뜨끈한 라면 한 박스 가져가세요!");
        Shop shop2 = Shop.createShop("쌀 10kg", 2000, "rice.jpg", "포인트로 든든한 쌀밥 가져가세요!");

        // when
        userService.join(user);
        shopService.save(shop1);
        shopService.save(shop2);
        List<Shop> shopDESC = shopService.findByCreatedDateDESC();

        // then
        assertEquals(shop2, shopDESC.get(0));
    }

    @Test
    public void 물품_전체_수정() throws Exception {

        // given
        User user = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", LocalDateTime.now(), Role.ADMIN);
        Shop shop = Shop.createShop("라면 한 박스", 1000, "ramen.jpg", "포인트로 뜨끈한 라면 한 박스 가져가세요!");

        // when
        userService.join(user);
        Long savedId = shopService.save(shop);

        shopService.updateAll(savedId, "라면 한 박스", 2000, "ramen.jpg", "포인트로 뜨끈한 라면 한 박스 가져가세요!");
        Shop updateShop = shopService.findOne(savedId);

        // then
        assertEquals(2000, updateShop.getPrice());
    }

    @Test(expected = AssertionFailedError.class)
    public void 물품_삭제() throws Exception {

        // given
        User user = User.newInstance("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", LocalDateTime.now(), Role.ADMIN);
        Shop shop = Shop.createShop("라면 한 박스", 1000, "ramen.jpg", "포인트로 뜨끈한 라면 한 박스 가져가세요!");

        // when
        userService.join(user);
        Long savedId = shopService.save(shop);

        shopService.deleteShop(savedId);
        Shop deleteShop = shopService.findOne(savedId);

        // then
        assertEquals(shop, deleteShop);
    }
}

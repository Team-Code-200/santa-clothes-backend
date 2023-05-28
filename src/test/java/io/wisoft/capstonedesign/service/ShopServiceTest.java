package io.wisoft.capstonedesign.service;

import io.wisoft.capstonedesign.domain.shop.web.dto.CreateShopRequest;
import io.wisoft.capstonedesign.domain.shop.web.dto.UpdateShopRequest;
import io.wisoft.capstonedesign.domain.user.web.dto.CreateUserRequest;
import io.wisoft.capstonedesign.global.enumerated.Role;
import io.wisoft.capstonedesign.domain.shop.application.ShopService;
import io.wisoft.capstonedesign.domain.shop.persistence.Shop;
import io.wisoft.capstonedesign.domain.user.application.UserService;
import io.wisoft.capstonedesign.global.exception.service.PostNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ShopServiceTest {

    @Autowired ShopService shopService;
    @Autowired UserService userService;

    @Test
    public void 산타샵_물품_생성() throws Exception {

        // given
        CreateUserRequest request1 = new CreateUserRequest("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", String.valueOf(Role.GENERAL));
        CreateShopRequest request2 = new CreateShopRequest("라면 한 박스", 1000, "ramen.jpg", "포인트로 뜨끈한 라면 한 박스 가져가세요!", 1L);

        // when
        userService.join(request1);
        Long savedId = shopService.save(request2);

        // then
        assertEquals(request2.title(), shopService.findById(savedId).getTitle());
    }

    @Test
    public void 전체_산타샵_물품조회() throws Exception {

        // given
        CreateUserRequest request1 = new CreateUserRequest("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", String.valueOf(Role.GENERAL));
        CreateShopRequest request2 = new CreateShopRequest("라면 한 박스", 1000, "ramen.jpg", "포인트로 뜨끈한 라면 한 박스 가져가세요!", 1L);
        CreateShopRequest request3 = new CreateShopRequest("쌀 10kg", 2000, "rice.jpg", "포인트로 든든한 쌀 밥 가져가세요!", 1L);

        // when
        userService.join(request1);
        shopService.save(request2);
        shopService.save(request3);
        List<Shop> shopList = shopService.findShopList();

        // then
        assertEquals(2, shopList.size());
    }

    @Test
    public void 물품_이름으로_조회() throws Exception {

        // given
        CreateUserRequest request1 = new CreateUserRequest("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", String.valueOf(Role.GENERAL));
        CreateShopRequest request2 = new CreateShopRequest("라면 한 박스", 1000, "ramen.jpg", "포인트로 뜨끈한 라면 한 박스 가져가세요!", 1L);

        // when
        userService.join(request1);
        shopService.save(request2);

        // then
        assertEquals("라면 한 박스", request2.title());
    }

    @Test
    public void 전체물품_최근순_조회() throws Exception {

        // given
        CreateUserRequest request1 = new CreateUserRequest("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", String.valueOf(Role.GENERAL));
        CreateShopRequest request2 = new CreateShopRequest("라면 한 박스", 1000, "ramen.jpg", "포인트로 뜨끈한 라면 한 박스 가져가세요!", 1L);
        CreateShopRequest request3 = new CreateShopRequest("쌀 10kg", 2000, "rice.jpg", "포인트로 든든한 쌀 밥 가져가세요!", 1L);
        PageRequest request = PageRequest.of(0, 5, Sort.by("createdDate").descending());

        // when
        userService.join(request1);
        shopService.save(request2);
        shopService.save(request3);
        List<Shop> shops = shopService.findByCreatedDateDescUsingPaging(request).getContent();

        // then
        assertEquals(request3.title(), shops.get(0).getTitle());
    }

    @Test
    public void 물품_전체_수정() throws Exception {

        // given
        CreateUserRequest request1 = new CreateUserRequest("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", String.valueOf(Role.GENERAL));
        CreateShopRequest request2 = new CreateShopRequest("라면 한 박스", 1000, "ramen.jpg", "포인트로 뜨끈한 라면 한 박스 가져가세요!", 1L);

        // when
        userService.join(request1);
        Long savedId = shopService.save(request2);

        UpdateShopRequest updateRequest = new UpdateShopRequest("쌀 10kg", 2000, "rice.png", "포인트로 든든한 쌀 밥 가져가세요!", 1L);
        shopService.updateAll(savedId, updateRequest);
        Shop updateShop = shopService.findById(savedId);

        // then
        assertEquals(2000, updateShop.getPrice());
    }

    @Test
    public void 물품_삭제() throws Exception {

        // given
        CreateUserRequest request1 = new CreateUserRequest("1", "jinwon@gmail.com", "profile.png", 1000, "jinwon", String.valueOf(Role.GENERAL));
        CreateShopRequest request2 = new CreateShopRequest("라면 한 박스", 1000, "ramen.jpg", "포인트로 뜨끈한 라면 한 박스 가져가세요!", 1L);

        // when
        userService.join(request1);
        Long savedId = shopService.save(request2);

        shopService.deleteShop(savedId);

        // then
        assertThrows(PostNotFoundException.class, () -> shopService.findById(savedId));
    }
}

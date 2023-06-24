package io.wisoft.capstonedesign.domain.shop.application;

import io.wisoft.capstonedesign.domain.shop.persistence.Shop;
import io.wisoft.capstonedesign.domain.shop.web.dto.CreateShopRequest;
import io.wisoft.capstonedesign.domain.shop.web.dto.UpdateShopRequest;
import io.wisoft.capstonedesign.domain.user.application.UserService;
import io.wisoft.capstonedesign.domain.user.web.dto.CreateUserRequest;
import io.wisoft.capstonedesign.global.exception.service.PostNotFoundException;
import io.wisoft.capstonedesign.global.exception.service.UserNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static io.wisoft.capstonedesign.setting.data.DefaultShopData.createDefaultShop;
import static io.wisoft.capstonedesign.setting.data.DefaultUserData.createDefaultUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class ShopServiceTest {

    @Autowired ShopService shopService;
    @Autowired UserService userService;

    @Nested
    @DisplayName("산타샵 물품 생성 테스트")
    class CreateProduct {

        @Test
        @DisplayName("산타샵 물품 생성시 정상적으로 생성되어야 한다,")
        void create_product() {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateShopRequest shopRequest = createDefaultShop(userId);

            // when
            Long savedId = shopService.save(shopRequest);

            // then
            Shop shop = shopService.findById(savedId);
            assertEquals("라면 한 박스", shop.getTitle());
        }

        @Test
        @DisplayName("회원이 아닌 사용자가 물품 생성시 예외가 발생해야 한다,")
        void create_product_fail() {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            userService.join(userRequest);

            CreateShopRequest shopRequest = createDefaultShop(100L);

            // when

            // then
            assertThrows(UserNotFoundException.class, () -> shopService.save(shopRequest));
        }
    }

    @Nested
    @DisplayName("산타샵 물품 조회 테스트")
    class FindProduct {

        @Test
        @DisplayName("물품 단건 조회 요청시 한 물품이 조회되어야 한다.")
        void find_single_product() {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateShopRequest shopRequest = createDefaultShop(userId);
            Long savedId = shopService.save(shopRequest);

            // when
            Shop savedShop = shopService.findById(savedId);

            // then
            assertEquals("라면 한 박스", savedShop.getTitle());
            assertEquals("포인트로 뜨끈한 라면 한 박스 가져가세요!", savedShop.getBody());
        }

        @Test
        @DisplayName("존재하지 않는 물품을 조회시 예외가 발생해야 한다.")
        void find_single_product_fail() {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateShopRequest shopRequest = createDefaultShop(userId);
            shopService.save(shopRequest);

            // when

            // then
            assertThrows(PostNotFoundException.class, () -> shopService.findById(100L));
        }

        @Test
        @DisplayName("전체 산타샵 물품 조회시 전체 물품 목록을 반환해야 한다.")
        void find_shop_products() {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateShopRequest shopRequest1 = createDefaultShop(userId);
            shopService.save(shopRequest1);

            CreateShopRequest shopRequest2 = CreateShopRequest.builder()
                    .title("쌀 10kg")
                    .price(2000)
                    .image("rice.jpg")
                    .body("포인트로 든든한 쌀 밥 가져가세요!")
                    .userId(userId)
                    .build();
            shopService.save(shopRequest2);

            // when
            List<Shop> shopList = shopService.findShopList();

            // then
            assertEquals(2, shopList.size());
            assertEquals("라면 한 박스", shopList.get(0).getTitle());
        }

        @Test
        @DisplayName("산타샵 물품 이름으로 조회시 이름에 맞는 물품을 반환한다.")
        void find_product_by_name() {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateShopRequest shopRequest = createDefaultShop(userId);
            Long savedId = shopService.save(shopRequest);

            Shop shop = shopService.findById(savedId);

            // when
            List<Shop> shopByTitle = shopService.findShopByTitle(shop.getTitle());

            // then
            assertEquals("라면 한 박스", shopByTitle.get(0).getTitle());
        }

        @Test
        @DisplayName("전체 물품을 페이징을 사용하여 조회시 페이지 번호와 내림차순으로 정렬된 페이지가 반환되어야 한다.")
        void find_products_using_paging() {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateShopRequest shopRequest1 = createDefaultShop(userId);
            shopService.save(shopRequest1);

            CreateShopRequest shopRequest2 = CreateShopRequest.builder()
                    .title("쌀 10kg")
                    .price(2000)
                    .image("rice.jpg")
                    .body("포인트로 든든한 쌀 밥 가져가세요!")
                    .userId(userId)
                    .build();
            shopService.save(shopRequest2);

            CreateShopRequest shopRequest3 = CreateShopRequest.builder()
                    .title("휴지 20롤")
                    .price(3000)
                    .image("paper.jpg")
                    .body("포인트로 떨어진 휴지 챙겨가세요!")
                    .userId(userId)
                    .build();
            shopService.save(shopRequest3);
            PageRequest request = PageRequest.of(0, 5, Sort.by("createdDate").descending());

            // when
            List<Shop> shopList = shopService.findByCreatedDateDescUsingPaging(request).getContent();

            // then
            assertEquals("휴지 20롤", shopList.get(0).getTitle());
        }
    }

    @Nested
    @DisplayName("산타샵 물품 수정 테스트")
    class UpdateProduct {

        @Test
        @DisplayName("산타샵 물품 수정시 물품이 수정되어야 한다.")
        void update_product() {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateShopRequest shopRequest = createDefaultShop(userId);
            Long savedId = shopService.save(shopRequest);

            UpdateShopRequest updateRequest = UpdateShopRequest.builder()
                    .title("쌀 10kg")
                    .price(2000)
                    .image("rice.jpg")
                    .body("포인트로 든든한 쌀 밥 가져가세요!")
                    .shopId(savedId)
                    .build();

            // when
            shopService.updateAll(savedId, updateRequest);
            Shop updateShop = shopService.findById(savedId);

            // then
            assertEquals("쌀 10kg", updateShop.getTitle());
        }

        @Test
        @DisplayName("존재하지 않는 물품을 수정시 예외가 발생해야 한다.")
        void update_product_fail() {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateShopRequest shopRequest = createDefaultShop(userId);
            Long savedId = shopService.save(shopRequest);

            UpdateShopRequest updateRequest = UpdateShopRequest.builder()
                    .title("쌀 10kg")
                    .price(2000)
                    .image("rice.jpg")
                    .body("포인트로 든든한 쌀 밥 가져가세요!")
                    .shopId(savedId)
                    .build();

            // when

            // then
            assertThrows(PostNotFoundException.class, () -> shopService.updateAll(100L, updateRequest));
        }
    }

    @Nested
    @DisplayName("산타샵 물품 삭제 테스트")
    class DeleteProduct {

        @Test
        @DisplayName("산타샵 물품 삭제시 물품이 삭제되어야 한다.")
        void delete_product() {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateShopRequest shopRequest = createDefaultShop(userId);
            Long savedId = shopService.save(shopRequest);

            // when
            shopService.deleteShop(savedId);

            // then
            assertThrows(PostNotFoundException.class, () -> shopService.findById(savedId));
        }

        @Test
        @DisplayName("존재하지 않는 물품을 삭제시 예외가 발생해야 한다.")
        void delete_product_fail() {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateShopRequest shopRequest = createDefaultShop(userId);
            Long savedId = shopService.save(shopRequest);

            // when

            // then
            assertThrows(PostNotFoundException.class, () -> shopService.deleteShop(100L));
        }
    }
}

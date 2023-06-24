package io.wisoft.capstonedesign.domain.shop.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.wisoft.capstonedesign.domain.shop.application.ShopService;
import io.wisoft.capstonedesign.domain.shop.web.dto.CreateShopRequest;
import io.wisoft.capstonedesign.domain.shop.web.dto.UpdateShopRequest;
import io.wisoft.capstonedesign.domain.user.application.UserService;
import io.wisoft.capstonedesign.domain.user.web.dto.CreateUserRequest;
import io.wisoft.capstonedesign.global.jwt.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static io.wisoft.capstonedesign.setting.data.DefaultShopData.createDefaultShop;
import static io.wisoft.capstonedesign.setting.data.DefaultUserData.createDefaultUser;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ShopControllerTest {

    @Autowired private UserService userService;
    @Autowired private ShopService shopService;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private MockMvc mockMvc;
    @Autowired private JwtTokenProvider jwtTokenProvider;

    @Nested
    @DisplayName("산타샵 물품 생성 테스트")
    class CreateProduct {

        @Test
        @DisplayName("산타샵 물품 생성시 정상적으로 DB에 저장되어야 한다.")
        void create_product() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(userId));

            CreateShopRequest shopRequest = createDefaultShop(userId);
            String json = objectMapper.writeValueAsString(shopRequest);

            // expected
            mockMvc.perform(post("/api/shops/new")
                            .contentType(APPLICATION_JSON)
                            .header("Authorization", "bearer " + accessToken)
                            .content(json))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").exists())
                    .andDo(print());
        }

        @Test
        @DisplayName("회원이 아닌 사용자가 물품 생성시 예외가 발생해야 한다.")
        void create_product_fail1() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateShopRequest shopRequest = createDefaultShop(userId);
            String json = objectMapper.writeValueAsString(shopRequest);

            // expected
            mockMvc.perform(post("/api/shops/new")
                            .contentType(APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isUnauthorized())
                    .andDo(print());
        }

        @Test
        @DisplayName("물품 생성시 모든 필드는 필수다.")
        void create_product_fail2() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(userId));

            CreateShopRequest shopRequest = CreateShopRequest.builder()
                    .title("    ")
                    .price(0)
                    .image(null)
                    .body(null)
                    .userId(userId)
                    .build();

            String json = objectMapper.writeValueAsString(shopRequest);

            // expected
            mockMvc.perform(post("/api/shops/new")
                            .contentType(APPLICATION_JSON)
                            .header("Authorization", "bearer " + accessToken)
                            .content(json))
                    .andExpect(status().isBadRequest())
                    .andDo(print());
        }
    }

    @Nested
    @DisplayName("산타샵 물품 조회 API 테스트")
    class FindProduct {

        @Test
        @DisplayName("산타샵 물품 전체 조회시 전체 물품을 페이징된 목록으로 반환해야 한다.")
        void find_products() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateShopRequest shopRequest1 = createDefaultShop(userId);
            shopService.save(shopRequest1);

            CreateShopRequest shopRequest2 = createDefaultShop(userId);
            shopService.save(shopRequest2);

            // expected
            mockMvc.perform(get("/api/shops?page=0&size=5")
                            .contentType(APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content.length()", is(2)))
                    .andDo(print());
        }

        @Test
        @DisplayName("산타샵 물품 단건 조회 요청시 요청한 물품이 조회되어야 한다.")
        void find_single_product() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateShopRequest shopRequest = createDefaultShop(userId);
            Long shopId = shopService.save(shopRequest);

            // expected
            mockMvc.perform(get("/api/shops/details/{id}", shopId)
                            .contentType(APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.title").value("라면 한 박스"))
                    .andExpect(jsonPath("$.price").value(1000))
                    .andDo(print());
        }

        @Test
        @DisplayName("존재하지 않는 물품을 조회시 예외가 발생해야 한다.")
        void find_single_product_fail() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateShopRequest shopRequest = createDefaultShop(userId);
            shopService.save(shopRequest);

            // expected
            mockMvc.perform(get("/api/shops/details/{id}", 1000L)
                            .contentType(APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andDo(print());
        }
    }

    @Nested
    @DisplayName("산타샵 물품 수정 API 테스트")
    class UpdateProduct {

        @Test
        @DisplayName("산타샵 물품 수정시 물품이 정상적으로 수정되어야 한다.")
        void update_product() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(userId));

            CreateShopRequest shopRequest = createDefaultShop(userId);
            Long savedId = shopService.save(shopRequest);

            UpdateShopRequest updateRequest = UpdateShopRequest.builder()
                    .title("쌀 10kg")
                    .price(2000)
                    .image("rice.jpg")
                    .body("포인트로 든든한 쌀 밥 가져가세요!")
                    .shopId(savedId)
                    .build();

            String json = objectMapper.writeValueAsString(updateRequest);

            // expected
            mockMvc.perform(patch("/api/shops/{id}", savedId)
                            .contentType(APPLICATION_JSON)
                            .header("Authorization", "bearer " + accessToken)
                            .content(json))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.title").value("쌀 10kg"))
                    .andDo(print());
        }

        @Test
        @DisplayName("존재하지 않는 물품을 수정시 예외가 발생해야 한다.")
        void update_product_fail1() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(userId));

            CreateShopRequest shopRequest = createDefaultShop(userId);
            Long savedId = shopService.save(shopRequest);

            UpdateShopRequest updateRequest = UpdateShopRequest.builder()
                    .title("쌀 10kg")
                    .price(2000)
                    .image("rice.jpg")
                    .body("포인트로 든든한 쌀 밥 가져가세요!")
                    .shopId(savedId)
                    .build();

            String json = objectMapper.writeValueAsString(updateRequest);

            // expected
            mockMvc.perform(patch("/api/shops/{id}", 1000L)
                            .contentType(APPLICATION_JSON)
                            .header("Authorization", "bearer " + accessToken)
                            .content(json))
                    .andExpect(status().isNotFound())
                    .andDo(print());
        }

        @Test
        @DisplayName("물품 수정 요청시 모든 필드는 필수다.")
        void update_product_fail2() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(userId));

            CreateShopRequest shopRequest = createDefaultShop(userId);
            Long savedId = shopService.save(shopRequest);

            UpdateShopRequest updateRequest = UpdateShopRequest.builder()
                    .title(null)
                    .price(0)
                    .image("     ")
                    .body("포인트로 든든한 쌀 밥 가져가세요!")
                    .shopId(savedId)
                    .build();

            String json = objectMapper.writeValueAsString(updateRequest);

            // expected
            mockMvc.perform(patch("/api/shops/{id}", savedId)
                            .contentType(APPLICATION_JSON)
                            .header("Authorization", "bearer " + accessToken)
                            .content(json))
                    .andExpect(status().isBadRequest())
                    .andDo(print());
        }

        @Test
        @DisplayName("로그인하지 않은 사용자가 수정 요청시 에외가 발생해야 한다.")
        void update_product_fail3() throws Exception {

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

            String json = objectMapper.writeValueAsString(updateRequest);

            // expected
            mockMvc.perform(patch("/api/shops/{id}", savedId)
                            .contentType(APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isUnauthorized())
                    .andDo(print());
        }
    }

    @Nested
    @DisplayName("산타샵 물품 삭제 API 테스트")
    class DeleteProduct {

        @Test
        @DisplayName("물품 삭제 요청시 정상적으로 삭제되어야 한다.")
        void delete_product() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(userId));

            CreateShopRequest shopRequest = createDefaultShop(userId);
            Long savedId = shopService.save(shopRequest);

            // expected
            mockMvc.perform(delete("/api/shops/{id}", savedId)
                            .contentType(APPLICATION_JSON)
                            .header("Authorization", "bearer " + accessToken))
                    .andExpect(status().isOk())
                    .andDo(print());
        }

        @Test
        @DisplayName("회원이 아닌 사용자가 물품 삭제 요청시 예외가 발생해야 한다.")
        void delete_product_fail1() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateShopRequest shopRequest = createDefaultShop(userId);
            Long savedId = shopService.save(shopRequest);

            // expected
            mockMvc.perform(delete("/api/shops/{id}", savedId)
                            .contentType(APPLICATION_JSON))
                    .andExpect(status().isUnauthorized())
                    .andDo(print());
        }

        @Test
        @DisplayName("존재하지 않는 물품을 삭제 요청시 예외가 발생해야 한다.")
        void delete_product_fail2() throws Exception {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(userId));

            CreateShopRequest shopRequest = createDefaultShop(userId);
            shopService.save(shopRequest);

            // expected
            mockMvc.perform(delete("/api/shops/{id}", 1000L)
                            .contentType(APPLICATION_JSON)
                            .header("Authorization", "bearer " + accessToken))
                    .andExpect(status().isNotFound())
                    .andDo(print());
        }
    }
}
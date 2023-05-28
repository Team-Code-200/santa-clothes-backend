package io.wisoft.capstonedesign.domain.shop.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.wisoft.capstonedesign.domain.shop.application.ShopService;
import io.wisoft.capstonedesign.domain.shop.persistence.Shop;
import io.wisoft.capstonedesign.domain.shop.web.dto.*;
import io.wisoft.capstonedesign.global.annotation.SwaggerApiError;
import io.wisoft.capstonedesign.global.annotation.SwaggerApiNotFoundError;
import io.wisoft.capstonedesign.global.annotation.SwaggerApiSuccess;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "산타샵", description = "산타샵 관리 API")
@RestController
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    @SwaggerApiSuccess(summary = "상품 생성", implementation = CreateShopResponse.class)
    @SwaggerApiError
    @PostMapping("/api/shops/new")
    public CreateShopResponse saveShop(@RequestBody @Valid final CreateShopRequest request) {

        Long id = shopService.save(request);
        return new CreateShopResponse(id);
    }

    @SwaggerApiSuccess(summary = "상품 정보 수정", implementation = UpdateShopResponse.class)
    @SwaggerApiError
    @PatchMapping("/api/shops/{id}")
    public UpdateShopResponse updateShop(
            @PathVariable("id") final Long id,
            @RequestBody @Valid final UpdateShopRequest request) {

        shopService.updateAll(id, request);
        Shop updateShop = shopService.findById(id);
        return new UpdateShopResponse(updateShop);
    }

    @SwaggerApiSuccess(summary = "전체 상품 목록 조회", implementation = Page.class)
    @SwaggerApiNotFoundError
    @GetMapping("/api/shops")
    public Page<ShopListDto> findShops(final Pageable pageable) {
        return shopService.findByCreatedDateDescUsingPaging(pageable).map(ShopListDto::new);
    }

    @SwaggerApiSuccess(summary = "상품 상세 조회", implementation = GetShopResponse.class)
    @SwaggerApiNotFoundError
    @GetMapping("/api/shops/details/{id}")
    public GetShopResponse getShop(@PathVariable("id") final Long id) {

        Shop findShop = shopService.findById(id);
        return new GetShopResponse(findShop);
    }

    @SwaggerApiSuccess(summary = "상품 삭제", implementation = DeleteShopResponse.class)
    @SwaggerApiError
    @DeleteMapping("/api/shops/{id}")
    public DeleteShopResponse deleteShop(@PathVariable("id") final Long id) {

        shopService.deleteShop(id);
        return new DeleteShopResponse(id);
    }
}

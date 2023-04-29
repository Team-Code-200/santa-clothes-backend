package io.wisoft.capstonedesign.domain.shop.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.wisoft.capstonedesign.domain.shop.application.ShopService;
import io.wisoft.capstonedesign.domain.shop.persistence.Shop;
import io.wisoft.capstonedesign.domain.shop.web.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "산타샵", description = "산타샵 관리 API")
@RestController
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    @Operation(summary = "상품 생성")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = CreateShopResponse.class))),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PostMapping("/api/shops/new")
    public CreateShopResponse saveShop(@RequestBody @Valid final CreateShopRequest request) {

        Long id = shopService.save(request);
        return new CreateShopResponse(id);
    }

    @Operation(summary = "상품 정보 수정")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = UpdateShopResponse.class))),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PatchMapping("/api/shops/{id}")
    public UpdateShopResponse updateShop(
            @PathVariable("id") final Long id,
            @RequestBody @Valid final UpdateShopRequest request) {

        shopService.updateAll(id, request);
        Shop updateShop = shopService.findById(id);
        return new UpdateShopResponse(updateShop);
    }

    @Operation(summary = "전체 상품 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/api/shops")
    public Result findShops() {
        List<Shop> findShops = shopService.findShopList();

        List<ShopDto> collect = findShops.stream()
                .map(ShopDto::new)
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @Operation(summary = "상품 상세 조회")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = GetShopResponse.class))),
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/api/shops/details/{id}")
    public GetShopResponse getShop(@PathVariable("id") final Long id) {

        Shop findShop = shopService.findById(id);
        return new GetShopResponse(findShop);
    }

    @Operation(summary = "상품 삭제")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = DeleteShopResponse.class))),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @DeleteMapping("/api/shops/{id}")
    public DeleteShopResponse deleteShop(@PathVariable("id") final Long id) {

        shopService.deleteShop(id);
        return new DeleteShopResponse(id);
    }
}

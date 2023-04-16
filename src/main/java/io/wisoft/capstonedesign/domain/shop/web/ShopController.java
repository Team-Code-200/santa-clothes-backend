package io.wisoft.capstonedesign.domain.shop.web;

import io.wisoft.capstonedesign.domain.shop.application.ShopService;
import io.wisoft.capstonedesign.domain.shop.persistence.Shop;
import io.wisoft.capstonedesign.domain.shop.web.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    @PostMapping("/api/shops/new")
    public CreateShopResponse saveShop(@RequestBody @Valid final CreateShopRequest request) {

        Long id = shopService.save(request);
        return new CreateShopResponse(id);
    }

    @PatchMapping("/api/shops/{id}")
    public UpdateShopResponse updateShop(
            @PathVariable("id") final Long id,
            @RequestBody @Valid final UpdateShopRequest request) {

        shopService.updateAll(id, request);
        Shop updateShop = shopService.findById(id);
        return new UpdateShopResponse(updateShop);
    }

    @GetMapping("/api/shops")
    public Result findShops() {
        List<Shop> findShops = shopService.findShopList();

        List<ShopDto> collect = findShops.stream()
                .map(ShopDto::new)
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @GetMapping("/api/shops/{id}")
    public GetShopResponse getShop(@PathVariable("id") final Long id) {

        Shop findShop = shopService.findById(id);
        return new GetShopResponse(findShop);
    }

    @DeleteMapping("/api/shops/{id}")
    public DeleteShopResponse deleteShop(@PathVariable("id") final Long id) {

        shopService.deleteShop(id);
        return new DeleteShopResponse(id);
    }
}

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
    public CreateShopResponse saveShop(@RequestBody @Valid CreateShopRequest request) {

        Long id = shopService.save(request);
        return new CreateShopResponse(id);
    }

    @PatchMapping("/api/shops/{id}")
    public UpdateShopResponse updateShop(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateShopRequest request) {

        shopService.updateAll(request);
        Shop updateShop = shopService.findOne(id);
        return new UpdateShopResponse(
                id,
                updateShop.getTitle(),
                updateShop.getPrice(),
                updateShop.getImage(),
                updateShop.getBody()
        );
    }

    @GetMapping("/api/shops")
    public Result findShops() {
        List<Shop> findShops = shopService.findShopList();

        List<ShopDto> collect = findShops.stream()
                .map(s -> new ShopDto(s.getTitle(), s.getPrice(), s.getImage(), s.getBody()))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @DeleteMapping("/api/shops/{id}")
    public DeleteShopResponse deleteShop(@PathVariable("id") Long id) {

        shopService.deleteShop(id);
        return new DeleteShopResponse(id);
    }
}

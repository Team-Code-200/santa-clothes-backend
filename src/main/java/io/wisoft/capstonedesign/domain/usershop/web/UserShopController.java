package io.wisoft.capstonedesign.domain.usershop.web;

import io.wisoft.capstonedesign.domain.usershop.application.UserShopService;
import io.wisoft.capstonedesign.domain.usershop.persistence.UserShop;
import io.wisoft.capstonedesign.domain.usershop.web.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserShopController {

    private final UserShopService userShopService;

    @PostMapping("/api/shop-orders/new")
    public CreateOrderResponse saveOrder(@RequestBody @Valid final CreateOrderRequest request) {

        Long id = userShopService.save(request);
        return new CreateOrderResponse(id);
    }

    @PatchMapping("/api/shop-orders/{id}")
    public UpdateOrderResponse updateOrder(
            @PathVariable("id") final Long id,
            @RequestBody @Valid final UpdateOrderRequest request) {

        userShopService.updateBody(request);
        UserShop updateOrder = userShopService.findById(id);
        return new UpdateOrderResponse(
                id,
                updateOrder.getText()
        );
    }

    @GetMapping("/api/shop-orders")
    public Result findOrders() {
        List<UserShop> findOrders = userShopService.findShopOrders();

        List<OrderDto> collect = findOrders.stream()
                .map(o -> new OrderDto(o.getText(), o.getUser().getNickname(), o.getInformation().getPhoneNumber(), o.getShop().getTitle()))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @DeleteMapping("/api/shop-orders/{id}")
    public DeleteOrderResponse deleteOrder(@PathVariable("id") final Long id) {

        userShopService.deleteOrder(id);
        return new DeleteOrderResponse(id);
    }
}

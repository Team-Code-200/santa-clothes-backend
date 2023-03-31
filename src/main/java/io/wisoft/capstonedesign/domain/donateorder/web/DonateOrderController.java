package io.wisoft.capstonedesign.domain.donateorder.web;

import io.wisoft.capstonedesign.domain.donateorder.application.DonateOrderService;
import io.wisoft.capstonedesign.domain.donateorder.persistence.DonateOrder;
import io.wisoft.capstonedesign.domain.donateorder.web.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class DonateOrderController {

    private final DonateOrderService donateOrderService;

    @PostMapping("/api/donate-orders/new")
    public CreateOrderResponse saveOrder(@RequestBody @Valid CreateOrderRequest request) {

        Long id = donateOrderService.save(request);
        return new CreateOrderResponse(id);
    }

    @PatchMapping("/api/donate-orders/{id}")
    public UpdateOrderResponse updateOrder(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateOrderRequest request) {

        donateOrderService.updateBody(request);
        DonateOrder updateOrder = donateOrderService.findOne(id);
        return new UpdateOrderResponse(
                id,
                updateOrder.getText()
        );
    }

    @GetMapping("/api/donate-orders")
    public Result findOrders() {
        List<DonateOrder> findOrders = donateOrderService.findDonateOrders();

        List<OrderDto> collect = findOrders.stream()
                .map(o -> new OrderDto(o.getText(), o.getUser().getNickname(), o.getInformation().getPhoneNumber(), o.getDonate().getTitle()))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @DeleteMapping("/api/donate-orders/{id}")
    public DeleteOrderResponse deleteOrder(@PathVariable("id") Long id) {

        donateOrderService.deleteOrder(id);
        return new DeleteOrderResponse(id);
    }
}
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
    public CreateOrderResponse saveOrder(@RequestBody @Valid final CreateOrderRequest request) {

        Long id = donateOrderService.save(request);
        return new CreateOrderResponse(id);
    }

    @PatchMapping("/api/donate-orders/{id}")
    public UpdateOrderResponse updateOrder(
            @PathVariable("id") final Long id,
            @RequestBody @Valid final UpdateOrderRequest request) {

        donateOrderService.updateBody(id, request);
        DonateOrder updateOrder = donateOrderService.findById(id);
        return new UpdateOrderResponse(updateOrder);
    }

    @GetMapping("/api/donate-orders")
    public Result findOrders() {
        List<DonateOrder> findOrders = donateOrderService.findByCreatedDateDESC();

        List<OrderDto> collect = findOrders.stream()
                .map(OrderDto::new)
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @GetMapping("/api/donate-orders/{id}")
    public GetDonateOrderDto getOrder(@PathVariable("id") final Long id) {

        DonateOrder findOrder = donateOrderService.findById(id);
        return new GetDonateOrderDto(findOrder);
    }

    @GetMapping("/api/donate-orders/user/{id}")
    public Result getOrderByUser(@PathVariable("id") final Long userId) {

        List<DonateOrder> byUser = donateOrderService.findByUser(userId);

        List<GetDonateOrderDto> collect = byUser.stream()
                .map(GetDonateOrderDto::new)
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @DeleteMapping("/api/donate-orders/{id}")
    public DeleteOrderResponse deleteOrder(@PathVariable("id") final Long id) {

        donateOrderService.deleteOrder(id);
        return new DeleteOrderResponse(id);
    }
}

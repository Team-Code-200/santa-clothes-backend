package io.wisoft.capstonedesign.domain.findorder.web;

import io.wisoft.capstonedesign.domain.findorder.application.FindOrderService;
import io.wisoft.capstonedesign.domain.findorder.persistence.FindOrder;
import io.wisoft.capstonedesign.domain.findorder.web.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class FindOrderController {

    private final FindOrderService findOrderService;

    @PostMapping("/api/find-orders/new")
    public CreateOrderResponse saveOrder(@RequestBody @Valid final CreateOrderRequest request) {

        Long id = findOrderService.save(request);
        return new CreateOrderResponse(id);
    }

    @PatchMapping("/api/find-orders/{id}")
    public UpdateOrderResponse updateOrder(
            @PathVariable("id") final Long id,
            @RequestBody @Valid final UpdateOrderRequest request) {

        findOrderService.updateBody(request);
        FindOrder updateOrder = findOrderService.findById(id);
        return new UpdateOrderResponse(updateOrder);
    }

    @GetMapping("/api/find-orders")
    public Result findOrders() {
        List<FindOrder> findOrders = findOrderService.findFindOrders();

        List<OrderDto> collect = findOrders.stream()
                .map(OrderDto::new)
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @DeleteMapping("/api/find-orders/{id}")
    public DeleteOrderResponse deleteOrder(@PathVariable("id") final Long id) {

        findOrderService.deleteOrder(id);
        return new DeleteOrderResponse(id);
    }
}

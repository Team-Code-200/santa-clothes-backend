package io.wisoft.capstonedesign.domain.donateorder.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.wisoft.capstonedesign.domain.donateorder.application.DonateOrderService;
import io.wisoft.capstonedesign.domain.donateorder.persistence.DonateOrder;
import io.wisoft.capstonedesign.domain.donateorder.web.dto.*;
import io.wisoft.capstonedesign.global.annotation.SwaggerApiError;
import io.wisoft.capstonedesign.global.annotation.SwaggerApiSuccess;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "나눠줄래요 게시판 주문 내역", description = "게시판 주문 관리 API")
@RestController
@RequiredArgsConstructor
public class DonateOrderController {

    private final DonateOrderService donateOrderService;

    @SwaggerApiSuccess(summary = "주문 생성", implementation = CreateOrderResponse.class)
    @SwaggerApiError
    @PostMapping("/api/donate-orders/new")
    public CreateOrderResponse saveOrder(@RequestBody @Valid final CreateOrderRequest request) {

        Long id = donateOrderService.save(request);
        return new CreateOrderResponse(id);
    }

    @SwaggerApiSuccess(summary = "주문 요청사항 수정", implementation = UpdateOrderResponse.class)
    @SwaggerApiError
    @PatchMapping("/api/donate-orders/{id}")
    public UpdateOrderResponse updateOrder(
            @PathVariable("id") final Long id,
            @RequestBody @Valid final UpdateOrderRequest request) {

        donateOrderService.updateBody(id, request);
        DonateOrder updateOrder = donateOrderService.findById(id);
        return new UpdateOrderResponse(updateOrder);
    }

    @SwaggerApiSuccess(summary = "전체 사용자의 주문 내역 목록 조회", implementation = Result.class)
    @SwaggerApiError
    @GetMapping("/api/donate-orders")
    public Result findOrders() {
        List<DonateOrder> findOrders = donateOrderService.findByCreatedDateDESC();

        List<OrderDto> collect = findOrders.stream()
                .map(OrderDto::new)
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @SwaggerApiSuccess(summary = "주문 내역 상세 조회", implementation = GetDonateOrderDto.class)
    @SwaggerApiError
    @GetMapping("/api/donate-orders/{id}")
    public GetDonateOrderDto getOrder(@PathVariable("id") final Long id) {

        DonateOrder findOrder = donateOrderService.findById(id);
        return new GetDonateOrderDto(findOrder);
    }

    @SwaggerApiSuccess(summary = "특정 사용자의 주문 내역 조회", implementation = Result.class)
    @SwaggerApiError
    @GetMapping("/api/donate-orders/user/{id}")
    public Result getOrderByUser(@PathVariable("id") final Long userId) {

        List<DonateOrder> byUser = donateOrderService.findByUser(userId);

        List<GetDonateOrderDto> collect = byUser.stream()
                .map(GetDonateOrderDto::new)
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @SwaggerApiSuccess(summary = "주문 취소", implementation = DeleteOrderResponse.class)
    @SwaggerApiError
    @DeleteMapping("/api/donate-orders/{id}")
    public DeleteOrderResponse deleteOrder(@PathVariable("id") final Long id) {

        donateOrderService.deleteOrder(id);
        return new DeleteOrderResponse(id);
    }
}

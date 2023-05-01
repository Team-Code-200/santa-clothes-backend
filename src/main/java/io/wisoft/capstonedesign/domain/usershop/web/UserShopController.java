package io.wisoft.capstonedesign.domain.usershop.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.wisoft.capstonedesign.domain.usershop.application.UserShopService;
import io.wisoft.capstonedesign.domain.usershop.persistence.UserShop;
import io.wisoft.capstonedesign.domain.usershop.web.dto.*;
import io.wisoft.capstonedesign.global.annotation.SwaggerApiError;
import io.wisoft.capstonedesign.global.annotation.SwaggerApiSuccess;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "산타샵 주문 내역", description = "산타샵 주문 관리 API")
@RestController
@RequiredArgsConstructor
public class UserShopController {

    private final UserShopService userShopService;

    @SwaggerApiSuccess(summary = "주문 생성", implementation = CreateOrderResponse.class)
    @SwaggerApiError
    @PostMapping("/api/shop-orders/new")
    public CreateOrderResponse saveOrder(@RequestBody @Valid final CreateOrderRequest request) {

        Long id = userShopService.save(request);
        return new CreateOrderResponse(id);
    }

    @SwaggerApiSuccess(summary = "주문 요청사항 수정", implementation = UpdateOrderResponse.class)
    @SwaggerApiError
    @PatchMapping("/api/shop-orders/{id}")
    public UpdateOrderResponse updateOrder(
            @PathVariable("id") final Long id,
            @RequestBody @Valid final UpdateOrderRequest request) {

        userShopService.updateBody(id, request);
        UserShop updateOrder = userShopService.findById(id);
        return new UpdateOrderResponse(updateOrder);
    }

    @SwaggerApiSuccess(summary = "전체 사용자의 주문내역 목록 조회", implementation = Result.class)
    @SwaggerApiError
    @GetMapping("/api/shop-orders")
    public Result findOrders() {
        List<UserShop> findOrders = userShopService.findByCreatedDateDESC();

        List<OrderDto> collect = findOrders.stream()
                .map(OrderDto::new)
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @SwaggerApiSuccess(summary = "주문 내역 상세 조회", implementation = GetShopOrderDto.class)
    @SwaggerApiError
    @GetMapping("/api/shop-orders/{id}")
    public GetShopOrderDto getOrder(@PathVariable("id") final Long id) {

        UserShop findOrder = userShopService.findById(id);
        return new GetShopOrderDto(findOrder);
    }

    @SwaggerApiSuccess(summary = "특정 사용자의 주문 내역 조회", implementation = Result.class)
    @SwaggerApiError
    @GetMapping("/api/shop-orders/user/{id}")
    public Result getOrderByUser(@PathVariable("id") final Long userId) {

        List<UserShop> byUser = userShopService.findByUser(userId);

        List<GetShopOrderDto> collect = byUser.stream()
                .map(GetShopOrderDto::new)
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @SwaggerApiSuccess(summary = "주문 취소", implementation = DeleteOrderResponse.class)
    @SwaggerApiError
    @DeleteMapping("/api/shop-orders/{id}")
    public DeleteOrderResponse deleteOrder(@PathVariable("id") final Long id) {

        userShopService.deleteOrder(id);
        return new DeleteOrderResponse(id);
    }
}

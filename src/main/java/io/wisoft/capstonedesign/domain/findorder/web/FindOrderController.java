package io.wisoft.capstonedesign.domain.findorder.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.wisoft.capstonedesign.domain.findorder.application.FindOrderService;
import io.wisoft.capstonedesign.domain.findorder.persistence.FindOrder;
import io.wisoft.capstonedesign.domain.findorder.web.dto.*;
import io.wisoft.capstonedesign.global.annotation.SwaggerApiError;
import io.wisoft.capstonedesign.global.annotation.SwaggerApiSuccess;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "찾아볼래요 게시판 주문 내역", description = "게시판 주문 관리 API")
@RestController
@RequiredArgsConstructor
public class FindOrderController {

    private final FindOrderService findOrderService;

    @SwaggerApiSuccess(summary = "주문 생성", implementation = CreateOrderResponse.class)
    @SwaggerApiError
    @PostMapping("/api/find-orders/new")
    public CreateOrderResponse saveOrder(@RequestBody @Valid final CreateOrderRequest request) {

        Long id = findOrderService.save(request);
        return new CreateOrderResponse(id);
    }

    @SwaggerApiSuccess(summary = "주문 요청사항 수정", implementation = UpdateOrderResponse.class)
    @SwaggerApiError
    @PatchMapping("/api/find-orders/{id}")
    public UpdateOrderResponse updateOrder(
            @PathVariable("id") final Long id,
            @RequestBody @Valid final UpdateOrderRequest request) {

        findOrderService.updateBody(id, request);
        FindOrder updateOrder = findOrderService.findById(id);
        return new UpdateOrderResponse(updateOrder);
    }

    @SwaggerApiSuccess(summary = "전체 사용자의 주문 내역 목록 조회", implementation = Result.class)
    @SwaggerApiError
    @GetMapping("/api/find-orders")
    public Result findOrders() {
        List<FindOrder> findOrders = findOrderService.findByCreatedDateDESC();

        List<OrderDto> collect = findOrders.stream()
                .map(OrderDto::new)
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @SwaggerApiSuccess(summary = "주문 내역 상세 조회", implementation = GetFindOrderDto.class)
    @SwaggerApiError
    @GetMapping("/api/find-orders/{id}")
    public GetFindOrderDto getOrder(@PathVariable("id") final Long id) {

        FindOrder findOrder = findOrderService.findById(id);
        return new GetFindOrderDto(findOrder);
    }

    @SwaggerApiSuccess(summary = "특정 사용자의 주문 내역 조회", implementation = Result.class)
    @SwaggerApiError
    @GetMapping("/api/find-orders/user/{id}")
    public Result getOrderByUser(@PathVariable("id") final Long userId) {

        List<FindOrder> byUser = findOrderService.findByUser(userId);

        List<GetFindOrderDto> collect = byUser.stream()
                .map(GetFindOrderDto::new)
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @SwaggerApiSuccess(summary = "주문 취소", implementation = DeleteOrderResponse.class)
    @SwaggerApiError
    @DeleteMapping("/api/find-orders/{id}")
    public DeleteOrderResponse deleteOrder(@PathVariable("id") final Long id) {

        findOrderService.deleteOrder(id);
        return new DeleteOrderResponse(id);
    }
}

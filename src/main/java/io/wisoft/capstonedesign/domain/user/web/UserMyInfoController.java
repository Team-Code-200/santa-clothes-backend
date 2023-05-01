package io.wisoft.capstonedesign.domain.user.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.wisoft.capstonedesign.domain.donate.web.dto.GetDonateResponse;
import io.wisoft.capstonedesign.domain.donateorder.web.dto.GetDonateOrderDto;
import io.wisoft.capstonedesign.domain.find.web.dto.GetFindResponse;
import io.wisoft.capstonedesign.domain.findorder.web.dto.GetFindOrderDto;
import io.wisoft.capstonedesign.domain.information.web.dto.GetInfoResponse;
import io.wisoft.capstonedesign.domain.user.application.UserMyInfoService;
import io.wisoft.capstonedesign.domain.usershop.web.dto.GetShopOrderDto;
import io.wisoft.capstonedesign.global.annotation.SwaggerApiError;
import io.wisoft.capstonedesign.global.annotation.SwaggerApiSuccess;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "내 정보", description = "마이 페이지 API")
@RestController
@RequiredArgsConstructor
public class UserMyInfoController {

    private final UserMyInfoService userMyInfoService;

    @SwaggerApiSuccess(summary = "마이 페이지 - 게시글 조회", implementation = Page.class)
    @SwaggerApiError
    @GetMapping("/api/users/{id}/myinfo/donates")
    public Page<GetDonateResponse> donateListByUserId(
            @PathVariable("id") final Long userId, final Pageable pageable) {

        return userMyInfoService.findDonatesByIdUsingPaging(userId, pageable)
                .map(GetDonateResponse::new);
    }

    @SwaggerApiSuccess(summary = "마이 페이지 - 게시글 조회", implementation = Page.class)
    @SwaggerApiError
    @GetMapping("/api/users/{id}/myinfo/finds")
    public Page<GetFindResponse> findListByUserId(
            @PathVariable("id") final Long userId, final Pageable pageable) {

        return userMyInfoService.findFindsByIdUsingPaging(userId, pageable)
                .map(GetFindResponse::new);
    }

    @SwaggerApiSuccess(summary = "마이 페이지 - 배송 정보 조회", implementation = Page.class)
    @SwaggerApiError
    @GetMapping("/api/users/{id}/myinfo/infos")
    public Page<GetInfoResponse> infoListByUserId(
            @PathVariable("id") final Long userId, final Pageable pageable) {

        return userMyInfoService.findInfosByIdUsingPaging(userId, pageable)
                .map(GetInfoResponse::new);
    }

    @SwaggerApiSuccess(summary = "마이 페이지 - 거래 내역 조회", implementation = Page.class)
    @SwaggerApiError
    @GetMapping("/api/users/{id}/myinfo/donate-orders")
    public Page<GetDonateOrderDto> donateOrderListByUserId(
            @PathVariable("id") final Long userId, final Pageable pageable) {

        return userMyInfoService.findDonateOrdersByIdUsingPaging(userId, pageable)
                .map(GetDonateOrderDto::new);
    }

    @SwaggerApiSuccess(summary = "마이 페이지 - 거래 내역 조회", implementation = Page.class)
    @SwaggerApiError
    @GetMapping("/api/users/{id}/myinfo/find-orders")
    public Page<GetFindOrderDto> findOrderListByUserId(
            @PathVariable("id") final Long userId, final Pageable pageable) {

        return userMyInfoService.findFindOrdersByIdUsingPaging(userId, pageable)
                .map(GetFindOrderDto::new);
    }

    @SwaggerApiSuccess(summary = "마이 페이지 - 주문 내역 조회", implementation = Page.class)
    @SwaggerApiError
    @GetMapping("/api/users/{id}/myinfo/shop-orders")
    public Page<GetShopOrderDto> shopOrderListByUserId(
            @PathVariable("id") final Long userId, final Pageable pageable) {

        return userMyInfoService.findShopOrdersByIdUsingPaging(userId, pageable)
                .map(GetShopOrderDto::new);
    }
}

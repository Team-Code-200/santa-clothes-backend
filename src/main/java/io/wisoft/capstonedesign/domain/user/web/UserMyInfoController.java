package io.wisoft.capstonedesign.domain.user.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.wisoft.capstonedesign.domain.donate.web.dto.GetDonateResponse;
import io.wisoft.capstonedesign.domain.donateorder.web.dto.GetDonateOrderDto;
import io.wisoft.capstonedesign.domain.find.web.dto.GetFindResponse;
import io.wisoft.capstonedesign.domain.findorder.web.dto.GetFindOrderDto;
import io.wisoft.capstonedesign.domain.information.web.dto.GetInfoResponse;
import io.wisoft.capstonedesign.domain.user.application.UserMyInfoService;
import io.wisoft.capstonedesign.domain.usershop.web.dto.GetShopOrderDto;
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

    @Operation(summary = "마이 페이지 - 게시글 조회")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = GetDonateResponse.class))),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/api/users/{id}/myinfo/donates")
    public Page<GetDonateResponse> donateListByUserId(
            @PathVariable("id") final Long userId, final Pageable pageable) {

        return userMyInfoService.findDonatesByIdUsingPaging(userId, pageable)
                .map(GetDonateResponse::new);
    }

    @Operation(summary = "마이 페이지 - 게시글 조회")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = GetFindResponse.class))),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/api/users/{id}/myinfo/finds")
    public Page<GetFindResponse> findListByUserId(
            @PathVariable("id") final Long userId, final Pageable pageable) {

        return userMyInfoService.findFindsByIdUsingPaging(userId, pageable)
                .map(GetFindResponse::new);
    }

    @Operation(summary = "마이 페이지 - 배송 정보 조회")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = GetInfoResponse.class))),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/api/users/{id}/myinfo/infos")
    public Page<GetInfoResponse> infoListByUserId(
            @PathVariable("id") final Long userId, final Pageable pageable) {

        return userMyInfoService.findInfosByIdUsingPaging(userId, pageable)
                .map(GetInfoResponse::new);
    }

    @Operation(summary = "마이 페이지 - 거래 내역 조회")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = GetDonateOrderDto.class))),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/api/users/{id}/myinfo/donate-orders")
    public Page<GetDonateOrderDto> donateOrderListByUserId(
            @PathVariable("id") final Long userId, final Pageable pageable) {

        return userMyInfoService.findDonateOrdersByIdUsingPaging(userId, pageable)
                .map(GetDonateOrderDto::new);
    }

    @Operation(summary = "마이 페이지 - 거래 내역 조회")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = GetFindOrderDto.class))),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/api/users/{id}/myinfo/find-orders")
    public Page<GetFindOrderDto> findOrderListByUserId(
            @PathVariable("id") final Long userId, final Pageable pageable) {

        return userMyInfoService.findFindOrdersByIdUsingPaging(userId, pageable)
                .map(GetFindOrderDto::new);
    }

    @Operation(summary = "마이 페이지 - 주문 내역 조회")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = GetShopOrderDto.class))),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/api/users/{id}/myinfo/shop-orders")
    public Page<GetShopOrderDto> shopOrderListByUserId(
            @PathVariable("id") final Long userId, final Pageable pageable) {

        return userMyInfoService.findShopOrdersByIdUsingPaging(userId, pageable)
                .map(GetShopOrderDto::new);
    }
}

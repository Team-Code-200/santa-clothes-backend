package io.wisoft.capstonedesign.domain.information.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.wisoft.capstonedesign.domain.information.application.InformationService;
import io.wisoft.capstonedesign.domain.information.persistence.Information;
import io.wisoft.capstonedesign.domain.information.web.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "배송 정보", description = "배송 정보 관리 API")
@RestController
@RequiredArgsConstructor
public class InformationController {

    private final InformationService informationService;

    @Operation(summary = "배송 정보 생성")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = CreateInformationResponse.class))),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PostMapping("/api/informations/new")
    public CreateInformationResponse saveInfo(@RequestBody @Valid final CreateInformationRequest request) {

        Long id = informationService.save(request);
        return new CreateInformationResponse(id);
    }

    @Operation(summary = "배송 정보 수정")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = UpdateInformationResponse.class))),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PatchMapping("/api/informations/{id}")
    public UpdateInformationResponse updateInfo(
            @PathVariable("id") final Long id,
            @RequestBody @Valid final UpdateInformationRequest request) {

        informationService.updateAll(id, request);
        Information updateInfo = informationService.findById(id);
        return new UpdateInformationResponse(updateInfo);
    }

    @Operation(summary = "전체 사용자의 배송 정보 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/api/informations")
    public Result findInformations() {
        List<Information> findInfos = informationService.findInformations();

        List<InformationDto> collect = findInfos.stream()
                .map(InformationDto::new)
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @Operation(summary = "배송 정보 상세 조회")
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
    @GetMapping("/api/informations/{id}")
    public GetInfoResponse getInfo(@PathVariable("id") final Long id) {

        Information findInfo = informationService.findById(id);
        return new GetInfoResponse(findInfo);
    }

    @Operation(summary = "특정 사용자의 배송 정보 조회")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/api/informations/user/{id}")
    public Result getInfoByUser(@PathVariable("id") final Long userId) {

        List<Information> byUser = informationService.findByUser(userId);

        List<GetInfoResponse> collect = byUser.stream()
                .map(GetInfoResponse::new)
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @Operation(summary = "배송 정보 삭제")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = DeleteInformationResponse.class))),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @DeleteMapping("/api/informations/{id}")
    public DeleteInformationResponse deleteInfo(@PathVariable("id") final Long id) {

        informationService.deleteInformation(id);
        return new DeleteInformationResponse(id);
    }
}

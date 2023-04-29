package io.wisoft.capstonedesign.domain.donate.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.wisoft.capstonedesign.domain.donate.application.DonateService;
import io.wisoft.capstonedesign.domain.donate.persistence.Donate;
import io.wisoft.capstonedesign.domain.donate.web.dto.*;
import io.wisoft.capstonedesign.global.enumerated.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@io.swagger.v3.oas.annotations.tags.Tag(name = "나눠줄래요 게시판", description = "게시판 관리 API")
@RestController
@RequiredArgsConstructor
public class DonateController {

    private final DonateService donateService;

    @Operation(summary = "게시글 작성")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = CreateDonateResponse.class))),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PostMapping("/api/donates/new")
    public CreateDonateResponse saveDonate(@RequestBody @Valid final CreateDonateRequest request) {

        Long id = donateService.join(request);
        return new CreateDonateResponse(id);
    }

    @Operation(summary = "게시글 수정")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = UpdateDonateResponse.class))),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PatchMapping("/api/donates/{id}")
    public UpdateDonateResponse updateDonate(
            @PathVariable("id") final Long id,
            @RequestBody @Valid final UpdateDonateRequest request) {

        donateService.updateAll(id, request);
        Donate updateDonate = donateService.findById(id);
        return new UpdateDonateResponse(updateDonate);
    }

    @Operation(summary = "전체 게시글 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/api/donates")
    public Result findDonates() {
        List<Donate> findDonates = donateService.findByCreatedDateDESC();

        List<DonateDto> collect = findDonates.stream()
                .map(DonateDto::new)
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @Operation(summary = "게시글 상세 조회")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = GetDonateResponse.class))),
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/api/donates/details/{id}")
    public GetDonateResponse getDonate(@PathVariable("id") final Long id) {

        Donate findDonate = donateService.findById(id);
        return new GetDonateResponse(findDonate);
    }

    @Operation(summary = "특정 작성자의 게시글 조회")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/api/donates/author/{id}")
    public Result getDonatesByUser(@PathVariable("id") final Long userId) {

        List<Donate> byUser = donateService.findByUser(userId);

        List<GetDonateResponse> collect = byUser.stream()
                .map(GetDonateResponse::new)
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @Operation(summary = "게시글 상의 태그로 조회")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/api/donates/top")
    public Result getDonatesByTop() {
        List<Donate> byTag = donateService.findByTag(Tag.TOP);

        List<DonateDto> collect = byTag.stream()
                .map(DonateDto::new)
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @Operation(summary = "게시글 하의 태그로 조회")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/api/donates/pants")
    public Result getDonatesByPants() {
        List<Donate> byTag = donateService.findByTag(Tag.PANTS);

        List<DonateDto> collect = byTag.stream()
                .map(DonateDto::new)
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @Operation(summary = "게시글 신발 태그로 조회")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/api/donates/shoes")
    public Result getDonatesByShoes() {
        List<Donate> byTag = donateService.findByTag(Tag.SHOES);

        List<DonateDto> collect = byTag.stream()
                .map(DonateDto::new)
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @Operation(summary = "게시글 기타 태그로 조회")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/api/donates/etc")
    public Result getDonatesByEtc() {
        List<Donate> byTag = donateService.findByTag(Tag.ETC);

        List<DonateDto> collect = byTag.stream()
                .map(DonateDto::new)
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @Operation(summary = "게시글 삭제")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = DeleteDonateResponse.class))),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @DeleteMapping("/api/donates/{id}")
    public DeleteDonateResponse deleteDonate(@PathVariable("id") final Long id) {

        donateService.deleteDonate(id);
        return new DeleteDonateResponse(id);
    }
}

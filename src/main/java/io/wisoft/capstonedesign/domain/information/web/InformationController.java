package io.wisoft.capstonedesign.domain.information.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.wisoft.capstonedesign.domain.information.application.InformationService;
import io.wisoft.capstonedesign.domain.information.persistence.Information;
import io.wisoft.capstonedesign.domain.information.web.dto.*;
import io.wisoft.capstonedesign.global.annotation.SwaggerApiError;
import io.wisoft.capstonedesign.global.annotation.SwaggerApiSuccess;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "배송 정보", description = "배송 정보 관리 API")
@RestController
@RequiredArgsConstructor
public class InformationController {

    private final InformationService informationService;

    @SwaggerApiSuccess(summary = "배송 정보 생성", implementation = CreateInformationResponse.class)
    @SwaggerApiError
    @PostMapping("/api/informations/new")
    public CreateInformationResponse saveInfo(@RequestBody @Valid final CreateInformationRequest request) {

        Long id = informationService.save(request);
        return new CreateInformationResponse(id);
    }

    @SwaggerApiSuccess(summary = "배송 정보 수정", implementation = UpdateInformationResponse.class)
    @SwaggerApiError
    @PatchMapping("/api/informations/{id}")
    public UpdateInformationResponse updateInfo(
            @PathVariable("id") final Long id,
            @RequestBody @Valid final UpdateInformationRequest request) {

        informationService.updateAll(id, request);
        Information updateInfo = informationService.findById(id);
        return new UpdateInformationResponse(updateInfo);
    }

    @SwaggerApiSuccess(summary = "전체 사용자의 배송 정보 목록 조회", implementation = Result.class)
    @SwaggerApiError
    @GetMapping("/api/informations")
    public Page<InfoListDto> findInformations(final Pageable pageable) {
        return informationService.findByCreatedDateDescUsingPaging(pageable).map(InfoListDto::new);
    }

    @SwaggerApiSuccess(summary = "배송 정보 상세 조회", implementation = GetInfoResponse.class)
    @SwaggerApiError
    @GetMapping("/api/informations/{id}")
    public GetInfoResponse getInfo(@PathVariable("id") final Long id) {

        Information findInfo = informationService.findById(id);
        return new GetInfoResponse(findInfo);
    }

    @SwaggerApiSuccess(summary = "특정 사용자의 배송 정보 조회", implementation = Result.class)
    @SwaggerApiError
    @GetMapping("/api/informations/user/{id}")
    public Result getInfoByUser(@PathVariable("id") final Long userId) {

        List<Information> byUser = informationService.findByUser(userId);

        List<GetInfoResponse> collect = byUser.stream()
                .map(GetInfoResponse::new)
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @SwaggerApiSuccess(summary = "배송 정보 삭제", implementation = DeleteInformationResponse.class)
    @SwaggerApiError
    @DeleteMapping("/api/informations/{id}")
    public DeleteInformationResponse deleteInfo(@PathVariable("id") final Long id) {

        informationService.deleteInformation(id);
        return new DeleteInformationResponse(id);
    }
}

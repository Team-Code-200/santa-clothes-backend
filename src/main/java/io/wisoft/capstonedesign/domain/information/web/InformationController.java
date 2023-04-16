package io.wisoft.capstonedesign.domain.information.web;

import io.wisoft.capstonedesign.domain.information.application.InformationService;
import io.wisoft.capstonedesign.domain.information.persistence.Information;
import io.wisoft.capstonedesign.domain.information.web.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class InformationController {

    private final InformationService informationService;

    @PostMapping("/api/informations/new")
    public CreateInformationResponse saveInfo(@RequestBody @Valid final CreateInformationRequest request) {

        Long id = informationService.save(request);
        return new CreateInformationResponse(id);
    }

    @PatchMapping("/api/informations/{id}")
    public UpdateInformationResponse updateInfo(
            @PathVariable("id") final Long id,
            @RequestBody @Valid final UpdateInformationRequest request) {

        informationService.updateAll(id, request);
        Information updateInfo = informationService.findById(id);
        return new UpdateInformationResponse(updateInfo);
    }

    @GetMapping("/api/informations")
    public Result findInformations() {
        List<Information> findInfos = informationService.findInformations();

        List<InformationDto> collect = findInfos.stream()
                .map(InformationDto::new)
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @GetMapping("/api/informations/{id}")
    public GetInfoResponse getInfo(@PathVariable("id") final Long id) {

        Information findInfo = informationService.findById(id);
        return new GetInfoResponse(findInfo);
    }

    @GetMapping("/api/informations/user/{id}")
    public Result getInfoByUser(@PathVariable("id") final Long userId) {

        List<Information> byUser = informationService.findByUser(userId);

        List<GetInfoResponse> collect = byUser.stream()
                .map(GetInfoResponse::new)
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @DeleteMapping("/api/informations/{id}")
    public DeleteInformationResponse deleteInfo(@PathVariable("id") final Long id) {

        informationService.deleteInformation(id);
        return new DeleteInformationResponse(id);
    }
}

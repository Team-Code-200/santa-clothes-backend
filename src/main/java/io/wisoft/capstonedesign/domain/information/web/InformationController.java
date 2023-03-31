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

        informationService.updateAll(request);
        Information updateInfo = informationService.findOne(id);
        return new UpdateInformationResponse(
                id,
                updateInfo.getUsername(),
                updateInfo.getAddress(),
                updateInfo.getPhoneNumber()
        );
    }

    @GetMapping("/api/informations")
    public Result findInformations() {
        List<Information> findInfos = informationService.findInformations();

        List<InformationDto> collect = findInfos.stream()
                .map(i -> new InformationDto(i.getUsername(), i.getAddress(), i.getPhoneNumber()))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @DeleteMapping("/api/informations/{id}")
    public DeleteInformationResponse deleteInfo(@PathVariable("id") final Long id) {

        informationService.deleteInformation(id);
        return new DeleteInformationResponse(id);
    }
}

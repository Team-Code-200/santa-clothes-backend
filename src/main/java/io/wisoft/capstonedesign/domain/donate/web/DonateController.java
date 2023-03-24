package io.wisoft.capstonedesign.domain.donate.web;

import io.wisoft.capstonedesign.domain.donate.application.DonateService;
import io.wisoft.capstonedesign.domain.donate.persistence.Donate;
import io.wisoft.capstonedesign.domain.donate.web.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class DonateController {

    private final DonateService donateService;

    @PostMapping("/api/donates/new")
    public CreateDonateResponse saveDonate(@RequestBody @Valid CreateDonateRequest request) {

        Long id = donateService.join(request);
        return new CreateDonateResponse(id);
    }

    @PatchMapping("/api/donates/{id}")
    public UpdateDonateResponse updateDonate(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateDonateRequest request) {

        donateService.updateAll(request);
        Donate updateDonate = donateService.findOne(id);
        return new UpdateDonateResponse(
                id,
                updateDonate.getTitle(),
                updateDonate.getImage(),
                updateDonate.getText(),
                updateDonate.getView(),
                String.valueOf(updateDonate.getTag())
        );
    }

    @GetMapping("/api/donates")
    public Result findDonates() {
        List<Donate> findDonates = donateService.findDonates();

        List<DonateDto> collect = findDonates.stream()
                .map(d -> new DonateDto(d.getTitle(), String.valueOf(d.getTag())))
                .collect(Collectors.toList());

        return new Result(collect);
    }
}

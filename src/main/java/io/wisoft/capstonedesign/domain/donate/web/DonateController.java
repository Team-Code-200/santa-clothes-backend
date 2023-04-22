package io.wisoft.capstonedesign.domain.donate.web;

import io.wisoft.capstonedesign.domain.donate.application.DonateService;
import io.wisoft.capstonedesign.domain.donate.persistence.Donate;
import io.wisoft.capstonedesign.domain.donate.web.dto.*;
import io.wisoft.capstonedesign.global.enumerated.Tag;
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
    public CreateDonateResponse saveDonate(@RequestBody @Valid final CreateDonateRequest request) {

        Long id = donateService.join(request);
        return new CreateDonateResponse(id);
    }

    @PatchMapping("/api/donates/{id}")
    public UpdateDonateResponse updateDonate(
            @PathVariable("id") final Long id,
            @RequestBody @Valid final UpdateDonateRequest request) {

        donateService.updateAll(id, request);
        Donate updateDonate = donateService.findById(id);
        return new UpdateDonateResponse(updateDonate);
    }

    @GetMapping("/api/donates")
    public Result findDonates() {
        List<Donate> findDonates = donateService.findByCreatedDateDESC();

        List<DonateDto> collect = findDonates.stream()
                .map(DonateDto::new)
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @GetMapping("/api/donates/details/{id}")
    public GetDonateResponse getDonate(@PathVariable("id") final Long id) {

        Donate findDonate = donateService.findById(id);
        return new GetDonateResponse(findDonate);
    }

    @GetMapping("/api/donates/author/{id}")
    public Result getDonatesByUser(@PathVariable("id") final Long userId) {

        List<Donate> byUser = donateService.findByUser(userId);

        List<GetDonateResponse> collect = byUser.stream()
                .map(GetDonateResponse::new)
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @GetMapping("/api/donates/top")
    public Result getDonatesByTop() {
        List<Donate> byTag = donateService.findByTag(Tag.TOP);

        List<DonateDto> collect = byTag.stream()
                .map(DonateDto::new)
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @GetMapping("/api/donates/pants")
    public Result getDonatesByPants() {
        List<Donate> byTag = donateService.findByTag(Tag.PANTS);

        List<DonateDto> collect = byTag.stream()
                .map(DonateDto::new)
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @GetMapping("/api/donates/shoes")
    public Result getDonatesByShoes() {
        List<Donate> byTag = donateService.findByTag(Tag.SHOES);

        List<DonateDto> collect = byTag.stream()
                .map(DonateDto::new)
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @GetMapping("/api/donates/etc")
    public Result getDonatesByEtc() {
        List<Donate> byTag = donateService.findByTag(Tag.ETC);

        List<DonateDto> collect = byTag.stream()
                .map(DonateDto::new)
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @DeleteMapping("/api/donates/{id}")
    public DeleteDonateResponse deleteDonate(@PathVariable("id") final Long id) {

        donateService.deleteDonate(id);
        return new DeleteDonateResponse(id);
    }
}

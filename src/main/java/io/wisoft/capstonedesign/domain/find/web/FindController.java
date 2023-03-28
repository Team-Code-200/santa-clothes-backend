package io.wisoft.capstonedesign.domain.find.web;

import io.wisoft.capstonedesign.domain.find.application.FindService;
import io.wisoft.capstonedesign.domain.find.persistence.Find;
import io.wisoft.capstonedesign.domain.find.web.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class FindController {

    private final FindService findService;

    @PostMapping("/api/finds/new")
    public CreateFindResponse saveFind(@RequestBody @Valid CreateFindRequest request) {

        Long id = findService.join(request);
        return new CreateFindResponse(id);
    }

    @PatchMapping("/api/finds/{id}")
    public UpdateFindResponse updateFind(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateFindRequest request) {

        findService.updateAll(request);
        Find updateFind = findService.findOne(id);
        return new UpdateFindResponse(
                id,
                updateFind.getTitle(),
                updateFind.getImage(),
                updateFind.getText(),
                updateFind.getView(),
                String.valueOf(updateFind.getTag())
        );
    }

    @GetMapping("/api/finds")
    public Result findFinds() {
        List<Find> findFinds = findService.findFinds();

        List<FindDto> collect = findFinds.stream()
                .map(f -> new FindDto(f.getTitle(), String.valueOf(f.getTag())))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @DeleteMapping("/api/finds/{id}")
    public DeleteFindResponse deleteFind(@PathVariable("id") Long id) {

        findService.deleteFind(id);
        return new DeleteFindResponse(id);
    }
}

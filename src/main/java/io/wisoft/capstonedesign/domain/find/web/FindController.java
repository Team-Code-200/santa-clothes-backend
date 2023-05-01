package io.wisoft.capstonedesign.domain.find.web;

import io.wisoft.capstonedesign.domain.find.application.FindService;
import io.wisoft.capstonedesign.domain.find.persistence.Find;
import io.wisoft.capstonedesign.domain.find.web.dto.*;
import io.wisoft.capstonedesign.global.annotation.SwaggerApiError;
import io.wisoft.capstonedesign.global.annotation.SwaggerApiNotFoundError;
import io.wisoft.capstonedesign.global.annotation.SwaggerApiSuccess;
import io.wisoft.capstonedesign.global.enumerated.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@io.swagger.v3.oas.annotations.tags.Tag(name = "찾아볼래요 게시판", description = "게시판 관리 API")
@RestController
@RequiredArgsConstructor
public class FindController {

    private final FindService findService;

    @SwaggerApiSuccess(summary = "게시글 작성", implementation = CreateFindResponse.class)
    @SwaggerApiError
    @PostMapping("/api/finds/new")
    public CreateFindResponse saveFind(@RequestBody @Valid final CreateFindRequest request) {

        Long id = findService.join(request);
        return new CreateFindResponse(id);
    }

    @SwaggerApiSuccess(summary = "게시글 수정", implementation = UpdateFindResponse.class)
    @SwaggerApiError
    @PatchMapping("/api/finds/{id}")
    public UpdateFindResponse updateFind(
            @PathVariable("id") final Long id,
            @RequestBody @Valid final UpdateFindRequest request) {

        findService.updateAll(id, request);
        Find updateFind = findService.findById(id);
        return new UpdateFindResponse(updateFind);
    }

    @SwaggerApiSuccess(summary = "전체 게시글 목록 조회", implementation = Result.class)
    @SwaggerApiNotFoundError
    @GetMapping("/api/finds")
    public Result findFinds() {
        List<Find> findFinds = findService.findByCreatedDateDESC();

        List<FindDto> collect = findFinds.stream()
                .map(FindDto::new)
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @SwaggerApiSuccess(summary = "게시글 상세 조회", implementation = GetFindResponse.class)
    @SwaggerApiNotFoundError
    @GetMapping("/api/finds/details/{id}")
    public GetFindResponse getFind(@PathVariable("id") final Long id) {

        Find findFind = findService.findById(id);
        return new GetFindResponse(findFind);
    }

    @SwaggerApiSuccess(summary = "특정 작성자의 게시글 조회", implementation = Result.class)
    @SwaggerApiNotFoundError
    @GetMapping("/api/finds/author/{id}")
    public Result getFindsByUser(@PathVariable("id") final Long userId) {

        List<Find> byUser = findService.findByUser(userId);

        List<GetFindResponse> collect = byUser.stream()
                .map(GetFindResponse::new)
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @SwaggerApiSuccess(summary = "게시글 상의 태그로 조회", implementation = Result.class)
    @SwaggerApiNotFoundError
    @GetMapping("/api/finds/top")
    public Result getFindsByTop() {
        List<Find> byTag = findService.findByTag(Tag.TOP);

        List<FindDto> collect = byTag.stream()
                .map(FindDto::new)
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @SwaggerApiSuccess(summary = "게시글 하의 태그로 조회", implementation = Result.class)
    @SwaggerApiNotFoundError
    @GetMapping("/api/finds/pants")
    public Result getFindsByPants() {
        List<Find> byTag = findService.findByTag(Tag.PANTS);

        List<FindDto> collect = byTag.stream()
                .map(FindDto::new)
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @SwaggerApiSuccess(summary = "게시글 신발 태그로 조회", implementation = Result.class)
    @SwaggerApiNotFoundError
    @GetMapping("/api/finds/shoes")
    public Result getFindsByShoes() {
        List<Find> byTag = findService.findByTag(Tag.SHOES);

        List<FindDto> collect = byTag.stream()
                .map(FindDto::new)
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @SwaggerApiSuccess(summary = "게시글 기타 태그로 조회", implementation = Result.class)
    @SwaggerApiNotFoundError
    @GetMapping("/api/finds/etc")
    public Result getFindsByEtc() {
        List<Find> byTag = findService.findByTag(Tag.ETC);

        List<FindDto> collect = byTag.stream()
                .map(FindDto::new)
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @SwaggerApiSuccess(summary = "게시글 삭제", implementation = DeleteFindResponse.class)
    @SwaggerApiError
    @DeleteMapping("/api/finds/{id}")
    public DeleteFindResponse deleteFind(@PathVariable("id") final Long id) {

        findService.deleteFind(id);
        return new DeleteFindResponse(id);
    }
}

package io.wisoft.capstonedesign.domain.user.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.wisoft.capstonedesign.domain.user.persistence.User;
import io.wisoft.capstonedesign.domain.user.web.dto.*;
import io.wisoft.capstonedesign.domain.user.application.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "회원", description = "회원 관리 API")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원 가입")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = CreateUserResponse.class))),
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(
                    responseCode = "409",
                    content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PostMapping("/api/auth/signup")
    public CreateUserResponse saveUser(@RequestBody @Valid final CreateUserRequest request) {

        Long id = userService.join(request);
        return new CreateUserResponse(id);
    }

    @Operation(summary = "회원 정보 수정")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = UpdateUserResponse.class))),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PatchMapping("/api/users/{id}")
    public UpdateUserResponse updateUser(
            @PathVariable("id") final Long id,
            @RequestBody @Valid final UpdateUserRequest request) {

        userService.updateNickname(id, request);
        User findUser = userService.findById(id);
        return new UpdateUserResponse(findUser);
    }

    @Operation(summary = "전체 회원 조회")
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
    @GetMapping("/api/users")
    public Result FindUsers() {
        List<User> findUsers = userService.findUsers();

        List<UserDto> collect = findUsers.stream()
                .map(UserDto::new)
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @Operation(summary = "회원정보 조회")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = GetUserResponse.class))),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/api/users/{id}")
    public GetUserResponse getUser(@PathVariable("id") final Long id) {

        User findUser = userService.findById(id);
        return new GetUserResponse(findUser);
    }

    @Operation(summary = "회원 탈퇴")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = DeleteUserResponse.class))),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @DeleteMapping("/api/users/{id}")
    public DeleteUserResponse deleteUser(@PathVariable("id") final Long id) {

        userService.deleteUser(id);
        return new DeleteUserResponse(id);
    }
}

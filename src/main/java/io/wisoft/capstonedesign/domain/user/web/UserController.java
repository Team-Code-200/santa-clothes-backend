package io.wisoft.capstonedesign.domain.user.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.wisoft.capstonedesign.domain.user.persistence.User;
import io.wisoft.capstonedesign.domain.user.web.dto.*;
import io.wisoft.capstonedesign.domain.user.application.UserService;
import io.wisoft.capstonedesign.global.annotation.SwaggerApiConflictError;
import io.wisoft.capstonedesign.global.annotation.SwaggerApiError;
import io.wisoft.capstonedesign.global.annotation.SwaggerApiSuccess;
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

    @SwaggerApiSuccess(summary = "회원 가입", implementation = CreateUserResponse.class)
    @SwaggerApiConflictError
    @PostMapping("/api/auth/signup")
    public CreateUserResponse saveUser(@RequestBody @Valid final CreateUserRequest request) {

        Long id = userService.join(request);
        return new CreateUserResponse(id);
    }

    @SwaggerApiSuccess(summary = "회원 정보 수정", implementation = UpdateUserResponse.class)
    @SwaggerApiError
    @PatchMapping("/api/users/{id}")
    public UpdateUserResponse updateUser(
            @PathVariable("id") final Long id,
            @RequestBody @Valid final UpdateUserRequest request) {

        userService.updateNickname(id, request);
        User findUser = userService.findById(id);
        return new UpdateUserResponse(findUser);
    }

    @SwaggerApiSuccess(summary = "전체 회원 조회", implementation = Result.class)
    @SwaggerApiError
    @GetMapping("/api/users")
    public Result FindUsers() {
        List<User> findUsers = userService.findUsers();

        List<UserDto> collect = findUsers.stream()
                .map(UserDto::new)
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @SwaggerApiSuccess(summary = "회원 정보 조회", implementation = GetUserResponse.class)
    @SwaggerApiError
    @GetMapping("/api/users/details/{id}")
    public GetUserResponse getUser(@PathVariable("id") final Long id) {

        User findUser = userService.findById(id);
        return new GetUserResponse(findUser);
    }

    @SwaggerApiSuccess(summary = "회원 탈퇴", implementation = DeleteUserResponse.class)
    @SwaggerApiError
    @DeleteMapping("/api/users/{id}")
    public DeleteUserResponse deleteUser(@PathVariable("id") final Long id) {

        userService.deleteUser(id);
        return new DeleteUserResponse(id);
    }
}

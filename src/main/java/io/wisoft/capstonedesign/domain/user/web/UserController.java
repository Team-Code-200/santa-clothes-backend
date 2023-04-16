package io.wisoft.capstonedesign.domain.user.web;

import io.wisoft.capstonedesign.domain.user.persistence.User;
import io.wisoft.capstonedesign.domain.user.web.dto.*;
import io.wisoft.capstonedesign.domain.user.application.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/api/auth/signup")
    public CreateUserResponse saveUser(@RequestBody @Valid final CreateUserRequest request) {

        Long id = userService.join(request);
        return new CreateUserResponse(id);
    }

    @PatchMapping("/api/users/{id}")
    public UpdateUserResponse updateUser(
            @PathVariable("id") final Long id,
            @RequestBody @Valid final UpdateUserRequest request) {

        userService.updateNickname(id, request);
        User findUser = userService.findById(id);
        return new UpdateUserResponse(findUser);
    }

    @GetMapping("/api/users")
    public Result FindUsers() {
        List<User> findUsers = userService.findUsers();

        List<UserDto> collect = findUsers.stream()
                .map(UserDto::new)
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @GetMapping("/api/users/{id}")
    public GetUserResponse getUser(@PathVariable("id") final Long id) {

        User findUser = userService.findById(id);
        return new GetUserResponse(findUser);
    }

    @DeleteMapping("/api/users/{id}")
    public DeleteUserResponse deleteUser(@PathVariable("id") final Long id) {

        userService.deleteUser(id);
        return new DeleteUserResponse(id);
    }
}

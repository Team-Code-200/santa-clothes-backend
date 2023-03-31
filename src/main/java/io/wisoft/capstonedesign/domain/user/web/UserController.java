package io.wisoft.capstonedesign.domain.user.web;

import io.wisoft.capstonedesign.domain.user.persistence.User;
import io.wisoft.capstonedesign.domain.user.web.dto.*;
import io.wisoft.capstonedesign.global.enumerated.Role;
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

        User user = User.newInstance(
                request.getOauthId(),
                request.getEmail(),
                request.getProfileImage(),
                request.getPoint(),
                request.getNickname(),
                Role.valueOf(request.getUserRole()));

        Long id = userService.join(user);
        return new CreateUserResponse(id);
    }

    @PatchMapping("/api/users/{id}")
    public UpdateUserResponse updateUser(
            @PathVariable("id") final Long id,
            @RequestBody @Valid final UpdateUserRequest request) {

        userService.updateNickname(id, request.getNickname());
        User findUser = userService.findOne(id);
        return new UpdateUserResponse(findUser.getId(), findUser.getNickname());
    }

    @GetMapping("/api/users")
    public Result FindUsers() {
        List<User> findUsers = userService.findUsers();

        List<UserDto> collect = findUsers.stream()
                .map(m -> new UserDto(m.getNickname()))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @DeleteMapping("/api/users/{id}")
    public DeleteUserResponse deleteUser(@PathVariable("id") final Long id) {

        userService.deleteUser(id);
        return new DeleteUserResponse(id);
    }
}

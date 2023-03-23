package io.wisoft.capstonedesign.controller;

import io.wisoft.capstonedesign.domain.Role;
import io.wisoft.capstonedesign.domain.User;
import io.wisoft.capstonedesign.global.dto.request.user.CreateUserRequest;
import io.wisoft.capstonedesign.global.dto.request.user.UpdateUserRequest;
import io.wisoft.capstonedesign.global.dto.request.user.UserDto;
import io.wisoft.capstonedesign.global.dto.response.user.CreateUserResponse;
import io.wisoft.capstonedesign.global.dto.response.user.Result;
import io.wisoft.capstonedesign.global.dto.response.user.UpdateUserResponse;
import io.wisoft.capstonedesign.service.UserService;
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
    public CreateUserResponse saveUser(@RequestBody @Valid CreateUserRequest request) {

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
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateUserRequest request) {

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
}

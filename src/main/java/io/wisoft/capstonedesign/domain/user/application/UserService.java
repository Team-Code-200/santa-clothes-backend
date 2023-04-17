package io.wisoft.capstonedesign.domain.user.application;

import io.wisoft.capstonedesign.domain.user.persistence.User;
import io.wisoft.capstonedesign.domain.user.persistence.UserRepository;
import io.wisoft.capstonedesign.domain.user.web.dto.CreateUserRequest;
import io.wisoft.capstonedesign.domain.user.web.dto.UpdateUserRequest;
import io.wisoft.capstonedesign.global.enumerated.Role;
import io.wisoft.capstonedesign.global.exception.service.UserDuplicateException;
import io.wisoft.capstonedesign.global.exception.service.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static io.wisoft.capstonedesign.global.exception.ErrorCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * 회원 저장
     */
    @Transactional
    public Long join(final CreateUserRequest request) {
        validateDuplicateUser(request);

        User user = User.builder()
                .oauthId(request.oauthId())
                .email(request.email())
                .profileImage(request.profileImage())
                .point(request.point())
                .nickname(request.nickname())
                .userRole(Role.valueOf(request.userRole()))
                .build();

        userRepository.save(user);
        return user.getId();
    }

    /**
     * 이메일 중복 검사
     */
    private void validateDuplicateUser(final CreateUserRequest request) {
        List<User> findUsers = userRepository.findByEmail(request.email());
        if (!findUsers.isEmpty()) throw new UserDuplicateException(DUPLICATE_USER);
    }

    /**
     * 회원 전체 조회
     */
    public List<User> findUsers() {
        return userRepository.findAll();
    }

    /**
     * 단 건 조회
     */
    public User findById(final Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(NOT_FOUND_ACCOUNT));
    }

    /**
     * 닉네임 수정
     */
    @Transactional
    public void updateNickname(final Long userId, final UpdateUserRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(NOT_FOUND_ACCOUNT));

        validateNickname(request.nickname());
        user.updateNickname(request.nickname());
    }

    private void validateNickname(final String nickname) {
        if (nickname == null) {
            throw new IllegalStateException("닉네임을 입력해주세요.");
        }
    }

    /**
     * 회원 삭제
     */
    @Transactional
    public void deleteUser(final Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(NOT_FOUND_ACCOUNT));
        userRepository.delete(user);
    }
}

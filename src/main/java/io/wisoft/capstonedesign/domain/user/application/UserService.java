package io.wisoft.capstonedesign.domain.user.application;

import io.wisoft.capstonedesign.domain.user.persistence.User;
import io.wisoft.capstonedesign.domain.user.persistence.UserRepository;
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
    public Long join(final User user) {
        validateDuplicateUser(user);
        userRepository.save(user);
        return user.getId();
    }

    /**
     * 이메일 중복 검사
     */
    private void validateDuplicateUser(final User user) {
        List<User> findUsers = userRepository.findByEmail(user.getEmail());
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
    public User findOne(final Long userId) {
        return userRepository.findOne(userId)
                .orElseThrow(() -> new UserNotFoundException(NOT_FOUND_ACCOUNT));
    }

    /**
     * 닉네임 수정
     */
    @Transactional
    public void updateNickname(final Long userId, final String nickname) {
        User user = userRepository.findOne(userId)
                .orElseThrow(() -> new UserNotFoundException(NOT_FOUND_ACCOUNT));
        validateNickname(nickname);
        user.updateNickname(nickname);
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
        User user = userRepository.findOne(userId).orElseThrow(() -> new UserNotFoundException(NOT_FOUND_ACCOUNT));
        userRepository.delete(user);
    }
}

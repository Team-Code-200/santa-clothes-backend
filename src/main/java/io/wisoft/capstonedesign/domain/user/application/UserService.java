package io.wisoft.capstonedesign.domain.user.application;

import io.wisoft.capstonedesign.domain.user.persistence.User;
import io.wisoft.capstonedesign.domain.user.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * 회원 저장
     */
    @Transactional
    public Long join(User user) {
        validateDuplicateUser(user);
        userRepository.save(user);
        return user.getId();
    }

    /**
     * 이메일 중복 검사
     */
    private void validateDuplicateUser(User user) {
        List<User> findUsers = userRepository.findByEmail(user.getEmail());
        if (!findUsers.isEmpty()) throw new IllegalStateException("이미 존재하는 회원입니다.");
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
    public User findOne(Long userId) {
        return userRepository.findOne(userId);
    }

    /**
     * 닉네임 수정
     */
    @Transactional
    public void updateNickname(Long userId, String nickname) {
        User user = findOne(userId);
        validateNickname(nickname);
        user.updateNickname(nickname);
    }

    private void validateNickname(String nickname) {
        if (nickname == null) {
            throw new IllegalStateException("닉네임을 입력해주세요.");
        }
    }

    /**
     * 회원 삭제
     */
    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findOne(userId);
        userRepository.delete(user);
    }
}
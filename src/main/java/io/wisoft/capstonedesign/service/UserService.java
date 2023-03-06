package io.wisoft.capstonedesign.service;

import io.wisoft.capstonedesign.domain.User;
import io.wisoft.capstonedesign.repository.UserRepository;
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
}

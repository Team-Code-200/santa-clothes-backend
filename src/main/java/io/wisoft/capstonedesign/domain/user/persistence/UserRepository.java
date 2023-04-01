package io.wisoft.capstonedesign.domain.user.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByNickname(final String nickname);

    List<User> findByEmail(final String email);
}

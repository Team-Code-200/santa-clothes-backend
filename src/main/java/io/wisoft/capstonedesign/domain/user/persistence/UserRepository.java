package io.wisoft.capstonedesign.domain.user.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByNickname(final String nickname);

    List<User> findByEmail(final String email);

    Optional<User> findByOauthId(final String oauthId);
}

package io.wisoft.capstonedesign.domain.donate.persistence;

import io.wisoft.capstonedesign.global.enumerated.Tag;
import io.wisoft.capstonedesign.domain.user.persistence.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonateRepository extends JpaRepository<Donate, Long> {

    List<Donate> findByUser(final User user);

    List<Donate> findAllByOrderByCreatedDateDesc();

    List<Donate> findByTag(final Tag tag);
}

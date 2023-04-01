package io.wisoft.capstonedesign.domain.find.persistence;

import io.wisoft.capstonedesign.domain.user.persistence.User;
import io.wisoft.capstonedesign.global.enumerated.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FindRepository extends JpaRepository<Find, Long> {

    List<Find> findByUser(final User user);

    List<Find> findAllByOrderByCreatedDateDesc();

    List<Find> findByTag(final Tag tag);
}

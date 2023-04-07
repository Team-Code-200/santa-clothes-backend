package io.wisoft.capstonedesign.domain.donate.persistence;

import io.wisoft.capstonedesign.global.enumerated.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DonateRepository extends JpaRepository<Donate, Long> {

    @Query("select d from Donate d join fetch d.user u where u.id = :id")
    List<Donate> findByUser(@Param("id") final Long userId);

    List<Donate> findAllByOrderByCreatedDateDesc();

    List<Donate> findByTag(final Tag tag);
}

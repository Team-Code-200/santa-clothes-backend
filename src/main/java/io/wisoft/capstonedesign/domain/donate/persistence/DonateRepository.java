package io.wisoft.capstonedesign.domain.donate.persistence;

import io.wisoft.capstonedesign.global.enumerated.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DonateRepository extends JpaRepository<Donate, Long> {

    @Query("select d from Donate d join fetch d.user u where u.id = :id order by d.createdDate desc")
    List<Donate> findByUser(@Param("id") final Long userId);

    @Query(value = "select d from Donate d order by d.createdDate desc", countQuery = "select count(d) from Donate d")
    Page<Donate> findAllByOrderByCreatedDateDesc(final Pageable pageable);

    List<Donate> findByTag(final Tag tag);
}

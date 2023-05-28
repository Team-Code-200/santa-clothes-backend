package io.wisoft.capstonedesign.domain.find.persistence;

import io.wisoft.capstonedesign.domain.user.persistence.User;
import io.wisoft.capstonedesign.global.enumerated.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FindRepository extends JpaRepository<Find, Long> {

    @Query("select f from Find f join fetch f.user u where u.id = :id order by f.createdDate desc")
    List<Find> findByUser(@Param("id") final Long userId);

    @Query(value = "select f from Find f order by f.createdDate desc", countQuery = "select count(f) from Find f")
    Page<Find> findAllByOrderByCreatedDateDesc(final Pageable pageable);

    List<Find> findByTag(final Tag tag);
}

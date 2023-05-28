package io.wisoft.capstonedesign.domain.information.persistence;

import io.wisoft.capstonedesign.domain.user.persistence.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InformationRepository extends JpaRepository<Information, Long> {

    @Query("select i from Information i join fetch i.user u where u.id = :id")
    List<Information> findByUser(@Param("id") final Long userId);

    @Query(value = "select i from Information i order by i.createdDate desc", countQuery = "select count(i) from Information i")
    Page<Information> findAllByOrderByCreatedDateDesc(final Pageable pageable);
}

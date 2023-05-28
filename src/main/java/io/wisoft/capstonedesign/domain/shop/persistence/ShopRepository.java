package io.wisoft.capstonedesign.domain.shop.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShopRepository extends JpaRepository<Shop, Long> {

    List<Shop> findByTitle(final String title);

    @Query(value = "select s from Shop s order by s.createdDate desc", countQuery = "select count(s) from Shop s")
    Page<Shop> findAllByOrderByCreatedDateDesc(final Pageable pageable);
}

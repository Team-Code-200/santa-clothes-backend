package io.wisoft.capstonedesign.domain.shop.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopRepository extends JpaRepository<Shop, Long> {

    List<Shop> findByTitle(final String title);

    List<Shop> findAllByOrderByCreatedDateDesc();
}

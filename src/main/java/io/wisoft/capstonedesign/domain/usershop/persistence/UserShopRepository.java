package io.wisoft.capstonedesign.domain.usershop.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserShopRepository extends JpaRepository<UserShop, Long> {

    @Query("select s from UserShop s join fetch s.user u where u.id = :id order by s.createdDate desc")
    List<UserShop> findByUser(@Param("id") final Long userId);

    List<UserShop> findAllByOrderByCreatedDateDesc();
}

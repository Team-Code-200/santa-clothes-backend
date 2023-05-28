package io.wisoft.capstonedesign.domain.donateorder.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DonateOrderRepository extends JpaRepository<DonateOrder, Long> {

    @Query("select o from DonateOrder o join fetch o.user u where u.id = :id order by o.sendDate desc")
    List<DonateOrder> findByUser(@Param("id") final Long userId);

    @Query(value = "select o from DonateOrder o order by o.sendDate desc", countQuery = "select count(o) from DonateOrder o")
    Page<DonateOrder> findAllByOrderBySendDateDesc(final Pageable pageable);
}

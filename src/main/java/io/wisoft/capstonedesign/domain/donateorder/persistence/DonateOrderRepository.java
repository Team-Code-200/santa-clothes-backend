package io.wisoft.capstonedesign.domain.donateorder.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DonateOrderRepository extends JpaRepository<DonateOrder, Long> {

    @Query("select o from DonateOrder o join fetch o.user u where u.id = :id order by o.sendDate desc")
    List<DonateOrder> findByUser(@Param("id") final Long userId);

    List<DonateOrder> findAllByOrderBySendDateDesc();
}

package io.wisoft.capstonedesign.domain.findorder.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FindOrderRepository extends JpaRepository<FindOrder, Long> {

    @Query("select o from FindOrder o join fetch o.user u where u.id = :id order by o.sendDate desc")
    List<FindOrder> findByUser(@Param("id") final Long userId);

    List<FindOrder> findAllByOrderBySendDateDesc();
}

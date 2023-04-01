package io.wisoft.capstonedesign.domain.donateorder.persistence;

import io.wisoft.capstonedesign.domain.user.persistence.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonateOrderRepository extends JpaRepository<DonateOrder, Long> {

    List<DonateOrder> findAllByUserOrderBySendDateDesc(final User user);

    List<DonateOrder> findAllByOrderBySendDateDesc();
}

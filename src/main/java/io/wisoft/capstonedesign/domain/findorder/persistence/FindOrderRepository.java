package io.wisoft.capstonedesign.domain.findorder.persistence;

import io.wisoft.capstonedesign.domain.user.persistence.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FindOrderRepository extends JpaRepository<FindOrder, Long> {

    List<FindOrder> findAllByUserOrderBySendDateDesc(final User user);

    List<FindOrder> findAllByOrderBySendDateDesc();
}

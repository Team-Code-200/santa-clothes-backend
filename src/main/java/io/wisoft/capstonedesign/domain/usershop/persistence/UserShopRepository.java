package io.wisoft.capstonedesign.domain.usershop.persistence;

import io.wisoft.capstonedesign.domain.user.persistence.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserShopRepository extends JpaRepository<UserShop, Long> {

    List<UserShop> findAllByUserOrderByCreatedDateDesc(final User user);

    List<UserShop> findAllByOrderByCreatedDateDesc();
}

package io.wisoft.capstonedesign.domain.user.persistence;

import io.wisoft.capstonedesign.domain.donate.persistence.Donate;
import io.wisoft.capstonedesign.domain.donateorder.persistence.DonateOrder;
import io.wisoft.capstonedesign.domain.find.persistence.Find;
import io.wisoft.capstonedesign.domain.findorder.persistence.FindOrder;
import io.wisoft.capstonedesign.domain.information.persistence.Information;
import io.wisoft.capstonedesign.domain.payment.persistence.OrderPayment;
import io.wisoft.capstonedesign.domain.usershop.persistence.UserShop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserMyInfoRepository extends JpaRepository<User, Long> {

    @Query(value = "select d from Donate d" +
            " join fetch d.user u" +
            " where u.id = :id" +
            " order by d.createdDate desc",
            countQuery = "select count(d) from Donate d")
    Page<Donate> findDonatesByIdUsingPaging(@Param("id") final Long userId, final Pageable pageable);

    @Query(value = "select f from Find f" +
            " join fetch f.user u" +
            " where u.id = :id" +
            " order by f.createdDate desc",
            countQuery = "select count(f) from Find f")
    Page<Find> findFindsByIdUsingPaging(@Param("id") final Long userId, final Pageable pageable);

    @Query(value = "select i from Information i" +
            " join fetch i.user u" +
            " where u.id = :id" +
            " order by i.createdDate desc",
            countQuery = "select count(i) from Information i")
    Page<Information> findInfosByIdUsingPaging(@Param("id") final Long userId, final Pageable pageable);

    @Query(value = "select o from DonateOrder o" +
            " join fetch o.user u" +
            " where u.id = :id" +
            " order by o.sendDate desc",
            countQuery = "select count(o) from DonateOrder o")
    Page<DonateOrder> findDonateOrdersByIdUsingPaging(@Param("id") final Long userId, final Pageable pageable);

    @Query(value = "select o from FindOrder o" +
            " join fetch o.user u" +
            " where u.id = :id" +
            " order by o.sendDate desc",
            countQuery = "select count(o) from FindOrder o")
    Page<FindOrder> findFindOrdersByIdUsingPaging(@Param("id") final Long userId, final Pageable pageable);

    @Query(value = "select s from UserShop s" +
            " join fetch s.user u" +
            " where u.id = :id" +
            " order by s.createdDate desc",
            countQuery = "select count(s) from UserShop s")
    Page<UserShop> findShopOrdersByIdUsingPaging(@Param("id") final Long userId, final Pageable pageable);

    @Query(value = "select o from OrderPayment o" +
            " join fetch o.userShop s" +
            " join fetch s.user u" +
            " where u.id = :id" +
            " order by o.createdDate desc",
            countQuery = "select count(o) from OrderPayment o")
    Page<OrderPayment> findPaymentsByIdUsingPaging(@Param("id") final Long userId, final Pageable pageable);
}

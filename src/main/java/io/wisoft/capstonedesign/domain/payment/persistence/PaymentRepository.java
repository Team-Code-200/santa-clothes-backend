package io.wisoft.capstonedesign.domain.payment.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaymentRepository extends JpaRepository<OrderPayment, Long> {

    @Query(value = "select p from OrderPayment p order by p.createdDate desc", countQuery = "select count(p) from OrderPayment p")
    Page<OrderPayment> findAllByOrderByCreatedDateDesc(final Pageable pageable);
}

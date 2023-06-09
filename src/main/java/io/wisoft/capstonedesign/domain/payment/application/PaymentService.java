package io.wisoft.capstonedesign.domain.payment.application;

import io.wisoft.capstonedesign.domain.payment.persistence.OrderPayment;
import io.wisoft.capstonedesign.domain.payment.persistence.PaymentRepository;
import io.wisoft.capstonedesign.domain.usershop.persistence.UserShop;
import io.wisoft.capstonedesign.domain.usershop.persistence.UserShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserShopRepository userShopRepository;

    /**
     * 결제 정보 저장
     */
    @Transactional
    public Long save(final OrderPayment orderPayment) {

        UserShop userShop = userShopRepository.findById(orderPayment.getUserShop().getId())
                .orElseThrow(IllegalStateException::new);

        paymentRepository.save(orderPayment);
        return orderPayment.getId();
    }

    /**
     * 결제 정보 전체 조회
     */
    public List<OrderPayment> findPayments() {
        return paymentRepository.findAll();
    }

    /**
     * 단 건 조회
     */
    public OrderPayment findById(final Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(IllegalStateException::new);
    }

    /**
     * 모든 결제 정보 페이징 및 최근순으로 조회 - 기본값
     */
    public Page<OrderPayment> findByCreatedDateDescUsingPaging(final Pageable pageable) {
        return paymentRepository.findAllByOrderByCreatedDateDesc(pageable);
    }

    /**
     * 결제 정보 취소
     */
    public void cancelPayment(final Long paymentId) {
        OrderPayment orderPayment = paymentRepository.findById(paymentId)
                .orElseThrow(IllegalStateException::new);

        orderPayment.cancel();
    }

    /**
     * 결제 정보 삭제
     */
    @Transactional
    public void deletePayment(final Long paymentId) {
        OrderPayment orderPayment = paymentRepository.findById(paymentId)
                .orElseThrow(IllegalStateException::new);

        paymentRepository.delete(orderPayment);
    }
}

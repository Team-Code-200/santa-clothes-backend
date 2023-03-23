package io.wisoft.capstonedesign.domain.donateorder.application;

import io.wisoft.capstonedesign.domain.donateorder.persistence.DonateOrder;
import io.wisoft.capstonedesign.domain.donateorder.persistence.DonateOrderRepository;
import io.wisoft.capstonedesign.domain.user.persistence.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DonateOrderService {

    private final DonateOrderRepository donateOrderRepository;

    /**
     * 주문 내역 저장
     */
    @Transactional
    public Long save(DonateOrder donateOrder) {
        donateOrderRepository.save(donateOrder);
        return donateOrder.getId();
    }

    /**
     * 모든 주문 내역 전체 조회
     */
    public List<DonateOrder> findDonateOrders() {
        return donateOrderRepository.findAll();
    }

    /**
     * 단 건 조회
     */
    public DonateOrder findOne(Long id) {
        return donateOrderRepository.findOne(id);
    }

    /**
     * 특정 사용자의 주문 내역 최근순으로 조회 - 기본값
     */
    public List<DonateOrder> findByUserDESC(User user) {
        return donateOrderRepository.findByUserDESC(user);
    }

    /**
     * 모든 주문 내역 최근순으로 조회
     */
    public List<DonateOrder> findByCreatedDateDESC() {
        return donateOrderRepository.findByCreatedDateDESC();
    }

    /**
     * 주문 내역 기타 사항 수정
     */
    @Transactional
    public void updateBody(Long orderId, String text) {
        DonateOrder donateOrder = findOne(orderId);
        donateOrder.update(text);
    }

    /**
     * 주문 내역 삭제
     */
    @Transactional
    public void deleteOrder(Long orderId) {
        DonateOrder donateOrder = donateOrderRepository.findOne(orderId);
        donateOrderRepository.delete(donateOrder);
    }
}

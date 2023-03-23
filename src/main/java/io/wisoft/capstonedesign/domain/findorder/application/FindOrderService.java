package io.wisoft.capstonedesign.domain.findorder.application;

import io.wisoft.capstonedesign.domain.findorder.persistence.FindOrder;
import io.wisoft.capstonedesign.domain.findorder.persistence.FindOrderRepository;
import io.wisoft.capstonedesign.domain.user.persistence.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FindOrderService {

    private final FindOrderRepository findOrderRepository;

    /**
     * 주문 내역 저장
     */
    @Transactional
    public Long save(FindOrder findOrder) {
        findOrderRepository.save(findOrder);
        return findOrder.getId();
    }

    /**
     * 모든 주문 내역 전체 조회
     */
    public List<FindOrder> findFindOrders() {
        return findOrderRepository.findAll();
    }

    /**
     * 단 건 조회
     */
    public FindOrder findOne(Long id) {
        return findOrderRepository.findOne(id);
    }

    /**
     * 특정 사용자의 주문 내역 최근순으로 조회 - 기본값
     */
    public List<FindOrder> findByUserDESC(User user) {
        return findOrderRepository.findByUserDESC(user);
    }

    /**
     * 모든 주문 내역 최근순으로 조회
     */
    public List<FindOrder> findByCreatedDateDESC() {
        return findOrderRepository.findByCreatedDateDESC();
    }

    /**
     * 주문 내역 기타 사항 수정
     */
    @Transactional
    public void updateBody(Long orderId, String text) {
        FindOrder findOrder = findOne(orderId);
        findOrder.update(text);
    }

    /**
     * 주문 내역 삭제
     */
    @Transactional
    public void deleteOrder(Long orderId) {
        FindOrder findOrder = findOrderRepository.findOne(orderId);
        findOrderRepository.delete(findOrder);
    }
}

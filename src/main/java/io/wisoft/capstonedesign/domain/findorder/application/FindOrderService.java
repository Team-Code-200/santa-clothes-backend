package io.wisoft.capstonedesign.domain.findorder.application;

import io.wisoft.capstonedesign.domain.find.persistence.Find;
import io.wisoft.capstonedesign.domain.find.persistence.FindRepository;
import io.wisoft.capstonedesign.domain.findorder.persistence.FindOrder;
import io.wisoft.capstonedesign.domain.findorder.persistence.FindOrderRepository;
import io.wisoft.capstonedesign.domain.findorder.web.dto.CreateOrderRequest;
import io.wisoft.capstonedesign.domain.findorder.web.dto.UpdateOrderRequest;
import io.wisoft.capstonedesign.domain.information.persistence.Information;
import io.wisoft.capstonedesign.domain.information.persistence.InformationRepository;
import io.wisoft.capstonedesign.domain.user.persistence.User;
import io.wisoft.capstonedesign.domain.user.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FindOrderService {

    private final FindOrderRepository findOrderRepository;
    private final UserRepository userRepository;
    private final InformationRepository informationRepository;
    private final FindRepository findRepository;

    /**
     * 주문 내역 저장
     */
    @Transactional
    public Long save(CreateOrderRequest request) {

        User user = userRepository.findOne(request.getUserId());
        Information information = informationRepository.findOne(request.getInfoId());
        Find find = findRepository.findOne(request.getFindId());

        FindOrder findOrder = FindOrder.createFindOrder(
                request.getText(),
                information,
                find,
                user
        );

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
    public void updateBody(UpdateOrderRequest request) {
        FindOrder findOrder = findOne(request.getOrderId());
        findOrder.update(request.getText());
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

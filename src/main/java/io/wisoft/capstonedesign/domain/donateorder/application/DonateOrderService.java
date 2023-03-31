package io.wisoft.capstonedesign.domain.donateorder.application;

import io.wisoft.capstonedesign.domain.donate.persistence.Donate;
import io.wisoft.capstonedesign.domain.donate.persistence.DonateRepository;
import io.wisoft.capstonedesign.domain.donateorder.persistence.DonateOrder;
import io.wisoft.capstonedesign.domain.donateorder.persistence.DonateOrderRepository;
import io.wisoft.capstonedesign.domain.donateorder.web.dto.CreateOrderRequest;
import io.wisoft.capstonedesign.domain.donateorder.web.dto.UpdateOrderRequest;
import io.wisoft.capstonedesign.domain.information.persistence.Information;
import io.wisoft.capstonedesign.domain.information.persistence.InformationRepository;
import io.wisoft.capstonedesign.domain.user.persistence.User;
import io.wisoft.capstonedesign.domain.user.persistence.UserRepository;
import io.wisoft.capstonedesign.global.exception.ErrorCode;
import io.wisoft.capstonedesign.global.exception.service.InfoNotFoundException;
import io.wisoft.capstonedesign.global.exception.service.OrderNotFoundException;
import io.wisoft.capstonedesign.global.exception.service.PostNotFoundException;
import io.wisoft.capstonedesign.global.exception.service.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static io.wisoft.capstonedesign.global.exception.ErrorCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DonateOrderService {

    private final DonateOrderRepository donateOrderRepository;
    private final UserRepository userRepository;
    private final InformationRepository informationRepository;
    private final DonateRepository donateRepository;

    /**
     * 주문 내역 저장
     */
    @Transactional
    public Long save(final CreateOrderRequest request) {

        User user = userRepository.findOne(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException(NOT_FOUND_ACCOUNT));
        Information information = informationRepository.findOne(request.getInfoId())
                .orElseThrow(() -> new InfoNotFoundException(NOT_FOUND_INFO));
        Donate donate = donateRepository.findOne(request.getDonateId())
                .orElseThrow(() -> new PostNotFoundException(NOT_FOUND_POST));

        DonateOrder donateOrder = DonateOrder.createDonateOrder(
                request.getText(),
                information,
                donate,
                user
        );

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
    public DonateOrder findOne(final Long id) {
        return donateOrderRepository.findOne(id)
                .orElseThrow(() -> new OrderNotFoundException(NOT_FOUND_ORDER));
    }

    /**
     * 특정 사용자의 주문 내역 최근순으로 조회 - 기본값
     */
    public List<DonateOrder> findByUserDESC(final User user) {
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
    public void updateBody(final UpdateOrderRequest request) {
        DonateOrder donateOrder = donateOrderRepository.findOne(request.getOrderId())
                .orElseThrow(() -> new OrderNotFoundException(NOT_FOUND_ORDER));
        donateOrder.update(request.getText());
    }

    /**
     * 주문 내역 삭제
     */
    @Transactional
    public void deleteOrder(final Long orderId) {
        DonateOrder donateOrder = donateOrderRepository.findOne(orderId)
                .orElseThrow(() -> new OrderNotFoundException(NOT_FOUND_ORDER));
        donateOrderRepository.delete(donateOrder);
    }
}

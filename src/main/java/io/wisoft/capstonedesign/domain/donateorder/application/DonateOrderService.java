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
import io.wisoft.capstonedesign.global.exception.service.InfoNotFoundException;
import io.wisoft.capstonedesign.global.exception.service.OrderNotFoundException;
import io.wisoft.capstonedesign.global.exception.service.PostNotFoundException;
import io.wisoft.capstonedesign.global.exception.service.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

        User user = userRepository.findById(request.userId())
                .orElseThrow(UserNotFoundException::new);
        Information information = informationRepository.findById(request.infoId())
                .orElseThrow(InfoNotFoundException::new);
        Donate donate = donateRepository.findById(request.donateId())
                .orElseThrow(PostNotFoundException::new);

        DonateOrder donateOrder = DonateOrder.builder()
                .text(request.text())
                .information(information)
                .donate(donate)
                .user(user)
                .build();

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
    public DonateOrder findById(final Long id) {
        return donateOrderRepository.findById(id)
                .orElseThrow(OrderNotFoundException::new);
    }

    /**
     * 특정 사용자의 주문 내역 최근순으로 조회
     */
    public List<DonateOrder> findByUser(final Long userId) {
        return donateOrderRepository.findByUser(userId);
    }

    /**
     * 모든 주문 내역 페이징 및 최근순으로 조회 - 기본값
     */
    public Page<DonateOrder> findByCreatedDateDescUsingPaging(final Pageable pageable) {
        return donateOrderRepository.findAllByOrderBySendDateDesc(pageable);
    }

    /**
     * 주문 내역 기타 사항 수정
     */
    @Transactional
    public void updateBody(final Long id, final UpdateOrderRequest request) {
        DonateOrder donateOrder = donateOrderRepository.findById(id)
                .orElseThrow(OrderNotFoundException::new);
        donateOrder.update(request.text());
    }

    /**
     * 주문 내역 삭제
     */
    @Transactional
    public void deleteOrder(final Long orderId) {
        DonateOrder donateOrder = donateOrderRepository.findById(orderId)
                .orElseThrow(OrderNotFoundException::new);
        donateOrderRepository.delete(donateOrder);
    }
}

package io.wisoft.capstonedesign.domain.usershop.application;

import io.wisoft.capstonedesign.domain.information.persistence.Information;
import io.wisoft.capstonedesign.domain.information.persistence.InformationRepository;
import io.wisoft.capstonedesign.domain.shop.persistence.Shop;
import io.wisoft.capstonedesign.domain.shop.persistence.ShopRepository;
import io.wisoft.capstonedesign.domain.user.persistence.UserRepository;
import io.wisoft.capstonedesign.domain.usershop.persistence.UserShopRepository;
import io.wisoft.capstonedesign.domain.user.persistence.User;
import io.wisoft.capstonedesign.domain.usershop.persistence.UserShop;
import io.wisoft.capstonedesign.domain.usershop.web.dto.CreateOrderRequest;
import io.wisoft.capstonedesign.domain.usershop.web.dto.UpdateOrderRequest;
import io.wisoft.capstonedesign.global.exception.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static io.wisoft.capstonedesign.global.exception.ErrorCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserShopService {

    private final UserShopRepository userShopRepository;
    private final UserRepository userRepository;
    private final InformationRepository informationRepository;
    private final ShopRepository shopRepository;

    /**
     * 주문 내역 저장
     */
    @Transactional
    public Long save(final CreateOrderRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException(NOT_FOUND_ACCOUNT));
        Information information = informationRepository.findById(request.getInfoId())
                .orElseThrow(() -> new InfoNotFoundException(NOT_FOUND_INFO));
        Shop shop = shopRepository.findById(request.getShopId())
                .orElseThrow(() -> new PostNotFoundException(NOT_FOUND_POST));

        UserShop userShop = UserShop.builder()
                .text(request.getText())
                .user(user)
                .shop(shop)
                .information(information)
                .build();

        userShopRepository.save(userShop);
        return userShop.getId();
    }

    /**
     * 모든 주문 내역 전체 조회
     */
    public List<UserShop> findShopOrders() {
        return userShopRepository.findAll();
    }

    /**
     * 단 건 조회
     */
    public UserShop findById(final Long id) {
        return userShopRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(NOT_FOUND_ORDER));
    }

    /**
     * 특정 사용자의 주문 내역 최근순으로 조회 - 기본값
     */
    public List<UserShop> findByUser(final Long userId) {
        return userShopRepository.findByUser(userId);
    }

    /**
     * 모든 주문 내역 최근순으로 조회
     */
    public List<UserShop> findByCreatedDateDESC() {
        return userShopRepository.findAllByOrderByCreatedDateDesc();
    }

    /**
     * 주문 내역 기타 사항 수정
     */
    @Transactional
    public void updateBody(final Long id, final UpdateOrderRequest request) {
        UserShop userShop = userShopRepository.findById(request.getOrderId())
                .orElseThrow(() -> new OrderNotFoundException(NOT_FOUND_ORDER));
        userShop.update(request.getText());
    }

    /**
     * 주문 내역 삭제
     */
    @Transactional
    public void deleteOrder(final Long orderId) {
        UserShop userShop = userShopRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(NOT_FOUND_ORDER));
        userShopRepository.delete(userShop);
    }
}

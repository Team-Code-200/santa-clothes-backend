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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

        User user = userRepository.findById(request.userId())
                .orElseThrow(UserNotFoundException::new);
        Information information = informationRepository.findById(request.infoId())
                .orElseThrow(InfoNotFoundException::new);
        Shop shop = shopRepository.findById(request.shopId())
                .orElseThrow(PostNotFoundException::new);

        UserShop userShop = UserShop.builder()
                .text(request.text())
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
                .orElseThrow(OrderNotFoundException::new);
    }

    /**
     * 특정 사용자의 주문 내역 최근순으로 조회
     */
    public List<UserShop> findByUser(final Long userId) {
        return userShopRepository.findByUser(userId);
    }

    /**
     * 모든 주문 내역 페이징 및 최근순으로 조회 - 기본값
     */
    public Page<UserShop> findByCreatedDateDescUsingPaging(final Pageable pageable) {
        return userShopRepository.findAllByOrderByCreatedDateDesc(pageable);
    }

    /**
     * 주문 내역 기타 사항 수정
     */
    @Transactional
    public void updateBody(final Long id, final UpdateOrderRequest request) {
        UserShop userShop = userShopRepository.findById(request.orderId())
                .orElseThrow(OrderNotFoundException::new);
        userShop.update(request.text());
    }

    /**
     * 주문 내역 삭제
     */
    @Transactional
    public void deleteOrder(final Long orderId) {
        UserShop userShop = userShopRepository.findById(orderId)
                .orElseThrow(OrderNotFoundException::new);
        userShopRepository.delete(userShop);
    }
}

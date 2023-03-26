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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public Long save(CreateOrderRequest request) {

        User user = userRepository.findOne(request.getUserId());
        Information information = informationRepository.findOne(request.getInfoId());
        Shop shop = shopRepository.findOne(request.getShopId());

        UserShop userShop = UserShop.createUserShop(
                request.getText(),
                user,
                shop,
                information
        );

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
    public UserShop findOne(Long id) {
        return userShopRepository.findOne(id);
    }

    /**
     * 특정 사용자의 주문 내역 최근순으로 조회 - 기본값
     */
    public List<UserShop> findByUserDESC(User user) {
        return userShopRepository.findByUserDESC(user);
    }

    /**
     * 모든 주문 내역 최근순으로 조회
     */
    public List<UserShop> findByCreatedDateDESC() {
        return userShopRepository.findByCreatedDateDESC();
    }

    /**
     * 주문 내역 기타 사항 수정
     */
    @Transactional
    public void updateBody(UpdateOrderRequest request) {
        UserShop userShop = findOne(request.getOrderId());
        userShop.update(request.getText());
    }

    /**
     * 주문 내역 삭제
     */
    @Transactional
    public void deleteOrder(Long orderId) {
        UserShop userShop = userShopRepository.findOne(orderId);
        userShopRepository.delete(userShop);
    }
}

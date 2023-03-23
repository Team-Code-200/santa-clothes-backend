package io.wisoft.capstonedesign.domain.usershop.application;

import io.wisoft.capstonedesign.domain.usershop.persistence.UserShopRepository;
import io.wisoft.capstonedesign.domain.user.persistence.User;
import io.wisoft.capstonedesign.domain.usershop.persistence.UserShop;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserShopService {

    private final UserShopRepository userShopRepository;

    /**
     * 주문 내역 저장
     */
    @Transactional
    public Long save(UserShop userShop) {
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
    public void updateBody(Long orderId, String text) {
        UserShop userShop = findOne(orderId);
        userShop.update(text);
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

package io.wisoft.capstonedesign.domain.shop.application;

import io.wisoft.capstonedesign.domain.shop.persistence.Shop;
import io.wisoft.capstonedesign.domain.shop.persistence.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;

    /**
     * 산타샵 물품 저장
     */
    @Transactional
    public Long save(Shop shop) {
        shopRepository.save(shop);
        return shop.getId();
    }

    /**
     * 산타샵 물품 전체 조회
     */
    public List<Shop> findShopList() {
        return shopRepository.findAll();
    }

    /**
     * 단 건 조회
     */
    public Shop findOne(Long id) {
        return shopRepository.findOne(id);
    }

    /**
     * 산타샵 물품 이름으로 조회
     */
    public List<Shop> findShopByTitle(String title) {
        return shopRepository.findByTitle(title);
    }

    /**
     * 산타샵 물품 최근순으로 조회 - 기본값
     */
    public List<Shop> findByCreatedDateDESC() {
        return shopRepository.findByCreatedDateDESC();
    }

    /**
     * 산타샵 물품 정보 수정
     */
    @Transactional
    public void updateAll(Long shopId, String title, int price, String image, String body) {
        Shop shop = findOne(shopId);
        shop.update(title, price, image, body);
    }

    /**
     * 산타샵 물품 삭제
     */
    @Transactional
    public void deleteShop(Long shopId) {
        Shop shop = shopRepository.findOne(shopId);
        shopRepository.delete(shop);
    }
}

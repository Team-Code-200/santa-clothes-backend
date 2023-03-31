package io.wisoft.capstonedesign.domain.shop.application;

import io.wisoft.capstonedesign.domain.shop.persistence.Shop;
import io.wisoft.capstonedesign.domain.shop.persistence.ShopRepository;
import io.wisoft.capstonedesign.domain.shop.web.dto.CreateShopRequest;
import io.wisoft.capstonedesign.domain.shop.web.dto.UpdateShopRequest;
import io.wisoft.capstonedesign.domain.user.persistence.User;
import io.wisoft.capstonedesign.domain.user.persistence.UserRepository;
import io.wisoft.capstonedesign.global.exception.ErrorCode;
import io.wisoft.capstonedesign.global.exception.service.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static io.wisoft.capstonedesign.global.exception.ErrorCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;

    /**
     * 산타샵 물품 저장
     */
    @Transactional
    public Long save(CreateShopRequest request) {

        Shop shop = Shop.createShop(
                request.getTitle(),
                request.getPrice(),
                request.getImage(),
                request.getBody()
        );

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
        return shopRepository.findOne(id)
                .orElseThrow(() -> new PostNotFoundException(NOT_FOUND_POST));
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
    public void updateAll(UpdateShopRequest request) {
        Shop shop = shopRepository.findOne(request.getShopId())
                .orElseThrow(() -> new PostNotFoundException(NOT_FOUND_POST));
        shop.update(request.getTitle(), request.getPrice(), request.getImage(), request.getBody());
    }

    /**
     * 산타샵 물품 삭제
     */
    @Transactional
    public void deleteShop(Long shopId) {
        Shop shop = shopRepository.findOne(shopId)
                .orElseThrow(() -> new PostNotFoundException(NOT_FOUND_POST));
        shopRepository.delete(shop);
    }
}

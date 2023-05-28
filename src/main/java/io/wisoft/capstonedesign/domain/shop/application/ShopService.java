package io.wisoft.capstonedesign.domain.shop.application;

import io.wisoft.capstonedesign.domain.shop.persistence.Shop;
import io.wisoft.capstonedesign.domain.shop.persistence.ShopRepository;
import io.wisoft.capstonedesign.domain.shop.web.dto.CreateShopRequest;
import io.wisoft.capstonedesign.domain.shop.web.dto.UpdateShopRequest;
import io.wisoft.capstonedesign.global.exception.service.PostNotFoundException;
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
public class ShopService {

    private final ShopRepository shopRepository;

    /**
     * 산타샵 물품 저장
     */
    @Transactional
    public Long save(final CreateShopRequest request) {

        Shop shop = Shop.builder()
                .title(request.title())
                .price(request.price())
                .image(request.image())
                .body(request.body())
                .build();

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
    public Shop findById(final Long id) {
        return shopRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(NOT_FOUND_POST));
    }

    /**
     * 산타샵 물품 이름으로 조회
     */
    public List<Shop> findShopByTitle(final String title) {
        return shopRepository.findByTitle(title);
    }

    /**
     * 산타샵 물품 페이징 및 최근순으로 조회 - 기본값
     */
    public Page<Shop> findByCreatedDateDescUsingPaging(final Pageable pageable) {
        return shopRepository.findAllByOrderByCreatedDateDesc(pageable);
    }

    /**
     * 산타샵 물품 정보 수정
     */
    @Transactional
    public void updateAll(final Long id, final UpdateShopRequest request) {
        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(NOT_FOUND_POST));

        shop.update(request.title(), request.price(), request.image(), request.body());
    }

    /**
     * 산타샵 물품 삭제
     */
    @Transactional
    public void deleteShop(final Long shopId) {
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new PostNotFoundException(NOT_FOUND_POST));
        shopRepository.delete(shop);
    }
}

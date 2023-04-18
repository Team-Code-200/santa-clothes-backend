package io.wisoft.capstonedesign.domain.user.application;

import io.wisoft.capstonedesign.domain.donate.persistence.Donate;
import io.wisoft.capstonedesign.domain.donateorder.persistence.DonateOrder;
import io.wisoft.capstonedesign.domain.find.persistence.Find;
import io.wisoft.capstonedesign.domain.findorder.persistence.FindOrder;
import io.wisoft.capstonedesign.domain.information.persistence.Information;
import io.wisoft.capstonedesign.domain.user.persistence.UserMyInfoRepository;
import io.wisoft.capstonedesign.domain.usershop.persistence.UserShop;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserMyInfoService {

    private final UserMyInfoRepository userMyInfoRepository;

    /**
     * 자신이 작성한 나눠줄래요 게시글 목록 조회 - 페이징
     */
    public Page<Donate> findDonatesByIdUsingPaging(final Long userId, final Pageable pageable) {
        return userMyInfoRepository.findDonatesByIdUsingPaging(userId, pageable);
    }

    /**
     * 자신이 작성한 찾아볼래요 게시글 목록 조회 - 페이징
     */
    public Page<Find> findFindsByIdUsingPaging(final Long userId, final Pageable pageable) {
        return userMyInfoRepository.findFindsByIdUsingPaging(userId, pageable);
    }

    /**
     * 자신이 저장한 배송 정보 목록 조회 - 페이징
     */
    public Page<Information> findInfosByIdUsingPaging(final Long userId, final Pageable pageable) {
        return userMyInfoRepository.findInfosByIdUsingPaging(userId, pageable);
    }

    /**
     * 자신이 기부한 나눠줄래요 게시글 거래 내역 목록 조회 - 페이징
     */
    public Page<DonateOrder> findDonateOrdersByIdUsingPaging(final Long userId, final Pageable pageable) {
        return userMyInfoRepository.findDonateOrdersByIdUsingPaging(userId, pageable);
    }

    /**
     * 자신이 기부받는 찾아볼래요 게시글 거래 내역 목록 조회
     */
    public Page<FindOrder> findFindOrdersByIdUsingPaging(final Long userId, final Pageable pageable) {
        return userMyInfoRepository.findFindOrdersByIdUsingPaging(userId, pageable);
    }

    /**
     * 자신이 주문한 산타샵 주문 내역 목록 조회
     */
    public Page<UserShop> findShopOrdersByIdUsingPaging(final Long userId, final Pageable pageable) {
        return userMyInfoRepository.findShopOrdersByIdUsingPaging(userId, pageable);
    }
}

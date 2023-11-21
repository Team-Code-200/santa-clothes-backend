package io.wisoft.capstonedesign.domain.information.application;

import io.wisoft.capstonedesign.domain.address.application.AddressService;
import io.wisoft.capstonedesign.domain.address.persistence.Address;
import io.wisoft.capstonedesign.domain.information.persistence.Information;
import io.wisoft.capstonedesign.domain.information.persistence.InformationRepository;
import io.wisoft.capstonedesign.domain.information.web.dto.CreateInformationRequest;
import io.wisoft.capstonedesign.domain.information.web.dto.UpdateInformationRequest;
import io.wisoft.capstonedesign.domain.user.persistence.User;
import io.wisoft.capstonedesign.domain.user.persistence.UserRepository;
import io.wisoft.capstonedesign.global.exception.service.InfoNotFoundException;
import io.wisoft.capstonedesign.global.exception.service.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InformationService {

    private final InformationRepository informationRepository;
    private final UserRepository userRepository;
    private final AddressService addressService;

    /**
     * 배송 정보 저장
     */
    @Transactional
    public Long save(final CreateInformationRequest request) {

        User user = userRepository.findById(request.userId())
                .orElseThrow(UserNotFoundException::new);

        Information information = Information.builder()
                .username(request.username())
                .address(request.address())
                .phoneNumber(request.phoneNumber())
                .user(user)
                .build();


        informationRepository.save(information);
        return information.getId();
    }

    /**
     * 배송 정보 전체 조회
     */
    public List<Information> findInformations() {
        return informationRepository.findAll();
    }

    /**
     * 모든 사용자의 배송정보 페이징 및 최근순으로 조회 - 기본값
     */
    public Page<Information> findByCreatedDateDescUsingPaging(final Pageable pageable) {
        return informationRepository.findAllByOrderByCreatedDateDesc(pageable);
    }

    /**
     * 단 건 조회
     */
    public Information findById(final Long id) {
        return informationRepository.findById(id)
                .orElseThrow(InfoNotFoundException::new);
    }

    /**
     * 특정 사용자의 배송 정보 조회
     */
    public List<Information> findByUser(final Long userId) {

        userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return informationRepository.findByUser(userId);
    }

    /**
     * 배송 정보 수정
     */
    @Transactional
    public void updateAll(final Long id, final UpdateInformationRequest request) {
        Information information = informationRepository.findById(id)
                .orElseThrow(InfoNotFoundException::new);

        validateInformation(request.username(), request.address(), request.phoneNumber());
        information.update(request.username(), request.address(), request.phoneNumber());
    }

    private void validateInformation(final String username, final String address, final String phoneNumber) {
        if (username == null || address == null || phoneNumber == null) {
            throw new IllegalStateException("배송 정보를 모두 입력해주세요.");
        }
    }

    /**
     * 배송 정보 삭제
     */
    @Transactional
    public void deleteInformation(final Long informationId) {
        Information information = informationRepository.findById(informationId)
                .orElseThrow(InfoNotFoundException::new);
        informationRepository.delete(information);
    }

    @Transactional
    public String translateAddressToString(final Address address) {
        return String.join("", address.getDetailAddress(), address.getExtraAddress(), address.getPostAddress(), address.getPostcode());
    }

}

package io.wisoft.capstonedesign.domain.information.application;

import io.wisoft.capstonedesign.domain.information.persistence.Information;
import io.wisoft.capstonedesign.domain.information.web.dto.CreateInformationRequest;
import io.wisoft.capstonedesign.domain.information.web.dto.UpdateInformationRequest;
import io.wisoft.capstonedesign.domain.user.persistence.User;
import io.wisoft.capstonedesign.domain.information.persistence.InformationRepository;
import io.wisoft.capstonedesign.domain.user.persistence.UserRepository;
import io.wisoft.capstonedesign.global.exception.service.InfoNotFoundException;
import io.wisoft.capstonedesign.global.exception.service.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static io.wisoft.capstonedesign.global.exception.ErrorCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InformationService {

    private final InformationRepository informationRepository;
    private final UserRepository userRepository;

    /**
     * 배송 정보 저장
     */
    @Transactional
    public Long save(final CreateInformationRequest request) {

        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new UserNotFoundException(DUPLICATE_USER));

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
     * 단 건 조회
     */
    public Information findById(final Long id) {
        return informationRepository.findById(id)
                .orElseThrow(() -> new InfoNotFoundException(NOT_FOUND_INFO));
    }

    /**
     * 특정 사용자의 배송 정보 조회
     */
    public List<Information> findByUser(final Long userId) {
        return informationRepository.findByUser(userId);
    }

    /**
     * 배송 정보 수정
     */
    @Transactional
    public void updateAll(final Long id, final UpdateInformationRequest request) {
        Information information = informationRepository.findById(id)
                .orElseThrow(() -> new InfoNotFoundException(NOT_FOUND_INFO));

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
                .orElseThrow(() -> new InfoNotFoundException(NOT_FOUND_INFO));
        informationRepository.delete(information);
    }
}

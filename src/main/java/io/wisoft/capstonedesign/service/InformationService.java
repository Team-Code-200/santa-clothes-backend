package io.wisoft.capstonedesign.service;

import io.wisoft.capstonedesign.domain.Information;
import io.wisoft.capstonedesign.domain.User;
import io.wisoft.capstonedesign.repository.InformationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InformationService {

    private final InformationRepository informationRepository;

    /**
     * 배송 정보 저장
     */
    @Transactional
    public Long save(Information information) {
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
    public Information findOne(Long id) {
        return informationRepository.findOne(id);
    }

    /**
     * 회원별 배송 정보 조회
     */
    public List<Information> findInformationByUser(User user) {
        return informationRepository.findByUser(user);
    }

    /**
     * 배송 정보 수정
     */
    @Transactional
    public void updateAll(Long informationId, String username, String address, String phoneNumber) {
        Information information = findOne(informationId);
        validateInformation(username, address, phoneNumber);
        information.update(username, address, phoneNumber);
    }

    private void validateInformation(String username, String address, String phoneNumber) {
        if (username == null || address == null || phoneNumber == null) {
            throw new IllegalStateException("배송 정보를 모두 입력해주세요.");
        }
    }

    /**
     * 배송 정보 삭제
     */
    @Transactional
    public void deleteInformation(Long informationId) {
        Information information = informationRepository.findOne(informationId);
        informationRepository.delete(information);
    }
}

package io.wisoft.capstonedesign.service;

import io.wisoft.capstonedesign.domain.Donate;
import io.wisoft.capstonedesign.domain.Tag;
import io.wisoft.capstonedesign.repository.DonateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DonateService {

    private final DonateRepository donateRepository;

    /**
     * 게시글 저장
     */
    @Transactional
    public Long join(Donate donate) {
        donateRepository.save(donate);
        return donate.getId();
    }

    /**
     * 게시글 전체 조회
     */
    public List<Donate> findDonates() {
        return donateRepository.findAll();
    }

    /**
     * 단 건 조회
     */
    public Donate findOne(Long id) {
        return donateRepository.findOne(id);
    }

    /**
     * 모든 게시글 최근순으로 조회 - 기본값
     */
    public List<Donate> findByCreatedDateDESC() {
        return donateRepository.findByCreatedDateDESC();
    }

    /**
     * 모든 게시글 태그 종류별 조회
     */
    public List<Donate> findByTag(Tag tag) {
        return donateRepository.findByTag(tag);
    }

    /**
     * 게시글 제목, 본문 및 태그 수정
     */
    @Transactional
    public void updateAll(Long donateId, String title, String image, String text, Tag tag) {
        Donate donate = findOne(donateId);
        validateDonate(title, image, text, tag);
        donate.update(title, image, text, tag);
    }

    private void validateDonate(String title, String image, String text, Tag tag) {
        if (title == null || image == null || text == null || tag == null) {
            throw new IllegalStateException("게시글을 모두 작성해주세요.");
        }
    }

    /**
     * 게시글 삭제
     */
    @Transactional
    public void deleteDonate(Long donateId) {
        Donate donate = donateRepository.findOne(donateId);
        donateRepository.delete(donate);
    }
}

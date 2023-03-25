package io.wisoft.capstonedesign.domain.donate.application;

import io.wisoft.capstonedesign.domain.donate.persistence.Donate;
import io.wisoft.capstonedesign.domain.donate.web.dto.CreateDonateRequest;
import io.wisoft.capstonedesign.domain.donate.web.dto.UpdateDonateRequest;
import io.wisoft.capstonedesign.domain.user.persistence.User;
import io.wisoft.capstonedesign.domain.user.persistence.UserRepository;
import io.wisoft.capstonedesign.global.enumerated.Tag;
import io.wisoft.capstonedesign.domain.donate.persistence.DonateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DonateService {

    private final DonateRepository donateRepository;
    private final UserRepository userRepository;

    /**
     * 게시글 저장
     */
    @Transactional
    public Long join(CreateDonateRequest request) {

        User user = userRepository.findOne(request.getUserId());
        Donate donate = Donate.createDonate(
                request.getTitle(),
                request.getImage(),
                request.getText(),
                request.getView(),
                Tag.valueOf(request.getTag()),
                user
        );

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
    public void updateAll(UpdateDonateRequest request) {
        Donate donate = findOne(request.getDonateId());
        validateDonate(request.getTitle(), request.getImage(), request.getText(), Tag.valueOf(request.getTag()));
        donate.update(request.getTitle(), request.getImage(), request.getText(), Tag.valueOf(request.getTag()));
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

package io.wisoft.capstonedesign.domain.donate.application;

import io.wisoft.capstonedesign.domain.donate.persistence.Donate;
import io.wisoft.capstonedesign.domain.donate.web.dto.CreateDonateRequest;
import io.wisoft.capstonedesign.domain.donate.web.dto.UpdateDonateRequest;
import io.wisoft.capstonedesign.domain.user.persistence.User;
import io.wisoft.capstonedesign.domain.user.persistence.UserRepository;
import io.wisoft.capstonedesign.global.enumerated.Tag;
import io.wisoft.capstonedesign.domain.donate.persistence.DonateRepository;
import io.wisoft.capstonedesign.global.exception.ErrorCode;
import io.wisoft.capstonedesign.global.exception.service.PostNotFoundException;
import io.wisoft.capstonedesign.global.exception.service.UnAuthorizedAccessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static io.wisoft.capstonedesign.global.exception.ErrorCode.*;

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
    public Long join(final CreateDonateRequest request) {

        User user = userRepository.findOne(request.getUserId())
                .orElseThrow(() -> new PostNotFoundException(NOT_FOUND_POST));
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
    public Donate findOne(final Long id) {
        return donateRepository.findOne(id)
                .orElseThrow(() -> new PostNotFoundException(NOT_FOUND_POST));
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
    public List<Donate> findByTag(final Tag tag) {
        return donateRepository.findByTag(tag);
    }

    /**
     * 게시글 제목, 본문 및 태그 수정
     */
    @Transactional
    public void updateAll(final UpdateDonateRequest request) {
        Donate donate = findOne(request.getDonateId());
        validateDonate(request.getTitle(), request.getImage(), request.getText(), Tag.valueOf(request.getTag()));
        donate.update(request.getTitle(), request.getImage(), request.getText(), Tag.valueOf(request.getTag()));
    }

    private void validateDonate(final String title, final String image, final String text, final Tag tag) {
        if (title == null || image == null || text == null || tag == null) {
            throw new IllegalStateException("게시글을 모두 작성해주세요.");
        }
    }

    /**
     * 게시글 삭제
     */
    @Transactional
    public void deleteDonate(final Long donateId) {
        Donate donate = donateRepository.findOne(donateId)
                .orElseThrow(() -> new PostNotFoundException(NOT_FOUND_POST));
        donateRepository.delete(donate);
    }
}

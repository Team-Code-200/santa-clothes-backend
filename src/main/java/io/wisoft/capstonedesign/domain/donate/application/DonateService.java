package io.wisoft.capstonedesign.domain.donate.application;

import io.wisoft.capstonedesign.domain.donate.persistence.Donate;
import io.wisoft.capstonedesign.domain.donate.persistence.DonateRepository;
import io.wisoft.capstonedesign.domain.donate.web.dto.CreateDonateRequest;
import io.wisoft.capstonedesign.domain.donate.web.dto.UpdateDonateRequest;
import io.wisoft.capstonedesign.domain.user.persistence.User;
import io.wisoft.capstonedesign.domain.user.persistence.UserRepository;
import io.wisoft.capstonedesign.global.enumerated.Tag;
import io.wisoft.capstonedesign.global.exception.service.PostNotFoundException;
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
public class DonateService {

    private final DonateRepository donateRepository;
    private final UserRepository userRepository;

    /**
     * 게시글 저장
     */
    @Transactional
    public Long join(final CreateDonateRequest request) {

        final User user = userRepository.findById(request.userId())
                .orElseThrow(UserNotFoundException::new);

        final Donate donate = Donate.builder()
                .title(request.title())
                .image(request.image())
                .text(request.text())
                .view(0)
                .tag(Tag.valueOf(request.tag()))
                .user(user)
                .build();

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
    public Donate findById(final Long id) {
        return donateRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);
    }

    /**
     * 모든 게시글 페이징 및 최근순으로 조회 - 기본값
     */
    public Page<Donate> findByCreatedDateDescUsingPaging(final Pageable pageable) {
        return donateRepository.findAllByOrderByCreatedDateDesc(pageable);
    }

    /**
     * 특정 사용자의 게시글 조회
     */
    public List<Donate> findByUser(final Long userId) {

        userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return donateRepository.findByUser(userId);
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
    public void updateAll(final Long id, final UpdateDonateRequest request) {
        Donate donate = donateRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);

        validateDonate(request.title(), request.image(), request.text(), Tag.valueOf(request.tag()));
        donate.update(request.title(), request.image(), request.text(), Tag.valueOf(request.tag()));
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
        Donate donate = donateRepository.findById(donateId)
                .orElseThrow(PostNotFoundException::new);

        donateRepository.delete(donate);
    }
}

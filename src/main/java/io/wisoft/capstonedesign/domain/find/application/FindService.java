package io.wisoft.capstonedesign.domain.find.application;

import io.wisoft.capstonedesign.domain.find.persistence.FindRepository;
import io.wisoft.capstonedesign.domain.find.persistence.Find;
import io.wisoft.capstonedesign.domain.find.web.dto.CreateFindRequest;
import io.wisoft.capstonedesign.domain.find.web.dto.UpdateFindRequest;
import io.wisoft.capstonedesign.domain.user.persistence.User;
import io.wisoft.capstonedesign.domain.user.persistence.UserRepository;
import io.wisoft.capstonedesign.global.enumerated.Tag;
import io.wisoft.capstonedesign.global.exception.service.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static io.wisoft.capstonedesign.global.exception.ErrorCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FindService {

    private final FindRepository findRepository;
    private final UserRepository userRepository;

    /**
     * 게시글 저장
     */
    @Transactional
    public Long join(final CreateFindRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new PostNotFoundException(NOT_FOUND_POST));
        Find find = Find.createFind(
                request.getTitle(),
                request.getImage(),
                request.getText(),
                request.getView(),
                Tag.valueOf(request.getTag()),
                user
        );

        findRepository.save(find);
        return find.getId();
    }

    /**
     * 게시글 전체 조회
     */
    public List<Find> findFinds() {
        return findRepository.findAll();
    }

    /**
     * 단 건 조회
     */
    public Find findById(final Long id) {
        return findRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(NOT_FOUND_POST));
    }

    /**
     * 모든 게시글 최근순으로 조회 - 기본값
     */
    public List<Find> findByCreatedDateDESC() {
        return findRepository.findAllByOrderByCreatedDateDesc();
    }

    /**
     * 모든 게시글 태그 종류별 조회
     */
    public List<Find> findByTag(final Tag tag) {
        return findRepository.findByTag(tag);
    }

    /**
     * 게시글 제목, 본문 및 태그 수정
     */
    @Transactional
    public void updateAll(final UpdateFindRequest request) {
        Find find = findRepository.findById(request.getFindId())
                .orElseThrow(() -> new PostNotFoundException(NOT_FOUND_POST));
        validateFind(request.getTitle(), request.getImage(), request.getText(), Tag.valueOf(request.getTag()));
        find.update(request.getTitle(), request.getImage(), request.getText(), Tag.valueOf(request.getTag()));
    }

    private void validateFind(final String title, final String image, final String text, final Tag tag) {
        if (title == null || image == null || text == null || tag == null) {
            throw new IllegalStateException("게시글을 모두 작성해주세요.");
        }
    }

    /**
     * 게시글 삭제
     */
    @Transactional
    public void deleteFind(final Long findId) {
        Find find = findRepository.findById(findId)
                .orElseThrow(() -> new PostNotFoundException(NOT_FOUND_POST));
        findRepository.delete(find);
    }
}

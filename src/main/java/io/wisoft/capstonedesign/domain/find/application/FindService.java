package io.wisoft.capstonedesign.domain.find.application;

import io.wisoft.capstonedesign.domain.find.persistence.FindRepository;
import io.wisoft.capstonedesign.domain.find.persistence.Find;
import io.wisoft.capstonedesign.domain.find.web.dto.CreateFindRequest;
import io.wisoft.capstonedesign.domain.find.web.dto.UpdateFindRequest;
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

        User user = userRepository.findById(request.userId())
                .orElseThrow(UserNotFoundException::new);

        Find find = Find.builder()
                .title(request.title())
                .image(request.image())
                .text(request.text())
                .view(0)
                .tag(Tag.valueOf(request.tag()))
                .user(user)
                .build();

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
                .orElseThrow(PostNotFoundException::new);
    }

    /**
     * 모든 게시글 페이징 및 최근순으로 조회 - 기본값
     */
    public Page<Find> findByCreatedDateDescUsingPaging(final Pageable pageable) {
        return findRepository.findAllByOrderByCreatedDateDesc(pageable);
    }

    /**
     * 특정 사용자의 게시글 조회
     */
    public List<Find> findByUser(final Long userId) {
        return findRepository.findByUser(userId);
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
    public void updateAll(final Long id, final UpdateFindRequest request) {
        Find find = findRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);

        validateFind(request.title(), request.image(), request.text(), Tag.valueOf(request.tag()));
        find.update(request.title(), request.image(), request.text(), Tag.valueOf(request.tag()));
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
                .orElseThrow(PostNotFoundException::new);

        findRepository.delete(find);
    }
}

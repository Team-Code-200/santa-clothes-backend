package io.wisoft.capstonedesign.domain.find.application;

import io.wisoft.capstonedesign.domain.find.persistence.FindRepository;
import io.wisoft.capstonedesign.domain.find.persistence.Find;
import io.wisoft.capstonedesign.global.enumerated.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FindService {

    private final FindRepository findRepository;

    /**
     * 게시글 저장
     */
    @Transactional
    public Long join(Find find) {
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
    public Find findOne(Long id) {
        return findRepository.findOne(id);
    }

    /**
     * 모든 게시글 최근순으로 조회 - 기본값
     */
    public List<Find> findByCreatedDateDESC() {
        return findRepository.findByCreatedDateDESC();
    }

    /**
     * 모든 게시글 태그 종류별 조회
     */
    public List<Find> findByTag(Tag tag) {
        return findRepository.findByTag(tag);
    }

    /**
     * 게시글 제목, 본문 및 태그 수정
     */
    @Transactional
    public void updateAll(Long findId, String title, String image, String text, Tag tag) {
        Find find = findOne(findId);
        validateFind(title, image, text, tag);
        find.update(title, image, text, tag);
    }

    private void validateFind(String title, String image, String text, Tag tag) {
        if (title == null || image == null || text == null || tag == null) {
            throw new IllegalStateException("게시글을 모두 작성해주세요.");
        }
    }

    /**
     * 게시글 삭제
     */
    @Transactional
    public void deleteFind(Long findId) {
        Find find = findRepository.findOne(findId);
        findRepository.delete(find);
    }
}

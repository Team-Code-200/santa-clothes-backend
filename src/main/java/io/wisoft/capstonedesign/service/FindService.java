package io.wisoft.capstonedesign.service;

import io.wisoft.capstonedesign.domain.Find;
import io.wisoft.capstonedesign.repository.FindRepository;
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
}

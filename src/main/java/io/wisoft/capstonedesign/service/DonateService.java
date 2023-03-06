package io.wisoft.capstonedesign.service;

import io.wisoft.capstonedesign.domain.Donate;
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
}

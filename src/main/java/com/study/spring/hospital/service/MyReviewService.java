package com.study.spring.hospital.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.study.spring.hospital.entity.H_review;
import com.study.spring.hospital.repository.MyReviewLikeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyReviewService {
    private final MyReviewLikeRepository repo;

    // reviewId 기준으로 리뷰 조회
    public List<H_review> getMyReviews(Integer reviewId) {
        log.info("Searching for review with ID: {}", reviewId);
        try {
            // 먼저 Native Query로 시도 (timestamp 제외)
            List<Object[]> rawResults = repo.findMyReviewsByReviewIdRaw(reviewId);
            if (!rawResults.isEmpty()) {
                List<H_review> reviews = new ArrayList<>();
                for (Object[] row : rawResults) {
                    H_review review = H_review.builder()
                            .r_id((Integer) row[0])
                            .r_title((String) row[4])
                            .r_content((String) row[5])
                            .r_eval_pt((Integer) row[6])
                            .r_views((Integer) row[7])
                            .r_del_yn((String) row[8])
                            .build();
                    reviews.add(review);
                }
                log.info("Found {} reviews for ID: {} (using raw query)", reviews.size(), reviewId);
                return reviews;
            }
            // Native Query로 결과가 없으면 JPQL 시도
            List<H_review> result = repo.findMyReviewsByReviewId(reviewId);
            log.info("Found {} reviews for ID: {}", result.size(), reviewId);
            return result;
        } catch (DataIntegrityViolationException e) {
            log.error("Invalid timestamp data found for reviewId: {}. Error: {}", reviewId, e.getMessage(), e);
            // 예외 발생 시 Native Query로 재시도
            try {
                List<Object[]> rawResults = repo.findMyReviewsByReviewIdRaw(reviewId);
                List<H_review> reviews = new ArrayList<>();
                for (Object[] row : rawResults) {
                    H_review review = H_review.builder()
                            .r_id((Integer) row[0])
                            .r_title((String) row[4])
                            .r_content((String) row[5])
                            .r_eval_pt((Integer) row[6])
                            .r_views((Integer) row[7])
                            .r_del_yn((String) row[8])
                            .build();
                    reviews.add(review);
                }
                return reviews;
            } catch (Exception ex) {
                log.error("Error in fallback query: {}", ex.getMessage());
                return new ArrayList<>();
            }
        } catch (Exception e) {
            log.error("Error occurred while fetching review with ID: {}. Error: {}", reviewId, e.getMessage(), e);
            return new ArrayList<>();
        }
    }
}

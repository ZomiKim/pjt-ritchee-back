package com.study.spring.hospital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.study.spring.hospital.dto.MyReviewListDto;
import com.study.spring.hospital.entity.H_review;

@Repository
public interface MyReviewRepository extends JpaRepository<H_review, Integer> {

    @Query("""
        select new com.study.spring.hospital.dto.MyReviewListDto(
            hr.hospital.h_code,
            hr.hospital.h_name,
            hr.r_title,
            hr.r_content,
            hr.r_eval_pt,
            hr.r_views,
            hr.r_del_yn,
            hr.createdAt
        )
        from H_review hr
    """)
    List<MyReviewListDto> findByMyReviewList();
}

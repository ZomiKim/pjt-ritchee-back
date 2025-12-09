package com.study.spring.hospital.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.study.spring.hospital.dto.MyReviewListDto;
import com.study.spring.hospital.entity.H_review;
import com.study.spring.hospital.repository.MyReviewLikeRepository;
import com.study.spring.hospital.repository.MyReviewRepository;
import com.study.spring.hospital.service.MyReviewService;

@RestController
public class ReviewController {

	@Autowired
	MyReviewRepository myRLRepo;

	@Autowired
	MyReviewLikeRepository MyReviewLikeRepo;

	@Autowired
	MyReviewService myReviewService;

	@GetMapping("/api/myreviewlist")
	public List<MyReviewListDto> getMyLivew() {
		return myRLRepo.findByMyReviewList();
	}

	@GetMapping("/api/myreviewlist/{id}")
	public List<H_review> getMyRL(@PathVariable("id") Integer id) {
		return myReviewService.getMyReviews(id);
	}
}

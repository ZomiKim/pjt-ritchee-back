package com.study.spring.hospital.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.spring.hospital.dto.H_ReviewListDto;
import com.study.spring.hospital.dto.MyReviewListDto;
import com.study.spring.hospital.repository.MyReviewRepository;

@RestController
public class ReviewController {
	
	@Autowired
	MyReviewRepository myRLRepo;
	

	@GetMapping("/api/myreviewlist")
	public List<MyReviewListDto> getMyLivew (){
		return myRLRepo.findByMyReviewList();
	}
	
}

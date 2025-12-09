package com.study.spring.hospital.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.management.RuntimeErrorException;

import com.study.spring.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.study.spring.HospitalApplication;
import com.study.spring.config.WebCorsConfig;
import com.study.spring.hospital.dto.AppointmentDto;
import com.study.spring.hospital.dto.CommentDto;
import com.study.spring.hospital.dto.H_AppmListDto;
import com.study.spring.hospital.dto.H_AppmUserDto;
import com.study.spring.hospital.dto.H_CommentUserDto;
import com.study.spring.hospital.dto.H_LikeUserDto;
import com.study.spring.hospital.dto.H_ReviewAppmDto;
import com.study.spring.hospital.dto.H_ReviewCommentDto;
import com.study.spring.hospital.dto.H_ReviewLikeDto;
import com.study.spring.hospital.dto.HospitalDto;
import com.study.spring.hospital.dto.LikeDto;
import com.study.spring.hospital.dto.ReservationDto;
import com.study.spring.hospital.dto.H_ReviewListDto;
import com.study.spring.hospital.dto.H_ReviewUserDto;
import com.study.spring.hospital.dto.ReviewDto;
import com.study.spring.hospital.entity.H_appm;
import com.study.spring.hospital.entity.H_review;
import com.study.spring.hospital.entity.Hospital;
import com.study.spring.hospital.repository.HospitalAppmRepository;
import com.study.spring.hospital.repository.HospitalRepository;
import com.study.spring.hospital.service.HospitalService;
import com.study.spring.user.entity.User;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class HospitalController {
	@Autowired
	HospitalService hService;

	@GetMapping("/")
	public String root() {
		return "hi";
	}
	
	@GetMapping("/api/hospital")
	public List<HospitalDto> getHospital() {
		return hService.findAllHospitalByIdDesc();
	}
	
	@GetMapping("/api/reviewedAppm")
	public List<H_ReviewAppmDto> getReviewWithAppm() {
		return hService.findAllReviewByIdDesc();
	}
	
	@GetMapping("/api/review")
	public List<H_ReviewListDto> getReviewList() {
		return hService.findWithReivew();
	}
	
	
	@GetMapping("/api/review/{h_code}")
	public H_ReviewListDto getOneOfHospitalReviewList(
	        @PathVariable("h_code") String h_code) {

	    return hService.findWithReviews(h_code);
	}

	
	@GetMapping("/api/reviewUser")
	public List<H_ReviewUserDto> getReviewWithUserList() {
		return hService.findReviewWithUser();
	}
	
	@GetMapping("/api/comment")
	public List<H_ReviewCommentDto> getCommentList() {
		return hService.findWithComment();
	}
	
	@GetMapping("/api/commentUser")
	public List<H_CommentUserDto> getCommentWithUserList() {
		return hService.findCommentWithUser();
	}
	
	@GetMapping("/api/appm")
	public List<H_AppmListDto> getAppmList() {
		return hService.findWithAppm();
	}
	
	@PostMapping("/api/appm")
	public ResponseEntity<String> appmCreate(@RequestBody ReservationDto req) {
		try {
			hService.appmCreate(req);
			return ResponseEntity.ok("SUCCESS");	
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("FAILURE: " + e.getMessage());
		}

	}

	
//	예약 개별 조회
	@GetMapping("/api/appmUser/{userId}")
	public H_AppmUserDto getAppmWithUser(@PathVariable("userId") UUID userId) {
	    return hService.findAppmWithUserById(userId);
	}
	
	@GetMapping("/api/like")
	public List<H_ReviewLikeDto> getLikeList() {
		return hService.findWithLike();
	}
	
	@GetMapping("/api/likeUser")
	public List<H_LikeUserDto> getLikeWithUserList() {
		return hService.findLikeWithUser();
	}
	
	
}

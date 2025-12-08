package com.study.spring.hospital.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.study.spring.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.study.spring.hospital.repository.HospitalRepository;
import com.study.spring.user.entity.User;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class HospitalController {

    private final WebCorsConfig webCorsConfig;
	@Autowired
	HospitalRepository hRepo;

    HospitalController(WebCorsConfig webCorsConfig) {
        this.webCorsConfig = webCorsConfig;
    }

	@GetMapping("/")
	public String root() {
		return "hi";
	}
	
	@GetMapping("/api/hospital")
	public List<HospitalDto> getHospital() {
		return hRepo.findAllHospitalByIdDesc();
	}
	
	@GetMapping("/api/reviewedAppm")
	public List<H_ReviewAppmDto> getReviewWithAppm() {
		return hRepo.findAllReviewByIdDesc();
	}
	
	@GetMapping("/api/review")
	public List<H_ReviewListDto> getReviewList() {
		List<Hospital> hospitals = hRepo.findWithReview();
		
		return hospitals.stream()
				.map(h->H_ReviewListDto
						.builder()
						.h_code(h.getH_code())
						.h_name(h.getH_name())
						.createdAt(h.getCreatedAt())
						.reviewCount(h.getReviews().size())
						.reviews(h.getReviews().stream()
								.map(review-> new ReviewDto(
										review.getR_id(), 
										review.getR_title(), 
										review.getR_content(), 
										review.getR_eval_pt(), 
										review.getR_views()))
								.toList())
						.build())
				.toList();
	}
	
	@GetMapping("/api/review/{h_code}")
	public H_ReviewListDto getOneOfHospitalReviewList(
	        @PathVariable("h_code") String h_code) {

	    Hospital h = hRepo.findWithReviews(h_code);

	    return H_ReviewListDto.builder()
	            .h_code(h.getH_code())
	            .h_name(h.getH_name())
	            .createdAt(h.getCreatedAt())
	            .reviewCount(h.getReviews().size())
	            .reviews(
	                    h.getReviews().stream()
	                            .map(review -> new ReviewDto(
	                                    review.getR_id(),
	                                    review.getR_title(),
	                                    review.getR_content(),
	                                    review.getR_eval_pt(),
	                                    review.getR_views()
	                            ))
	                            .toList()   // Java 16+
	            )
	            .build();
	}

	
	@GetMapping("/api/reviewUser")
	public List<H_ReviewUserDto> getReviewWithUserList() {
		List<User> users = hRepo.findReviewWithUser();
		return users.stream()
				.map(u -> H_ReviewUserDto
						.builder()
						.id(u.getId())
						.u_kind(u.getU_kind())
						.name(u.getName())
						.gender(u.getGender())
						.phone(u.getPhone())
						.addr(u.getAddr())
						.birth(u.getBirth())
						.text(u.getText())
						.createdAt(u.getCreatedAt())
						.reviews(u.getReviews().stream()
								.map(review -> new ReviewDto(
										review.getR_id(), 
										review.getR_title(), 
										review.getR_content(), 
										review.getR_eval_pt(), 
										review.getR_views()))
								.toList())
						.build())
				.toList();
	}
	
	@GetMapping("/api/comment")
	public List<H_ReviewCommentDto> getCommentList() {
		List<H_review> reviews = hRepo.findWithComment();
		return reviews.stream()
				.map(r-> H_ReviewCommentDto
						.builder()
						.r_id(r.getR_id())
						.r_title(r.getR_title())
						.r_content(r.getR_content())
						.r_eval_pt(r.getR_eval_pt())
						.r_views(r.getR_views())
						.createdAt(r.getCreatedAt())
						.commentCount(r.getComments().size()) // getComments는 엔티티에서 가져오는 네임
						.comments(r.getComments().stream() // .comments는 dto에서 가져오는 네임
								.map(comment -> new CommentDto(
										comment.getC_id(), 
										comment.getC_content()))
								.toList())
						.build())
				.toList();
	}
	
	@GetMapping("/api/commentUser")
	public List<H_CommentUserDto> getCommentWithUserList() {
		List<User> users = hRepo.findCommentWithUser();
		return users.stream()
				.map(u -> H_CommentUserDto
						.builder()
						.id(u.getId())
						.u_kind(u.getU_kind())
						.name(u.getName())
						.gender(u.getGender())
						.phone(u.getPhone())
						.addr(u.getAddr())
						.birth(u.getBirth())
						.text(u.getText())
						.createdAt(u.getCreatedAt())
						.comments(u.getComments().stream()
								.map(comment -> new CommentDto(comment.getC_id(), comment.getC_content()))
								.toList())
						.build())
				.toList();
	}
	
	@GetMapping("/api/appm")
	public List<H_AppmListDto> getAppmList() {
		List<Hospital> hospitals = hRepo.findWithAppm();
		return hospitals.stream()
				.map(h-> H_AppmListDto
						.builder()
						.h_code(h.getH_code())
						.h_name(h.getH_name())
						.createdAt(h.getCreatedAt())
						.appmCount(h.getAppms().size())
						.appms(h.getAppms().stream()
								.map(appm-> new AppointmentDto(
										appm.getA_id(),
										appm.getA_date(),
										appm.getA_content(),
										appm.getA_dia_name(),
										appm.getA_dia_content()))
								.toList())
						.build())
				.toList();
	}
	
//	@PostMapping("/api/appm")
//	public H_appm appmCreate(@RequestBody ReservationDto req) {
//		H_appm appm = H_appm.builder()
//				.a_id(appm.getA_id())
//				.a_date(appm.getA_date())
//				.a_content(appm.getA_content())
//				.a_del_yn(appm.getA_del_yn())
//				.build();
//		return hRepo.save(appm);
//	}
	
	@PostMapping("/api/appm")
	public ResponseEntity<String> appmCreate(@ModelAttribute ReservationDto req) {
		System.out.println(req);
		System.out.println((String)req.a_date.getClass().getSimpleName());
//		LocalDate date = LocalDate.parse(req.a_date);
		
//		H_appm appm = H_appm.builder()
//				.a_date(req.a_date)
//				.a_content(req.a_content)
//				.build();
		
		return ResponseEntity.ok(("SUCCESS	"));
		
	}
	
//	예약 개별 조회
	@GetMapping("/api/appmUser/{userId}")
	public H_AppmUserDto getAppmWithUser(@PathVariable("userId") UUID userId) {
	    // 유저 + 예약 정보 포함 조회 (UserRepository에 해당 메소드가 있어야 함)
	    User user = hRepo.findAppmWithUserById(userId);


	    return H_AppmUserDto.builder()
	            .id(user.getId())
	            .u_kind(user.getU_kind())
	            .name(user.getName())
	            .gender(user.getGender())
	            .phone(user.getPhone())
	            .addr(user.getAddr())
	            .birth(user.getBirth())
	            .text(user.getText())
	            .createdAt(user.getCreatedAt())
	            .appms(user.getAppms().stream()
	                    .map(appm -> new AppointmentDto(
	                            appm.getA_id(),
	                            appm.getA_date(),
	                            appm.getA_content(),
	                            appm.getA_dia_name(),
	                            appm.getA_dia_content()))
	                    .toList())
	            .build();
	}
	
	@GetMapping("/api/like")
	public List<H_ReviewLikeDto> getLikeList() {
		List<H_review> reviews = hRepo.findWithLike();
		return reviews.stream()
				.map(r -> H_ReviewLikeDto
						.builder()
						.r_id(r.getR_id())
						.createdAt(r.getCreatedAt())
						.likeCount(r.getLikes().size())
						.likes(r.getLikes().stream()
								.map(like -> new LikeDto(like.getL_id()))
								.toList())
						.build())
				.toList();
	}
	
	@GetMapping("/api/likeUser")
	public List<H_LikeUserDto> getLikeWithUserList() {
		List<User> users = hRepo.findLikeWithUser();
		return users.stream()
				.map(u -> H_LikeUserDto
						.builder()
						.id(u.getId())
						.u_kind(u.getU_kind())
						.name(u.getName())
						.gender(u.getGender())
						.phone(u.getPhone())
						.addr(u.getAddr())
						.birth(u.getBirth())
						.text(u.getText())
						.createdAt(u.getCreatedAt())
						.likes(u.getLikes().stream()
								.map(like -> new LikeDto(like.getL_id()))
								.toList())
						.build())
				.toList();
	}
}

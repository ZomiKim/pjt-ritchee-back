package com.study.spring.hospital.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.spring.hospital.dto.AppointmentDto;
import com.study.spring.hospital.dto.CommentDto;
import com.study.spring.hospital.dto.H_AppmListDto;
import com.study.spring.hospital.dto.H_AppmUserDto;
import com.study.spring.hospital.dto.H_CommentUserDto;
import com.study.spring.hospital.dto.H_LikeUserDto;
import com.study.spring.hospital.dto.H_ReviewAppmDto;
import com.study.spring.hospital.dto.H_ReviewCommentDto;
import com.study.spring.hospital.dto.H_ReviewLikeDto;
import com.study.spring.hospital.dto.H_ReviewListDto;
import com.study.spring.hospital.dto.H_ReviewUserDto;
import com.study.spring.hospital.dto.HospitalDto;
import com.study.spring.hospital.dto.LikeDto;
import com.study.spring.hospital.dto.ReservationDto;
import com.study.spring.hospital.dto.ReviewDto;
import com.study.spring.hospital.entity.H_appm;
import com.study.spring.hospital.entity.H_review;
import com.study.spring.hospital.entity.Hospital;
import com.study.spring.hospital.repository.HospitalAppmRepository;
import com.study.spring.hospital.repository.HospitalRepository;
import com.study.spring.user.entity.User;
import com.study.spring.user.repository.UserRepository;

@Service
public class HospitalService {
	@Autowired
	HospitalRepository hRepo;
	@Autowired
	UserRepository uRepo;
	@Autowired
	HospitalAppmRepository aRepo;

	public List<HospitalDto> findAllHospitalByIdDesc() {
		return hRepo.findAllHospitalByIdDesc();
	}

	public List<H_ReviewAppmDto> findAllReviewByIdDesc() {
		return hRepo.findAllReviewByIdDesc();
	}

	public List<H_ReviewListDto> findWithReivew() {
		List<Hospital> hospitals = hRepo.findWithReview();

		return hospitals.stream()
				.map(h -> H_ReviewListDto.builder()
						.h_code(h.getH_code())
						.h_name(h.getH_name())
						.createdAt(h.getCreatedAt())
						.reviewCount(h.getReviews().size())
						.reviews(h.getReviews().stream()
						.map(review -> new ReviewDto(
								review.getR_id(), 
								review.getR_title(), 
								review.getR_content(),
								review.getR_eval_pt(), 
								review.getR_views(), 
								review.getR_del_yn()))
						.toList())
						.build())
				.toList();
	}

	public H_ReviewListDto findWithReviews(String h_code) {
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
	                                    review.getR_views(),
	                                    review.getR_del_yn()
	                            ))
	                            .toList()
	            )
	            .build();
	}

	public List<H_ReviewUserDto> findReviewWithUser() {
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
										review.getR_views(),
										review.getR_del_yn()))
								.toList())
						.build())
				.toList();	}

	public List<H_ReviewCommentDto> findWithComment() {
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

	public List<H_CommentUserDto> findCommentWithUser() {
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

	public List<H_AppmListDto> findWithAppm() {
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

	public void appmCreate(ReservationDto req) {
		Hospital hospital = hRepo.findById(req.getH_code())
	            .orElseThrow(() -> new RuntimeException("Hospital not Found"));

	    User user = uRepo.findById(req.getA_user_id())
	            .orElseThrow(() -> new RuntimeException("User not Found"));

	    H_appm appm = H_appm.builder()
	            .hospital(hospital)
	            .h_user(user)
	            .a_date(req.getA_date())
	            .a_content(req.getA_content())
	            .a_del_yn(req.getA_del_yn())
	            .createdAt(LocalDateTime.now())
	            .updatedAt(LocalDateTime.now())
	            .build();

	    aRepo.save(appm);
	}

	public H_AppmUserDto findAppmWithUserById(UUID userId) {
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

	public List<H_ReviewLikeDto> findWithLike() {
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

	public List<H_LikeUserDto> findLikeWithUser() {
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

package com.study.spring.hospital.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.study.spring.hospital.dto.H_ReviewAppmDto;
import com.study.spring.hospital.dto.H_ReviewCommentDto;
import com.study.spring.hospital.dto.H_ReviewListDto;
import com.study.spring.hospital.dto.HospitalDto;
import com.study.spring.hospital.entity.H_review;
//import com.study.spring.hospital.dto.H_ReviewListDto;
import com.study.spring.hospital.entity.Hospital;
import com.study.spring.user.entity.User;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, String> {

	@Query("""
			select new com.study.spring.hospital.dto.HospitalDto(
					h.h_code,
					h.h_name,
					h.h_addr,
					h.h_kind,
					h.h_bigo,
					h.h_content,
					h.h_smpl_dgm,
					h.h_tel1,
					h.h_tel2,
					h.h_long,
					h.h_lat,
					h.h_park_yn,
					s.h_mon_s,
					s.h_mon_c,
					s.h_tue_s,
					s.h_tue_c,
					s.h_wed_s,
					s.h_wed_c,
					s.h_tur_s,
					s.h_tur_c,
					s.h_fri_s,
					s.h_fri_c,
					s.h_sat_s,
					s.h_sat_c,
					s.h_sun_s,
					s.h_sun_c,
					s.h_hol_s,
					s.h_hol_c,
					s.h_lun_s,
					s.h_lun_c,
					h.createdAt
				)
				from Hospital h
				join h.hospital_s s
				order by h.h_code desc
				""")
	List<HospitalDto> findAllHospitalByIdDesc();

	@Query("""
			select h
			from Hospital h
			join h.reviews
			order by h.h_code desc
			""")
	List<Hospital> findWithReview();

	@Query("""
			select r
			from H_review  r
			join r.comments
			order by r.r_id desc
			""") // r.comments는 엔티티에서 가져오는 네임
	List<H_review> findWithComment();

	@Query("""
			select h
			from Hospital h
			join h.appms
			order by h.h_code desc
			""")
	List<Hospital> findWithAppm();

	@Query("""
			select r
			from H_review r
			join r.likes
			order by r.r_id desc
			""")
	List<H_review> findWithLike();

	@Query("""
			select new com.study.spring.hospital.dto.H_ReviewAppmDto(
				a.a_id,
				r.r_id,
				r.r_title,
				r.r_content,
				r.r_views,
				a.a_date,
				a.a_content,
				a.a_dia_name,
				a.a_dia_content,
				a.createdAt
			)
			from H_appm a
			join a.h_review r
			order by a.a_id desc
			""")
	List<H_ReviewAppmDto> findAllReviewByIdDesc();

	@Query("""
			select u
			from User u
			join u.appms
			order by u.id desc
			""")
	List<User> findAppmWithUser();

	@Query("""
			select u
			from User u
			join u.reviews
			order by u.id desc
			""")
	List<User> findReviewWithUser();

	@Query("""
			select u
			from User u
			join u.comments
			order by u.id desc
			""")
	List<User> findCommentWithUser();

	@Query("""
			select u
			from User u
			join u.likes
			order by u.id desc
			""")
	List<User> findLikeWithUser();


	@Query("""
			select h
			from Hospital h
			join h.reviews
			where h.h_code = :h_code
			order by h.h_code desc
			""")
	Hospital findWithReviews(@Param("h_code") String h_code);

	@Query("SELECT u FROM User u LEFT JOIN FETCH u.appms WHERE u.id = :id")
	User findAppmWithUserById(@Param("id") UUID id);
}

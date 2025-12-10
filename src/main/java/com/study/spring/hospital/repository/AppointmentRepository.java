package com.study.spring.hospital.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.study.spring.hospital.entity.H_appm;

@Repository
public interface AppointmentRepository extends JpaRepository<H_appm, Integer> {

    // 삭제되지 않은 모든 예약 조회
    @Query("SELECT a FROM H_appm a WHERE a.a_del_yn = 'N'")
    Page<H_appm> findAllActive(Pageable pageable);

    // 특정 사용자(id) 예약 조회
    @Query("SELECT a FROM H_appm a WHERE a.h_user.id = :userId AND a.a_del_yn = 'N'")
    Page<H_appm> findByUserId(@Param("userId") UUID userId, Pageable pageable);

    // 예약 소프트 삭제
    @Modifying
    @Query("UPDATE H_appm a SET a.a_del_yn = 'Y' WHERE a.a_id = :aId")
    void softDelete(@Param("aId") int aId);
}
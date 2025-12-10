package com.study.spring.hospital.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import com.study.spring.hospital.dto.AppointmentFullDto;
import com.study.spring.hospital.entity.H_appm;
import com.study.spring.hospital.repository.AppointmentRepository;

@RestController
@RequestMapping("/api")
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    // 유저별 예약 내역 페이징 조회
    @GetMapping("/appmlist/{a_user_id}")
    public Page<AppointmentFullDto> getUserAppointments(
    		@PathVariable(name = "a_user_id") UUID a_user_id, 
    		 @RequestParam(name = "page", defaultValue = "0") int page,
    	        @RequestParam(name = "size", defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<H_appm> appointmentsPage = appointmentRepository.findByUserId(a_user_id, pageable);

        // Page<H_appm> -> Page<AppointmentFullDto>
        return appointmentsPage.map(this::mapToDto);
    }


    // 예약 업데이트
    @PutMapping("/appointment/update/{a_id}")
    public AppointmentFullDto updateAppointment(
            @PathVariable("a_id") int a_id,
            @RequestParam("symptom") String symptom,
            @RequestParam("note") String note,
            @RequestParam("diagnosis") String diagnosis) {

        return appointmentRepository.findById(a_id)
                .map(appointment -> {
                    appointment.setA_content(symptom);
                    appointment.setA_dia_content(note);
                    appointment.setA_dia_name(diagnosis);
                    appointmentRepository.save(appointment);
                    return mapToDto(appointment);
                })
                .orElse(null);
    }

    // 예약 취소
    @PutMapping("/appointment/delete/{a_id}")
    public String cancelAppointment(@PathVariable("a_id") int a_id) {
        appointmentRepository.findById(a_id).ifPresent(a -> {
            a.setA_del_yn("Y");
            appointmentRepository.save(a);
        });
        return "예약이 취소되었습니다.";
    }

    // 엔티티 -> DTO 변환
    private AppointmentFullDto mapToDto(H_appm a) {
        if (a == null) return null;

        AppointmentFullDto dto = new AppointmentFullDto();
        dto.setA_id(a.getA_id());
        dto.setHospitalName(a.getHospital() != null ? a.getHospital().getH_name() : null);
        dto.setPatientName(a.getH_user() != null ? a.getH_user().getName() : null);
        dto.setSymptom(a.getA_content());
        dto.setNote(a.getA_dia_content());
        dto.setDiagnosis(a.getA_dia_name());
        dto.setDate(a.getA_date());
        dto.setTime(null); // 시간 컬럼이 없으므로 null로 설정
        dto.setPhone(a.getH_user() != null ? a.getH_user().getPhone() : null);
        dto.setAge(a.getH_user() != null ? String.valueOf(a.getH_user().getU_kind()) : null);
        return dto;
    }
}
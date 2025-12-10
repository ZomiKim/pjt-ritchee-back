package com.study.spring.hospital.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentFullDto {
	private int a_id;
	private String hospitalName;
	private String patientName;
	private String symptom;
	private String age;
	private LocalDate date;
	private LocalTime time;
	private String phone;
	private String note;
	private String diagnosis;

}

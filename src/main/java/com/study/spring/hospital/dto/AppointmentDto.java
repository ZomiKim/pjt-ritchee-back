package com.study.spring.hospital.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AppointmentDto {
	private int a_id;
	private String a_date;
	private String a_content;
	private String a_dia_name;
	private String a_dia_content;
}

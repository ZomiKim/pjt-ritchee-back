package com.study.spring.hospital.dto;

import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationDto {
	public String h_code;
	public LocalDate a_date;
	public String a_content;
	public UUID a_user_id;
	public String a_del_yn;
	
}

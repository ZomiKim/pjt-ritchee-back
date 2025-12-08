package com.study.spring.hospital.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class H_ReviewAppmDto {
	private int a_id;
	private int r_id;
	private String r_title;
	private String r_content;
	private Integer r_views;
	private String a_date;
	private String a_content;
	private String a_dia_name;
	private String a_dia_content;
	private LocalDateTime createdAt;
}

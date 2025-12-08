package com.study.spring.hospital.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ReviewDto {
	private int r_id;
	private String r_title;
	private String r_content;
	private Integer r_eval_pt;
	private Integer r_views;
}

package com.study.spring.hospital.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyCommentDto {
	private int c_id;
	private int reviewId; // 댓글이 달릴 리뷰 ID 추가
	private String c_content;
	private UUID userId;

	public MyCommentDto(int c_id, int reviewId, UUID userId, String c_content) {
		this.c_id = c_id;
		this.reviewId = reviewId;
		this.userId = userId;
		this.c_content = c_content;
	}
}

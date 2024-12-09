package com.semin.scheduler.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ScheduleUpdateRequest {
	private String title;
	private String task;
	
	@NotBlank(message = "비밀번호는 필수 입력 항목입니다")
	private String password;
}

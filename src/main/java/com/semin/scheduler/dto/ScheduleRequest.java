package com.semin.scheduler.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ScheduleRequest {
	@NotBlank(message = "제목은 필수 입력 항목입니다")
	private String title;
	
	@NotBlank(message = "할 일은 필수 입력 항목입니다")
	private String task;
	
	@NotBlank(message = "사용자 이름은 필수 입력 항목입니다")
	private String userName;
	
	@NotBlank(message = "비밀번호는 필수 입력 항목입니다")
	@Size(min = 3, max = 100, message = "비밀번호는 3자 이상이어야 합니다")
	private String password;
}

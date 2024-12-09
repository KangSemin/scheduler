package com.semin.scheduler.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

@Getter
@Setter
public class Schedule {

	private Long id;

	@NotBlank(message = "제목은 필수 입력 항목입니다")
	@Size(min = 1, max = 100, message = "제목은 1-100자 사이여야 합니다")
	private String title;

	@NotBlank(message = "할 일은 필수 입력 항목입니다")
	@Size(min = 1, max = 200, message = "할 일은 1-200자 사이여야 합니다")
	private String task;
	private LocalDateTime postedTime;
	private LocalDateTime updatedTime;

	@NotBlank(message = "사용자 이름은 필수입니다")
	private String userName;

}

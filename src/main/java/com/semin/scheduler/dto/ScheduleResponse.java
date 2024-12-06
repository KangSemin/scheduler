package com.semin.scheduler.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ScheduleResponse {
	private Long id;
	private String title;
	private String description;
	private String userName;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}

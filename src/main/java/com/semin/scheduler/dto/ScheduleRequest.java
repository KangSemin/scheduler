package com.semin.scheduler.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ScheduleRequest {
	private String title;
	private String description;
	private String userName;
	private String password;
}

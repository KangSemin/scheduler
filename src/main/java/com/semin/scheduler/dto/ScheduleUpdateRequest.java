package com.semin.scheduler.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleUpdateRequest {

	private String title;
	private String description;
	private String password;

}

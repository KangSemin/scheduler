package com.semin.scheduler.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ScheduleRequest {
	private String title;
	private String task;
	private String userName;
	private String password;
}

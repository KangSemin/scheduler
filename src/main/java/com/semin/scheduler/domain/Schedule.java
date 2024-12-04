package com.semin.scheduler.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Schedule {

	private Long id;
	private String title;
	private String description;
	private String userName;
	private String password;
	private LocalDateTime postedTime;
	private LocalDateTime updatedTime;

}

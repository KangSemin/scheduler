package com.semin.scheduler.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
public class Schedule {

	private Long id;
	private String title;
	private String task;
	private LocalDateTime postedTime;
	private LocalDateTime updatedTime;

}

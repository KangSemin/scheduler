package com.semin.scheduler.controller;

import com.semin.scheduler.dto.ScheduleRequest;
import com.semin.scheduler.dto.ScheduleResponse;
import com.semin.scheduler.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/schedules")
public class ScheduleController {

	private final ScheduleService scheduleService;


	public ScheduleController(ScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}

	@PostMapping
	public void createSchedule(@RequestBody ScheduleRequest request) {
		scheduleService.createSchedule(request);
	}

	@GetMapping
	public List<ScheduleResponse> getAllSchedules() {
		return scheduleService.getAllSchedules();
	}

	@GetMapping("/{id}")
	public ScheduleResponse getScheduleById(@PathVariable Long id) {
		return scheduleService.getScheduleById(id);
	}
}

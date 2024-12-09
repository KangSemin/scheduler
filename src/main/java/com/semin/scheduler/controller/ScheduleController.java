package com.semin.scheduler.controller;

import com.semin.scheduler.dto.*;
import com.semin.scheduler.service.ScheduleService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.semin.scheduler.domain.Schedule;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/schedules")
public class ScheduleController {

	private final ScheduleService scheduleService;

	public ScheduleController(ScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}

	@PostMapping
	public ResponseEntity<ScheduleResponse> createSchedule(@Valid @RequestBody ScheduleRequest request) {
		return ResponseEntity.ok(scheduleService.save(request));
	}

	@GetMapping
	public ResponseEntity<PageResponse<ScheduleResponse>> getAllSchedules(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size
	) {
		return ResponseEntity.ok(scheduleService.getAllSchedulesWithPaging(page, size));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ScheduleResponse> getScheduleById(@PathVariable Long id) {
		return ResponseEntity.ok(scheduleService.getScheduleById(id));
	}

	@GetMapping("/search")
	public ResponseEntity<List<ScheduleResponse>> searchSchedules(
		@RequestParam(required = false) Long userId,
		@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
		
		ScheduleSearchRequest request = new ScheduleSearchRequest();
		if (userId != null) {
			request.setUserId(userId);
		}
		if (date != null) {
			request.setDate(date);
		}
		
		return ResponseEntity.ok(scheduleService.getScheduleByUserIdOrDate(request));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<Void> updateSchedule(@PathVariable Long id, @RequestBody ScheduleUpdateRequest request) {
		scheduleService.updateSchedule(id, request);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteSchedule(@PathVariable Long id, @RequestBody ScheduleDeleteRequest request) {
		scheduleService.deleteSchedule(id, request);
		return ResponseEntity.noContent().build();
	}
}

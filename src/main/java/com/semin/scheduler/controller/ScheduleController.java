package com.semin.scheduler.controller;

import com.semin.scheduler.dto.ScheduleDeleteRequest;
import com.semin.scheduler.dto.ScheduleRequest;
import com.semin.scheduler.dto.ScheduleResponse;
import com.semin.scheduler.dto.ScheduleUpdateRequest;
import com.semin.scheduler.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

	@GetMapping("/search")
	public List<ScheduleResponse> searchSchedules(
			@RequestParam(required = false) String name,
			@RequestParam(required = false) LocalDate date) {

		return scheduleService.getScheduleByNameAndDate(
				Optional.ofNullable(name),
				Optional.ofNullable(date)
		);
	}


	@PatchMapping("/{id}")
	public ResponseEntity<Void> updateSchedule(@PathVariable Long id, @RequestBody ScheduleUpdateRequest request) {
		scheduleService.updateSchedule(id, request);
		return ResponseEntity.noContent().build(); //
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteSchedule(@PathVariable Long id, @RequestBody ScheduleDeleteRequest request) {
		scheduleService.deleteSchedule(id, request);
		return ResponseEntity.noContent().build();
	}
}

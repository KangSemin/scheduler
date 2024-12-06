package com.semin.scheduler.service;

import com.semin.scheduler.dto.ScheduleDeleteRequest;
import com.semin.scheduler.dto.ScheduleRequest;
import com.semin.scheduler.dto.ScheduleResponse;
import com.semin.scheduler.dto.ScheduleUpdateRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScheduleService {
	List<ScheduleResponse> getAllSchedules();

	ScheduleResponse getScheduleById(Long id);

	void createSchedule(ScheduleRequest request);

	void updateSchedule(Long id, ScheduleUpdateRequest request);

	void deleteSchedule(Long id, ScheduleDeleteRequest request);

	List<ScheduleResponse> getScheduleByNameAndDate(Optional<String> name, Optional<LocalDate> date);
}

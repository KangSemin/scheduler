package com.semin.scheduler.service;

import com.semin.scheduler.dto.*;

import java.util.List;

public interface ScheduleService {
	List<ScheduleResponse> getAllSchedules();

	ScheduleResponse getScheduleById(Long id);

	List<ScheduleResponse> getScheduleByUserIdOrDate(ScheduleSearchRequest request);

	void createSchedule(ScheduleRequest request);

	void updateSchedule(Long id, ScheduleUpdateRequest request);

	void deleteSchedule(Long id, ScheduleDeleteRequest request);

}

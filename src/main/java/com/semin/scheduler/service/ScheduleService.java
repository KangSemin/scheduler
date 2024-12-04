package com.semin.scheduler.service;

import com.semin.scheduler.dto.ScheduleRequest;
import com.semin.scheduler.dto.ScheduleResponse;

import java.util.List;

public interface ScheduleService {
	List<ScheduleResponse> getAllSchedules();
	ScheduleResponse getScheduleById(Long id);
	void createSchedule(ScheduleRequest request);

//	void updateSchedule(Long id, ScheduleRequest scheduleRequest);
//	void deleteSchedule(User user, Schedule schedule );
}

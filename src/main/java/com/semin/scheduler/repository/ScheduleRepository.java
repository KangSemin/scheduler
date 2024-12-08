package com.semin.scheduler.repository;

import com.semin.scheduler.domain.Schedule;
import com.semin.scheduler.dto.ScheduleSearchRequest;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {

	List<Schedule> findAll();
	List<Schedule> findByUserIdOrDate(ScheduleSearchRequest request);
	Schedule findById(Long id);
	void save(Schedule schedule, Long userId);
	void deleteById(Long id);
	void update(Schedule schedule);
	Optional<Schedule> findByIdAndPassword(Long scheduleId, String password);

}

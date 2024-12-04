package com.semin.scheduler.service;

import com.semin.scheduler.domain.Schedule;
import com.semin.scheduler.dto.ScheduleRequest;
import com.semin.scheduler.dto.ScheduleResponse;
import com.semin.scheduler.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ScheduleServiceImpl implements ScheduleService {

	private ScheduleRepository scheduleRepository;


	public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
		this.scheduleRepository = scheduleRepository;
	}

	@Override
	public List<ScheduleResponse> getAllSchedules() {
		return scheduleRepository.findAll().stream()
				.map(this::getScheduleResponse).collect(Collectors.toList());
	}


	@Override
	public ScheduleResponse getScheduleById(Long id) {
		Schedule schedule = scheduleRepository.findById(id);
		return getScheduleResponse(schedule);
	}

	@Override
	public void createSchedule(ScheduleRequest request) {
		Schedule schedule = new Schedule();
		schedule.setTitle(request.getTitle());
		schedule.setDescription(request.getDescription());
		schedule.setUserName(request.getUserName());
		schedule.setPassword(request.getPassword());
		schedule.setPostedTime(LocalDateTime.now());
		schedule.setUpdatedTime(LocalDateTime.now());
		scheduleRepository.save(schedule);
	}

	private ScheduleResponse getScheduleResponse(Schedule schedule) {
		ScheduleResponse scheduleResponse = new ScheduleResponse();
		scheduleResponse.setId(schedule.getId());
		scheduleResponse.setTitle(schedule.getTitle());
		scheduleResponse.setDescription(schedule.getDescription());
		scheduleResponse.setUserName(schedule.getUserName());
		scheduleResponse.setCreatedAt(schedule.getPostedTime());
		scheduleResponse.setUpdatedAt(schedule.getUpdatedTime());
		return scheduleResponse;
	}

//	@Override
//	public void updateSchedule(Long id, ScheduleRequest scheduleRequest) {
//
//	}
//
//	@Override
//	public void deleteSchedule(User user, Schedule schedule) {
//
//	}
}

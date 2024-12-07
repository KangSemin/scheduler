package com.semin.scheduler.service;

import com.semin.scheduler.domain.Schedule;
import com.semin.scheduler.dto.ScheduleDeleteRequest;
import com.semin.scheduler.dto.ScheduleRequest;
import com.semin.scheduler.dto.ScheduleResponse;
import com.semin.scheduler.dto.ScheduleUpdateRequest;
import com.semin.scheduler.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ScheduleServiceImpl implements ScheduleService {

	private final ScheduleRepository scheduleRepository;


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
	public List<ScheduleResponse> getScheduleByNameAndDate(Optional<String> name, Optional<LocalDate> date) {
		return scheduleRepository.findByNameAndDate(name,date).stream()
				.map(this::getScheduleResponse).collect(Collectors.toList());

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

	@Override
	public void updateSchedule(Long id, ScheduleUpdateRequest request) {
		Optional<Schedule> scheduleOp = scheduleRepository.findByIdAndPassword(id, request.getPassword());
		Schedule schedule = scheduleOp.orElseThrow(() ->
				new IllegalArgumentException("can't find schedule or wrong password."));

		schedule.setTitle(request.getTitle());
		schedule.setDescription(request.getDescription());
		schedule.setUpdatedTime(LocalDateTime.now());
		scheduleRepository.update(schedule);
	}

	@Override
	public void deleteSchedule(Long id,ScheduleDeleteRequest request) {
		Optional<Schedule> scheduleOp = scheduleRepository.findByIdAndPassword(id, request.getPassword());
		Schedule schedule = scheduleOp.orElseThrow(() ->
				new IllegalArgumentException("can't find schedule or wrong password."));

		scheduleRepository.deleteById(schedule.getId());
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


}

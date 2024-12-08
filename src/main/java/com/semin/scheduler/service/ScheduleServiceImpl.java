package com.semin.scheduler.service;

import com.semin.scheduler.domain.Schedule;
import com.semin.scheduler.dto.*;
import com.semin.scheduler.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
public class ScheduleServiceImpl implements ScheduleService {

	private final ScheduleRepository scheduleRepository;
	private final UserService userService;


	public ScheduleServiceImpl(ScheduleRepository scheduleRepository ,UserService userService) {
		this.scheduleRepository = scheduleRepository;
		this.userService = userService;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ScheduleResponse> getAllSchedules() {
		return scheduleRepository.findAll().stream()
				.map(this::getScheduleResponse).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public ScheduleResponse getScheduleById(Long id) {
		Schedule schedule = scheduleRepository.findById(id);
		if (schedule == null) {
			throw new IllegalArgumentException("스케줄을 찾을 수 없습니다.");
		}
		return getScheduleResponse(schedule);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ScheduleResponse> getScheduleByUserIdOrDate(ScheduleSearchRequest request) {
		return scheduleRepository.findByUserIdOrDate(request).stream()
				.map(this::getScheduleResponse).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void createSchedule(ScheduleRequest request) {
		UserUpdateRequest userRequest = new UserUpdateRequest(
			request.getUserName(), 
			request.getPassword()
		);
		userService.updateUser(userRequest);
		Long userId = userService.findUserId(userRequest);

		if (userId == null) {
			throw new IllegalStateException("사용자 생성/조회 실패");
		}

		Schedule schedule = new Schedule();
		schedule.setTitle(request.getTitle());
		schedule.setTask(request.getTask());
		schedule.setPostedTime(LocalDateTime.now());
		schedule.setUpdatedTime(LocalDateTime.now());

		scheduleRepository.save(schedule, userId);
	}

	@Override
	@Transactional
	public void updateSchedule(Long id, ScheduleUpdateRequest request) {
		Optional<Schedule> scheduleOp = scheduleRepository.findByIdAndPassword(id, request.getPassword());
		
		Schedule schedule = scheduleOp.orElseThrow(() ->
			new IllegalArgumentException("일정을 찾을 수 없거나 비밀번호가 일치하지 않습니다.")
		);
		
		if (request.getTitle() != null) {
			schedule.setTitle(request.getTitle());
		}
		if (request.getTask() != null) {
			schedule.setTask(request.getTask());
		}
		
		schedule.setUpdatedTime(LocalDateTime.now());
		scheduleRepository.update(schedule);
	}

	@Override
	@Transactional
	public void deleteSchedule(Long id, ScheduleDeleteRequest request) {
		Optional<Schedule> scheduleOp = scheduleRepository.findByIdAndPassword(id, request.getPassword());
		
		Schedule schedule = scheduleOp.orElseThrow(() ->
			new IllegalArgumentException("일정을 찾을 수 없거나 비밀번호가 일치하지 않습니다.")
		);
		
		scheduleRepository.deleteById(schedule.getId());
	}


	private ScheduleResponse getScheduleResponse(Schedule schedule) {
			ScheduleResponse response = new ScheduleResponse();
			response.setId(schedule.getId());
			response.setTitle(schedule.getTitle());
			response.setTask(schedule.getTask());
			response.setCreatedAt(schedule.getPostedTime());
			response.setUpdatedAt(schedule.getUpdatedTime());
			return response;
	}


}

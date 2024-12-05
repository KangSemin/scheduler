package com.semin.scheduler.repository;

import com.semin.scheduler.domain.Schedule;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {

	List<Schedule> findAll();   //모든 일정 조회
//	List<Schedule> findByUser(Long userId); // 사용자id를 통한 일정 조회
//	List<Schedule> findByDate(LocalDateTime date); //날짜를 통한 일정 조회
	Schedule findById(Long id); //일정 id를 통한 일정 조회
	void save(Schedule schedule); //저장

	Optional<Schedule> findByIdAndPassword(Long id, String password);
	void deleteById(Long id); //삭제
	void update(Schedule schedule); //수정

}

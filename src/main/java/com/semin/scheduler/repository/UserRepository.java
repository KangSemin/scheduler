package com.semin.scheduler.repository;

import com.semin.scheduler.domain.Schedule;
import com.semin.scheduler.domain.User;
import com.semin.scheduler.dto.ScheduleSearchRequest;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

	Long findUserId(User user);
	void save(User user);

}

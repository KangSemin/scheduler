package com.semin.scheduler.service;

import com.semin.scheduler.domain.User;
import com.semin.scheduler.dto.UserUpdateRequest;
import com.semin.scheduler.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


@Service
@Transactional
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void updateUser(UserUpdateRequest request) {
		Long userId = findUserId(request);
		if (userId == null) {
			User user = new User();
			user.setUsername(request.getUserName());
			user.setPassword(request.getPassword());
			user.setRegisterDate(LocalDate.now());
			user.setUpdateDate(LocalDate.now());
			userRepository.save(user);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Long findUserId(UserUpdateRequest request) {
		User user = new User();
		user.setUsername(request.getUserName());
		user.setPassword(request.getPassword());
		return userRepository.findUserId(user);
	}


}

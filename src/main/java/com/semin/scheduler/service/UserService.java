package com.semin.scheduler.service;

import com.semin.scheduler.dto.*;

public interface UserService {

	void updateUser(UserUpdateRequest request);
	Long findUserId(UserUpdateRequest request);

}

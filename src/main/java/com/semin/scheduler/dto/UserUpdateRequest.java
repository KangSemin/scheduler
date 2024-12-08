package com.semin.scheduler.dto;

import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public class UserUpdateRequest {
	private String userName;
	private String password;


}

package com.semin.scheduler.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@RequiredArgsConstructor
public class User {
	private Long userId;
	private String username;
	private String email;
	private String password;
	private LocalDate registerDate;
	private LocalDate updateDate;
}
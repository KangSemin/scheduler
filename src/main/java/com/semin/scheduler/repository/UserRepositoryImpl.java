package com.semin.scheduler.repository;

import com.semin.scheduler.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Long findUserId(User user) {
		try {
			String sql = "SELECT id FROM ScheduleManagementApplicationSchema.users WHERE username = ? AND password = ?";
			return jdbcTemplate.queryForObject(sql, Long.class, user.getUsername(), user.getPassword());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public void save(User user) {
		String sql = "INSERT INTO ScheduleManagementApplicationSchema.users (username, email, password, registerdate, updatedate) VALUES (?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql,
				user.getUsername(),
				user.getEmail(),
				user.getPassword(),
				user.getRegisterDate(),
				user.getUpdateDate()
		);
	}
}

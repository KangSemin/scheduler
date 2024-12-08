package com.semin.scheduler.repository;

import com.semin.scheduler.domain.Schedule;
import com.semin.scheduler.domain.User;
import com.semin.scheduler.dto.ScheduleSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private static Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
		return getSchedule(rs);
	}

	private static Schedule getSchedule(ResultSet rs) throws SQLException {
		Schedule schedule = new Schedule();
		schedule.setId(rs.getLong("id"));
		schedule.setTitle(rs.getString("title"));
		schedule.setTask(rs.getString("task"));
		schedule.setPostedTime(rs.getTimestamp("posted_time").toLocalDateTime());
		schedule.setUpdatedTime(rs.getTimestamp("updated_time").toLocalDateTime());
		return schedule;
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

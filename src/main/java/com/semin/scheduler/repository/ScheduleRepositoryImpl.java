package com.semin.scheduler.repository;

import com.semin.scheduler.domain.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public ScheduleRepositoryImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Schedule> findAll() {
		String sql = "SELECT * FROM ScheduleManagementApplicationSchema.schedules";
		return jdbcTemplate.query(sql, ScheduleRepositoryImpl::mapRow);
	}

	@Override
	public Schedule findById(Long id) {
		try {
			String sql = "SELECT * FROM ScheduleManagementApplicationSchema.schedules WHERE id = ?";
			return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> getSchedule(rs));
		} catch (EmptyResultDataAccessException e) {
			return null;  // 또는 Optional.empty()를 반환해도 좋다
		}
	}

	@Override
	public void save(Schedule schedule) {
		String sql = "INSERT INTO ScheduleManagementApplicationSchema.schedules (title, description, posted_time, updated_time, username, password) VALUES (?,?,?,?,?,?)";
		jdbcTemplate.update(sql,
				schedule.getTitle(),
				schedule.getDescription(),
				schedule.getPostedTime(),
				schedule.getUpdatedTime(),
				schedule.getUserName(),
				schedule.getPassword()
		);
	}

	private static Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
		return getSchedule(rs);
	}

	private static Schedule getSchedule(ResultSet rs) throws SQLException {
		Schedule schedule = new Schedule();
		schedule.setId(rs.getLong("id"));
		schedule.setTitle(rs.getString("title"));
		schedule.setDescription(rs.getString("description"));
		schedule.setUserName(rs.getString("username"));
		schedule.setPassword(rs.getString("password"));
		schedule.setPostedTime(rs.getTimestamp("posted_time").toLocalDateTime());
		schedule.setUpdatedTime(rs.getTimestamp("updated_time").toLocalDateTime());
		return schedule;
	}

	@Override
	public Optional<Schedule> findByIdAndPassword(Long id, String password) {
		try {
			String sql = "SELECT * FROM ScheduleManagementApplicationSchema.schedules WHERE id = ? AND password = ?";
			Schedule schedule = jdbcTemplate.queryForObject(sql, new Object[]{id, password}, (rs, rowNum) -> getSchedule(rs));
			return Optional.ofNullable(schedule);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public void update(Schedule schedule) {
		String sql = "UPDATE ScheduleManagementApplicationSchema.schedules SET title = ?, description = ?, updated_time = NOW() WHERE id = ?";
		jdbcTemplate.update(sql, schedule.getTitle(), schedule.getDescription(), schedule.getId());
	}

	@Override
	public void deleteById(Long id) {
		String sql = "DELETE FROM ScheduleManagementApplicationSchema.schedules WHERE id = ?";
		jdbcTemplate.update(sql, id);
	}
}

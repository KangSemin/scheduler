package com.semin.scheduler.repository;

import com.semin.scheduler.domain.Schedule;
import com.semin.scheduler.dto.ScheduleSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
			ResponseEntity.status(HttpStatus.NOT_FOUND);
			return null ;
		}
	}

	@Override
	public void save(Schedule schedule, Long userId) {

		String scheduleSql = "INSERT INTO ScheduleManagementApplicationSchema.schedules (title, task, posted_time, updated_time,user_id) VALUES (?,?,?,?,?)";

		jdbcTemplate.update(scheduleSql,
				schedule.getTitle(),
				schedule.getTask(),
				schedule.getPostedTime(),
				schedule.getUpdatedTime(),
				userId
		);
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
	public void update(Schedule schedule) {
		String sql = "UPDATE ScheduleManagementApplicationSchema.schedules SET title = ?, task = ?, updated_time = NOW() WHERE id = ?";
		jdbcTemplate.update(sql, schedule.getTitle(), schedule.getTask(), schedule.getId());
	}

	@Override
	public List<Schedule> findByUserIdOrDate(ScheduleSearchRequest request) {
		StringBuilder sql = new StringBuilder(
			"SELECT s.* FROM ScheduleManagementApplicationSchema.schedules s WHERE 1=1"
		);
		List<Object> params = new ArrayList<>();

		if (request.getUserId().isPresent()) {
			sql.append(" AND s.user_id = ?");
			params.add(request.getUserId().get());
		}

		if (request.getDate().isPresent()) {
			sql.append(" AND DATE(s.posted_time) = ?");
			params.add(request.getDate().get());
		}

		sql.append(" ORDER BY s.posted_time DESC");

		return jdbcTemplate.query(
			sql.toString(),
			params.toArray(),
			(rs, rowNum) -> getSchedule(rs)
		);
	}

	@Override
	public void deleteById(Long id) {
		String sql = "DELETE FROM ScheduleManagementApplicationSchema.schedules WHERE id = ?";
		jdbcTemplate.update(sql, id);
	}

	@Override
	public Optional<Schedule> findByIdAndPassword(Long scheduleId, String password) {
		try {
			String sql = "SELECT s.* FROM ScheduleManagementApplicationSchema.schedules s " +
						"JOIN ScheduleManagementApplicationSchema.users u ON s.user_id = u.id " +
						"WHERE s.id = ? AND u.password = ?";
						
			Schedule schedule = jdbcTemplate.queryForObject(
				sql,
				new Object[]{scheduleId, password},
				(rs, rowNum) -> getSchedule(rs)
			);
			return Optional.ofNullable(schedule);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}
}

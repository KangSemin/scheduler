package com.semin.scheduler.repository;

import com.semin.scheduler.domain.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;


@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository{

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public ScheduleRepositoryImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


	@Override
	public List<Schedule> findAll() {

		String sql = "SELECT * from ScheduleManagementApplicationSchema.schedules";

		return jdbcTemplate.query(sql, ScheduleRepositoryImpl::mapRow);

	}

//	@Override
//	public List<Schedule> findByUser(Long userId) {
//		return List.of();
//	}
//
//	@Override
//	public List<Schedule> findByDate(LocalDateTime date) {
//		return List.of();
//	}

	@Override
	public Schedule findById(Long id) {
		String sql = "SELECT * FROM ScheduleManagementApplicationSchema.schedules WHERE id = ?";
		return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> getSchedule(rs));
	}

	@Override
	public void save(Schedule schedule) {
		String sql ="Insert into ScheduleManagementApplicationSchema.schedules (title, description, posted_time, updated_time, username, password) VALUES (?,?,?,?,?,?)";
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

//	@Override
//	public void update(Schedule schedule) {
//
//	}
//
//	@Override
//	public void deleteById(Long id) {

}


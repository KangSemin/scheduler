package com.semin.scheduler.dto;

import lombok.Getter;

import java.time.LocalDate;
import java.util.Optional;

@Getter
public class ScheduleSearchRequest {

	private Optional<Long> userId = Optional.empty();
    private Optional<LocalDate> date = Optional.empty();
	
    public ScheduleSearchRequest() {
        this.userId = Optional.empty();
        this.date = Optional.empty();
    }
    
    public void setUserId(Long userId) {
        this.userId = Optional.ofNullable(userId);
    }
    
    public void setDate(LocalDate date) {
        this.date = Optional.ofNullable(date);
    }
}

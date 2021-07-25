package com.focalcxm.facedoc.dao;

import java.util.List;

import com.focalcxm.facedoc.bean.Schedule;

/**
 * @author focalcxm
 * @since 06/01/2021
 *
 */
public interface ScheduleDao {

	public void addDrSchedule(Schedule schedule) throws Exception;
	
	public List<Schedule> getDrSchedules();
	
}

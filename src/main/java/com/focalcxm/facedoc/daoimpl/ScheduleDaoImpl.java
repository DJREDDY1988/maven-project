package com.focalcxm.facedoc.daoimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.focalcxm.facedoc.bean.Schedule;
import com.focalcxm.facedoc.dao.ScheduleDao;
import com.focalcxm.facedoc.queries.FaceDocDrQueries;

/**
 * @author focalcxm
 * @since 06/01/2021
 *
 */
@Component
public class ScheduleDaoImpl implements ScheduleDao{

	Logger log = LogManager.getLogger(ScheduleDaoImpl.class);

	@Autowired
	private JdbcTemplate mysqlTemplate;

	@Override
	public void addDrSchedule(Schedule schedule) throws Exception {
		try {

			String drScheduleInsert = FaceDocDrQueries.getAddScheduleQuery(schedule);	

			mysqlTemplate.execute(drScheduleInsert);
		}catch(Exception e) {
			log.error("Exception occured while inserting Dr_schedule "+e.getMessage());
			throw new Exception("Dr_ScheduleInsertion failed ");
		}
	}

	@Override
	public List<Schedule> getDrSchedules() {
		List<Schedule> schedulesList = new ArrayList<>();
		try {
			String sql = FaceDocDrQueries.getDrSchedulesQuery();
			List<Map<String, Object>> resultList = mysqlTemplate.queryForList(sql);
			
			for (Map<String, Object> scheduleMap : resultList) {
				Schedule schedule = new Schedule();
				schedule.setId(Integer.parseInt(scheduleMap.get("ID").toString()));
				schedule.setDoctorId(Integer.parseInt(scheduleMap.get("DOCTOR_ID").toString()));
				schedule.setStartDate(scheduleMap.get("START_DATE").toString());
				schedule.setEndDate(scheduleMap.get("END_DATE").toString());
				schedule.setDayOfWeek(scheduleMap.get("DAY_OF_WEEK").toString());
				schedule.setStartTime(scheduleMap.get("START_TIME").toString());
				schedule.setEndTime(scheduleMap.get("END_TIME").toString());
				schedule.setSlotDuration(Integer.parseInt(scheduleMap.get("SLOT_DURATION").toString()));
				schedule.setTypeOfAppointment(scheduleMap.get("TYPE_OF_APPOINTMENT").toString());
				schedule.setCreatedDate(scheduleMap.get("CREATED_DATE").toString());
				schedule.setLastUpdatedDate(scheduleMap.get("LAST_UPDATED_DATE").toString());
				schedule.setCreatedBy(scheduleMap.get("CREATED_BY").toString());
				schedule.setLastUpdatedBy(scheduleMap.get("LAST_UPDATED_BY").toString());
				schedulesList.add(schedule);
			}
		}catch(Exception e) {
			log.error("Exception occured while processing getDrSchedules:: ScheduleDaoImpl "+e.getMessage());
		}
		return schedulesList;
	}

}

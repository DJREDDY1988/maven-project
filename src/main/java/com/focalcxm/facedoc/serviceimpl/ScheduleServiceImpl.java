/**
 * 
 */
package com.focalcxm.facedoc.serviceimpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.focalcxm.facedoc.bean.Schedule;
import com.focalcxm.facedoc.dao.ScheduleDao;
import com.focalcxm.facedoc.service.ScheduleService;

/**
 * @author focalcxm
 * @since 06/01/2021
 *
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {
	
	Logger log = LogManager.getLogger(ScheduleServiceImpl.class);

	@Autowired
	private ScheduleDao scheduleDaoImpl;
	
	@Override
	public void addDrSchedule(Schedule schedule) throws Exception {
		scheduleDaoImpl.addDrSchedule(schedule);
	}

}

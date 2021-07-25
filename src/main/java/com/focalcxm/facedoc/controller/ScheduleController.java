/**
 * 
 */
package com.focalcxm.facedoc.controller;

import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.focalcxm.facedoc.bean.Schedule;
import com.focalcxm.facedoc.service.ScheduleService;

/**
 * @author focalcxm
 * @since 06/01/2021
 *
 */
@RestController
@RequestMapping("/api/v1/schedule")
public class ScheduleController {

	Logger log = LogManager.getLogger(ScheduleController.class);

	@Autowired
	private ScheduleService scheduleServiceImpl;

	@RequestMapping(value="/addschedule",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE,
			method=RequestMethod.POST)
	public ResponseEntity<?> addDrSchedule(@RequestBody Schedule schedule){

		if(Objects.isNull(schedule.getDoctorId())) {
			log.error("DoctorId is Mandatory to add Dr_schedule.");
			throw new IllegalArgumentException("DoctorId is Mandatory.");
		}

		//TODO: add dup check here of doctor id ,based on that add update or create ops.

		if(Objects.isNull(schedule.getCreatedDate())||"".equals(schedule.getCreatedDate().toString())) { // add nullemptycheck for lastupdate
			schedule.setCreatedDate(new java.sql.Timestamp(System.currentTimeMillis()).toString());
			schedule.setLastUpdatedDate(new java.sql.Timestamp(System.currentTimeMillis()).toString());
		}

		try {
			if(!Objects.isNull(schedule.getDoctorId())) {
				scheduleServiceImpl.addDrSchedule(schedule);
			}
			return new ResponseEntity<>(HttpStatus.CREATED);
		}catch(Exception e) {
			log.error("Exception occured while creating Doctor's schedule "+e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

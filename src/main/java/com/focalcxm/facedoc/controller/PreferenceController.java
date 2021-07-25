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

import com.focalcxm.facedoc.bean.Preference;
import com.focalcxm.facedoc.service.PreferenceService;

/**
 * @author focalcxm
 * @since 5/30/2021
 *
 */

@RestController
@RequestMapping(value = "/api/v1/preference")
public class PreferenceController {

	Logger log = LogManager.getLogger(PreferenceController.class);

	@Autowired
	private PreferenceService preferenceServiceImpl;
	
	@RequestMapping(value = "/health",method=RequestMethod.GET)
	public String checkStatus() {
		return "FACE DOC APP IS UP";
	}

	@RequestMapping(value = "/addpreference",consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE,method=RequestMethod.POST)
	public ResponseEntity<?> addPreference(@RequestBody Preference preference) {
		log.debug("adding Preference for request ",preference.toString());
		if(Objects.isNull(preference.getUserId())) {
			log.error("User Id cant be null or Empty");
			throw new IllegalArgumentException("User Id cant be null or Empty");
		}

		/*
		 * { "userId":"2", "slotDuration":"30", "workingHours":"2",
		 * "consultationFee":"500", "generateSlotNumOfMonths":"3",
		 * "availableSunday":"false", "availableSaturday":"false", "createdDate":"",
		 * "lastUpdatedDate":"", "createdBy":"focalCxmAdmin",
		 * "lastUpdatedBy":"focalCxmAdmin" }
		 */

		//TODO : check if userId exits ,if not throw exception since userid is FK with preference table.

		//TODO : if ,for userId record already exits in DB ,set lastupdatedTime and dont execute below logic ,
		//add a true/false flag and dont execute below logic.


		if(Objects.isNull(preference.getCreatedDate())||"".equals(preference.getCreatedDate().toString())) {
			preference.setCreatedDate(new java.sql.Timestamp(System.currentTimeMillis()).toString());
			preference.setLastUpdatedDate(new java.sql.Timestamp(System.currentTimeMillis()).toString());
		}


		try {
			if(!Objects.isNull(preference.getUserId())) {
				preferenceServiceImpl.addDocPreferences(preference);
			}
			return new ResponseEntity<>(HttpStatus.CREATED);
		}catch(Exception e) {
			log.error("exception occurs while processing addPreference"+e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}

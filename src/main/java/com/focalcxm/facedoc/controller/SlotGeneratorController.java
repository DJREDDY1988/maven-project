package com.focalcxm.facedoc.controller;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.focalcxm.facedoc.service.SlotGenService;

@RestController
@RequestMapping("api/v1/slotgeneration")
public class SlotGeneratorController {
	
	Logger log = LogManager.getLogger(SlotGeneratorController.class);

	@Autowired
	private SlotGenService slotGenServiceImpl;


	@RequestMapping(value="/trigger",method=RequestMethod.GET)
	public ResponseEntity<?> triggerSlotGen() throws IOException {

		try {
			slotGenServiceImpl.triggerSlotGen();
			log.info("Slots Generated Successfully");
			return new ResponseEntity<String>("Slots Generated Successfully",HttpStatus.OK);
		}catch(Exception e) {
			log.error("Exception occured with Slot Creation"+e.getMessage());
			return new ResponseEntity<String>("Slot Generation Failed",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}

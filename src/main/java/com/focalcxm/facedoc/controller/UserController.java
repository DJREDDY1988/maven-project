/**
 * 
 */
package com.focalcxm.facedoc.controller;

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

import com.focalcxm.facedoc.bean.User;
import com.focalcxm.facedoc.service.UserService;

/**
 * @author focalcxm
 * @since 06/09/2021
 *
 */
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
	
	Logger log = LogManager.getLogger(UserController.class);
	
	@Autowired
	private UserService userServiceImpl;
	
	@RequestMapping(value="/adduser",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE,
			method=RequestMethod.POST)
	public ResponseEntity<?> addUser(@RequestBody User user){
		try {
			userServiceImpl.addUser(user);
			return new ResponseEntity<String>("User Added Successfully",HttpStatus.OK);
		}catch(Exception e) {
			log.error("Exception occured while adding User "+e.getMessage());
			return new ResponseEntity<String>("User adding failed",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}

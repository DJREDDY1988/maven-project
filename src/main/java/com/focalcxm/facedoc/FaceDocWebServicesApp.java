package com.focalcxm.facedoc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 
 * @author focalcxm
 * @since 5/30/2021
 *
 */
@SpringBootApplication
public class FaceDocWebServicesApp implements ApplicationRunner{
	
	Logger log = LogManager.getLogger(FaceDocWebServicesApp.class);
	
	public static void main(String[] args) {
		try {
			SpringApplication.run(FaceDocWebServicesApp.class, args);
		}catch(Exception e) {
			System.out.println("Exception occured : "+e.getMessage());
		}
		
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println("FaceDoc boot WebservicesApp started.");
	}

}

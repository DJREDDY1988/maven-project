/**
 * 
 */
package com.focalcxm.facedoc.serviceimpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.focalcxm.facedoc.bean.Preference;
import com.focalcxm.facedoc.dao.PreferenceDao;
import com.focalcxm.facedoc.service.PreferenceService;

/**
 * @author focalcxm
 * @since 5/30/2021
 *
 */
@Service
public class PreferenceServiceImpl implements PreferenceService{
	
	Logger log = LogManager.getLogger(PreferenceServiceImpl.class);

	@Autowired
	private PreferenceDao preferenceDaoImpl;

	@Override
	public void addDocPreferences(Preference preference) throws Exception {

		try {
			preferenceDaoImpl.addPreference(preference);
		}catch(Exception e) {
			log.error("Exception occured while processing addDocPreferences" ,e.getMessage());
			throw new Exception(e.getMessage());
		}
	}

}

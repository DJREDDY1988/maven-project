/**
 * 
 */
package com.focalcxm.facedoc.daoimpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.focalcxm.facedoc.bean.Preference;
import com.focalcxm.facedoc.dao.PreferenceDao;
import com.focalcxm.facedoc.queries.FaceDocDrQueries;

/**
 * @author FocalCXM
 * @since 06/01/2021
 *
 */
@Component
public class PreferenceDaoImpl implements PreferenceDao{

	Logger log = LogManager.getLogger(PreferenceDaoImpl.class);

	@Autowired
	private JdbcTemplate mysqlTemplate;

	@Override
	public void addPreference(Preference preference) throws Exception {
		try {

			String addDrPrefquery = FaceDocDrQueries.getAddPreferenceQuery(preference);
			mysqlTemplate.execute(addDrPrefquery);
		}catch(Exception e) {
			log.error("Exception occred while processing addPreference() :: PreferenceDaoImpl " +e.getMessage());
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public List<Preference> getDocPreferences() {

		List<Preference> preferencesList = new ArrayList<>();
		try {
			String sql = FaceDocDrQueries.getAllPreferenceQuery();
			List<Map<String, Object>> prefList = mysqlTemplate.queryForList(sql);

			for (Map<String, Object> prefMap : prefList) {
				Preference preference = new Preference();
				preference.setId(Integer.parseInt(prefMap.get("ID").toString()));
				preference.setUserId(Integer.parseInt(prefMap.get("USER_ID").toString()));
				preference.setSlotDuration(Integer.parseInt(prefMap.get("SLOT_DURATION").toString()));
				preference.setWorkingHours(Integer.parseInt(prefMap.get("WORKING_HOURS").toString()));
				preference.setConsultationFee(new BigDecimal(prefMap.get("CONSULTATION_FEE").toString()));
				preference.setGenerateSlotNumOfMonths(Integer.parseInt(prefMap.get("GENERATE_SLOTS_FOR_NO_OF_MONTHS").toString()));
				preference.setAvailableSunday(Boolean.valueOf(prefMap.get("AVAILABLE_SUNDAY").toString()));
				preference.setAvailableSunday(Boolean.valueOf(prefMap.get("AVAILABLE_SATURDAY").toString()));
				preference.setCreatedDate(prefMap.get("CREATED_DATE").toString());
				preference.setLastUpdatedDate(prefMap.get("LAST_UPDATED_DATE").toString());
				preference.setCreatedBy(prefMap.get("CREATED_BY").toString());
				preference.setLastUpdatedBy(prefMap.get("LAST_UPDATED_BY").toString());
				preferencesList.add(preference);
			}
		}catch(Exception e){
			log.error("Exception occred while processing getDocPreferences() :: PreferenceDaoImpl " +e.getMessage());
		}

		return preferencesList;
	}

	@Override
	public Preference getPreferenceByUserId(int doctorId) {
		List<Preference> preferencesList = new ArrayList<>();
		try {
			String sql = FaceDocDrQueries.getDocterPreferenceByDocId(doctorId);
			List<Map<String, Object>> prefList = mysqlTemplate.queryForList(sql);

			for (Map<String, Object> prefMap : prefList) {
				Preference preference = new Preference();
				preference.setId(Integer.parseInt(prefMap.get("ID").toString()));
				preference.setUserId(Integer.parseInt(prefMap.get("USER_ID").toString()));
				preference.setSlotDuration(Integer.parseInt(prefMap.get("SLOT_DURATION").toString()));
				preference.setWorkingHours(Integer.parseInt(prefMap.get("WORKING_HOURS").toString()));
				preference.setConsultationFee(new BigDecimal(prefMap.get("CONSULTATION_FEE").toString()));
				preference.setGenerateSlotNumOfMonths(Integer.parseInt(prefMap.get("GENERATE_SLOTS_FOR_NO_OF_MONTHS").toString()));
				preference.setAvailableSunday(Boolean.valueOf(prefMap.get("AVAILABLE_SUNDAY").toString()));
				preference.setAvailableSunday(Boolean.valueOf(prefMap.get("AVAILABLE_SATURDAY").toString()));
				preference.setCreatedDate(prefMap.get("CREATED_DATE").toString());
				preference.setLastUpdatedDate(prefMap.get("LAST_UPDATED_DATE").toString());
				preference.setCreatedBy(prefMap.get("CREATED_BY").toString());
				preference.setLastUpdatedBy(prefMap.get("LAST_UPDATED_BY").toString());
				preferencesList.add(preference);
			}
		}catch(Exception e){
			log.error("Exception occred while processing getDocPreferences() :: PreferenceDaoImpl " +e.getMessage());
		}

		if(!CollectionUtils.isEmpty(preferencesList)) {
			return preferencesList.get(0);
		}
		return null;
	}

	@Override
	public List<Integer> getDocterIdsList() {
		List<Integer> userIdList = new ArrayList<>();
		try {
			String sql = FaceDocDrQueries.getDistinctUserIdQuery();
			userIdList = mysqlTemplate.queryForList(sql,Integer.class);

		}catch(Exception e) {
			log.error("Exception occred while processing getDocterIdsList() :: PreferenceDaoImpl " + e.getMessage());
		}

		return userIdList;
	}

}

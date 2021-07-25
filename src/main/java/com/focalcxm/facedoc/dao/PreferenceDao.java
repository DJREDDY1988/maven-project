/**
 * 
 */
package com.focalcxm.facedoc.dao;

import java.util.List;

import com.focalcxm.facedoc.bean.Preference;

/**
 * @author focalcxm
 * @since 06/01/2021
 *
 */
public interface PreferenceDao {

	public void addPreference(Preference preference) throws Exception;
	
	public List<Preference> getDocPreferences();
	
	public Preference getPreferenceByUserId(int doctorId);
	
	public List<Integer> getDocterIdsList();
}

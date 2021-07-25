/**
 * 
 */
package com.focalcxm.facedoc.queries;

import com.focalcxm.facedoc.bean.Preference;
import com.focalcxm.facedoc.bean.Schedule;
import com.focalcxm.facedoc.bean.Slot;
import com.focalcxm.facedoc.bean.User;

/**
 * @author FocalCxm
 * @Since 06/01/2021
 *
 */
public class FaceDocDrQueries {

	public static String getAddPreferenceQuery(Preference preference) {

		String insertPreferences = "insert into DR_PREFERENCES(USER_ID,SLOT_DURATION,WORKING_HOURS,CONSULTATION_FEE,GENERATE_SLOTS_FOR_NO_OF_MONTHS,AVAILABLE_SUNDAY,AVAILABLE_SATURDAY,\r\n"
				+ "	CREATED_DATE,LAST_UPDATED_DATE,CREATED_BY,LAST_UPDATED_BY) values("
				+ preference.getUserId() + ","
				+ preference.getSlotDuration() + ","
				+ preference.getWorkingHours() + ","
				+ preference.getConsultationFee() + ","
				+ preference.getGenerateSlotNumOfMonths() + ","
				+ preference.isAvailableSunday() + ","
				+ preference.isAvailableSaturday() + ","
				+"'"+ preference.getCreatedDate() + "',"
				+"'"+ preference.getLastUpdatedDate() + "',"
				+"'"+ preference.getCreatedBy() + "',"
				+"'"+ preference.getLastUpdatedBy() 
				+"')";

		return insertPreferences;

	}

	public static String getAddScheduleQuery(Schedule schedule) {

		String drScheduleInsert = "insert into dr_schedules(DOCTOR_ID,START_DATE,END_DATE,DAY_OF_WEEK,START_TIME,END_TIME,"
				+ "SLOT_DURATION,TYPE_OF_APPOINTMENT,CREATED_DATE,LAST_UPDATED_DATE,CREATED_BY,LAST_UPDATED_BY) values("
				+ schedule.getDoctorId() +","
				+"'"+ schedule.getStartDate() +"',"
				+"'"+ schedule.getEndDate() +"',"
				+"'"+ schedule.getDayOfWeek() +"',"
				+"'"+ schedule.getStartTime() +"',"
				+"'"+ schedule.getEndTime() +"',"
				+ schedule.getSlotDuration() +","
				+"'"+ schedule.getTypeOfAppointment() +"',"
				+"'"+ schedule.getCreatedDate() +"',"
				+"'"+ schedule.getLastUpdatedDate() +"',"
				+"'"+ schedule.getCreatedBy() +"',"
				+"'"+ schedule.getLastUpdatedBy() +"'"
				+")";
		return drScheduleInsert;
	}

	public static String getAllPreferenceQuery() {
		String getAllPreferenceQuery="select * from dr_preferences";
		return getAllPreferenceQuery;
	}
	
	public static String getDrSchedulesQuery() {
		String getDrSchedules = "select * from dr_schedules"; 
		return getDrSchedules;
	}

	public static String getDocterPreferenceByDocId(int doctorId) {
		String getPrefByDocIdQuery = "select * from dr_preferences where user_id="+doctorId;
		return getPrefByDocIdQuery;
	}

	public static String getDistinctUserIdQuery() {
		return "select distinct user_id from dr_preferences";
	}

	public static String insertSlotQuery(Slot slot) {
		return "insert into dr_slots(schedule_id,doctor_id,DATE,slot_start_time,slot_end_time,is_available,created_date,last_updated_date,"
				+ "created_by,LAST_UPDATED_BY) values("
				+slot.getScheduleId()+","
				+slot.getDoctorId()+","
				+"'"+slot.getDate()+"',"
				+"'"+slot.getSlotStartTime()+"',"
				+"'"+slot.getSlotEndTime()+"',"
				+slot.isAvailable()+","
				+"'"+slot.getCreatedDate()+"',"
				+"'"+slot.getLastUpdatedDate()+"',"
				+"'"+slot.getCreatedBy()+"',"
				+"'"+slot.getLastUpdatedBy()+"')";
	}

	public static String getAddUserQuery(User user) {
		return "insert into user(user_id,user_name) values(" +user.getUserId()+ ",'" +user.getUserName()+ "')";
	}
}

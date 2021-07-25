/**
 * 
 */
package com.focalcxm.facedoc.serviceimpl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.focalcxm.facedoc.bean.Preference;
import com.focalcxm.facedoc.bean.Schedule;
import com.focalcxm.facedoc.bean.Slot;
import com.focalcxm.facedoc.dao.PreferenceDao;
import com.focalcxm.facedoc.dao.ScheduleDao;
import com.focalcxm.facedoc.dao.SlotsDao;
import com.focalcxm.facedoc.service.SlotGenService;

/**
 * @author focalcxm
 * @since 06/03/2021
 *
 */
@Service
public class SlotGenServiceImpl implements SlotGenService {
	Logger log = LogManager.getLogger(SlotGenServiceImpl.class);

	@Autowired
	private PreferenceDao preferenceDaoImpl;

	@Autowired
	private ScheduleDao scheduleDaoImpl;
	
	@Autowired
	private SlotsDao slotsDaoImpl;
	
	@Override
	public void triggerSlotGen() throws Exception {
		
		List<Slot> slotLists = new ArrayList<>();
		try {
			List<Integer> dupDocIdList = new ArrayList<>();
			List<Preference> prefList = preferenceDaoImpl.getDocPreferences();
			List<Schedule> scheduleList = scheduleDaoImpl.getDrSchedules();
			Map<Integer, List<Schedule>> scheduleDocMap = scheduleList.stream().collect(Collectors.groupingBy(Schedule::getDoctorId));
			prefList.stream().forEach(preference->{
				log.info("Preference is "+ preference);
				if(dupDocIdList.contains(preference.getUserId())) {
					// skip the slot generation process if already slot generated for doctorId
					return;
				}else {
					dupDocIdList.add(preference.getUserId());
					List<Schedule> doctrIdScheduleList = scheduleDocMap.get(preference.getUserId());
					log.info("doctrIdScheduleList is "+ doctrIdScheduleList);
					doctrIdScheduleList.stream().forEach(schedule->{
						log.info("schedule is "+ schedule);
						List<LocalDate> availableDates = getGeneratedDates(schedule,schedule.getDayOfWeek(),preference);
						log.info("availableDates are "+ availableDates);
						if(null!=availableDates && availableDates.size()>0) {
							availableDates.stream().forEach(date->{
								log.info("availableDates date "+ date);
								try {
									slotLists.addAll(getSlots(date,schedule.getId(),schedule.getStartTime(),schedule.getEndTime(),schedule.getSlotDuration(),preference));
								} catch (ParseException e) {
									e.printStackTrace();
								}
							});
						}
					});
				}

			});
			
			slotsDaoImpl.insertSlots(slotLists);
		}catch(Exception e) {
			log.error("Exception occured while processing triggerSlotGen() :: SlotGenServiceImpl " + e.getMessage());
		}
	}

	private List<Slot> getSlots(LocalDate date, int scheduleId, String slotGenStartTime, String slotGenEndTime, int slotDuration, Preference preference) throws ParseException {
		List<Slot> slotsList = new ArrayList<>();
		try {
			
			SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss a");
			String date1 = date.toString();
			String date2 = date.toString();

			//1 -sunday 2- monday 3-tuesday 4-wednesday 5- thrusday 6- friday 7 - saturday
			Date weekDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
			Calendar cal = Calendar.getInstance();
			cal.setTime(weekDate);
			int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
			boolean isAvailable = false;
			if(7==dayOfWeek && preference.isAvailableSaturday()) {
				isAvailable = true;
			}else if(1==dayOfWeek && preference.isAvailableSunday()) {
				isAvailable = true;
			}else {
				isAvailable = true;
			}

			String format = "yyyy-MM-dd hh:mm:ss a";
			SimpleDateFormat sdf = new SimpleDateFormat(format);

			Date dateObj1 = sdf.parse(date1 + " " + LocalTime.parse(slotGenStartTime, DateTimeFormatter.ofPattern("HH:mm:ss")).format(DateTimeFormatter.ofPattern("hh:mm:ss a")));
			Date dateObj2 = sdf.parse(date2 + " " + LocalTime.parse(slotGenEndTime, DateTimeFormatter.ofPattern("HH:mm:ss")).format(DateTimeFormatter.ofPattern("hh:mm:ss a")));

			long slotEndTime = dateObj1.getTime();
			while (slotEndTime < dateObj2.getTime()) {
				Slot slot = new Slot();
				slot.setScheduleId(scheduleId);
				slot.setDoctorId(preference.getUserId());
				slot.setDate(date1);

				Date slotStartTime = new Date(slotEndTime);
				slot.setSlotStartTime(timeFormat.format(slotStartTime));
				slotEndTime += slotDuration*60*1000;
				slot.setSlotEndTime(timeFormat.format(slotEndTime));
				slot.setAvailable(isAvailable);
				slot.setCreatedDate(new java.sql.Timestamp(System.currentTimeMillis()).toString());
				slot.setCreatedBy("focalCxm");
				slot.setLastUpdatedBy("focalcxm");
				slot.setLastUpdatedDate(new java.sql.Timestamp(System.currentTimeMillis()).toString());
				slotsList.add(slot);
			}
		}catch(Exception e) {
			log.error("Exception occured while processing getSlots() :: SlotGenServiceImpl "+e.getMessage());
		}

		return slotsList;
	}

	private List<LocalDate> getGeneratedDates(Schedule schedule, String dayOfWeek, Preference preference) {
		List<LocalDate> datesList = new ArrayList<>();
		LocalDate startDate = LocalDate.parse(schedule.getStartDate());
		LocalDate endDate = LocalDate.parse(schedule.getEndDate());

		switch(schedule.getDayOfWeek().toUpperCase()) {

		case "MONDAY":

			LocalDate thisMonday = startDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));

			if (startDate.isAfter(thisMonday)) {
				startDate = thisMonday.plusWeeks(1); 
			} else {
				startDate = thisMonday; 
			}

			while (startDate.isBefore(endDate) ||startDate.isEqual(endDate)) {
				datesList.add(startDate);
				startDate = startDate.plusWeeks(1);
			}

			break;

		case "TUESDAY":	

			LocalDate thisTuesday = startDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.TUESDAY));

			if (startDate.isAfter(thisTuesday)) {
				startDate = thisTuesday.plusWeeks(1); 
			} else {
				startDate = thisTuesday; 
			}

			while (startDate.isBefore(endDate)||startDate.isEqual(endDate)) {
				datesList.add(startDate);
				startDate = startDate.plusWeeks(1);
			}

			break;

		case "WEDNESDAY":	

			LocalDate thisWednesday = startDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.WEDNESDAY));

			if (startDate.isAfter(thisWednesday)) {
				startDate = thisWednesday.plusWeeks(1); 
			} else {
				startDate = thisWednesday; 
			}

			while (startDate.isBefore(endDate)||startDate.isEqual(endDate)) {
				datesList.add(startDate);
				startDate = startDate.plusWeeks(1);
			}

			break;

		case "THURSDAY":	

			LocalDate thisThursday = startDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.THURSDAY));

			if (startDate.isAfter(thisThursday)) {
				startDate = thisThursday.plusWeeks(1); 
			} else {
				startDate = thisThursday; 
			}

			while (startDate.isBefore(endDate)||startDate.isEqual(endDate)) {
				datesList.add(startDate);
				startDate = startDate.plusWeeks(1);
			}

			break;

		case "FRIDAY":	

			LocalDate thisFriday = startDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY));

			if (startDate.isAfter(thisFriday)) {
				startDate = thisFriday.plusWeeks(1); 
			} else {
				startDate = thisFriday; 
			}

			while (startDate.isBefore(endDate)||startDate.isEqual(endDate)) {
				datesList.add(startDate);
				startDate = startDate.plusWeeks(1);
			}

			break;

		case "SATURDAY":	
			if(preference.isAvailableSaturday()) {
				LocalDate thisSaturday = startDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));

				if (startDate.isAfter(thisSaturday)) {
					startDate = thisSaturday.plusWeeks(1); 
				} else {
					startDate = thisSaturday; 
				}

				while (startDate.isBefore(endDate)||startDate.isEqual(endDate)) {
					datesList.add(startDate);
					startDate = startDate.plusWeeks(1);
				}
			}else {
				datesList = new ArrayList<>();
			}

			break;

		case "SUNDAY":	
			if(preference.isAvailableSunday()) {
				LocalDate thisSunday = startDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

				if (startDate.isAfter(thisSunday)) {
					startDate = thisSunday.plusWeeks(1); 
				} else {
					startDate = thisSunday; 
				}

				while (startDate.isBefore(endDate)||startDate.isEqual(endDate)) {
					datesList.add(startDate);
					startDate = startDate.plusWeeks(1);
				}
			}else {
				datesList = new ArrayList<>();
			}

			break;

		}
		return datesList;
	}

}

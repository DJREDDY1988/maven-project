/**
 * 
 */
package com.focalcxm.facedoc.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author focalcxm
 * @since 06/01/2021
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {

	private int id;
	private int doctorId;
	private String startDate;
	private String endDate;
	private String dayOfWeek;
	private String startTime;
	private String endTime;
	private int slotDuration;
	private String typeOfAppointment;
	private String createdDate;
	private String lastUpdatedDate;
	private String createdBy;
	private String lastUpdatedBy;
	 
}

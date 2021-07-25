/**
 * 
 */
package com.focalcxm.facedoc.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author focalCxm
 * @since 06/04/2021
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Slot {

	private int scheduleId;
	private int doctorId;
	private String date;
	private String slotStartTime;
	private String slotEndTime;
	private boolean isAvailable;
	private String createdDate;
	private String lastUpdatedDate;
	private String createdBy;
	private String lastUpdatedBy;
}

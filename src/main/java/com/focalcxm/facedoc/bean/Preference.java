/**
 * 
 */
package com.focalcxm.facedoc.bean;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author focalcxm
 * @since 5/30/2021
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Preference {
	
	private int id;
	private int userId;
	private int slotDuration;
	private int workingHours;
	private BigDecimal consultationFee;
	private int generateSlotNumOfMonths;
	private boolean availableSunday;
	private boolean availableSaturday;
	private String createdDate;
	private String lastUpdatedDate;
	private String createdBy;
	private String lastUpdatedBy;

}

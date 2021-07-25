/**
 * 
 */
package com.focalcxm.facedoc.dao;

import java.util.List;

import com.focalcxm.facedoc.bean.Slot;

/**
 * @author focalCXM
 * @since 06/07/2021
 *
 */
public interface SlotsDao {
	
	public void insertSlots(List<Slot> slots)  throws Exception ;

}

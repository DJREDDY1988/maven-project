/**
 * 
 */
package com.focalcxm.facedoc.daoimpl;

import java.util.Comparator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.focalcxm.facedoc.bean.Slot;
import com.focalcxm.facedoc.dao.SlotsDao;
import com.focalcxm.facedoc.queries.FaceDocDrQueries;

/**
 * @author focalCXM
 * @since 06/07/2021
 *
 */
@Component
public class SlotsDaoImpl implements SlotsDao{

	Logger log = LogManager.getLogger(SlotsDaoImpl.class);

	@Autowired
	private JdbcTemplate mysqlTemplate;

	@Override
	public void insertSlots(List<Slot> slots) throws Exception {
		try {
			slots.stream().sorted(Comparator.comparing(Slot::getDoctorId).
					thenComparing(Comparator.comparing(Slot::getDate))).forEach(slot->{
						String insertSlot = FaceDocDrQueries.insertSlotQuery(slot);
						mysqlTemplate.execute(insertSlot);
					});
		}catch(Exception e) {
			log.error("Exception occured while processing insertSlots()::SlotsDaoImpl "+e.getMessage());
			throw new Exception(e.getMessage());
		}
	}

}

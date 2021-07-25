package com.focalcxm.facedoc.daoimpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.focalcxm.facedoc.bean.User;
import com.focalcxm.facedoc.dao.UserDao;
import com.focalcxm.facedoc.queries.FaceDocDrQueries;

@Component
public class UserDaoImpl implements UserDao {
	
	Logger log = LogManager.getLogger(SlotsDaoImpl.class);
	
	@Autowired
	private JdbcTemplate mysqlTemplate;

	@Override
	public void addUser(User user) throws Exception {
		
		String addUserSql = FaceDocDrQueries.getAddUserQuery(user);
		
		try {
			mysqlTemplate.execute(addUserSql);
		}catch(Exception e) {
			log.error("Exception occured while processing addUser() ::UserDaoImpl "+e.getMessage());
			throw new Exception(e.getMessage());
		}
		
	}

}

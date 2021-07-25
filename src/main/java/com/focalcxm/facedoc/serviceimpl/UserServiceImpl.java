package com.focalcxm.facedoc.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.focalcxm.facedoc.bean.User;
import com.focalcxm.facedoc.dao.UserDao;
import com.focalcxm.facedoc.service.UserService;


/**
 * @author focalcxm
 * @since 06/09/2021
 *
 */

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDaoImpl;
	

	@Override
	public void addUser(User user) throws Exception {
		userDaoImpl.addUser(user);
	}

}

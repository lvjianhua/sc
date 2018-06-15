package org.sc.jobs.services.impl;

import org.sc.facade.ps.model.table.User;
import org.sc.jobs.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 用户
 *
 * @author lv
 * @create 2017-09-15 11:27
 **/
@Service
public class UserServiceImpl implements UserService {
	private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private org.sc.facade.ps.service.UserService userService;
	
	@Override
	public User findByUserName(String userName) {
		System.out.println("进入监听器");
		return userService.findByUserName(userName);
	}

}

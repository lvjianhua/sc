package org.sc.api.ps.service.impl;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.sc.facade.ps.model.table.User;
import org.sc.api.ps.service.UserApiService;
import org.sc.facade.ps.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserApiService {
	@Autowired
	private UserService UserService;

	@Override
	public List<User> getUsers() {
		List<User> Users = UserService.getUsers();
		return Users;
	}

	@Override
	public User getById(String id) {
		return UserService.getById(id);
	}

	@Override
	public User update(User object) {
		return UserService.update(object);
	}

	@Override
	public User save(User object) {		
		return UserService.save(object);
	}
}

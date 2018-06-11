package org.sc.api.ps.service.impl;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.sc.facade.ps.model.table.User;
import org.sc.api.ps.model.param.Login;
import org.sc.api.ps.service.UserApiService;
import org.sc.common.service.BaseService;
import org.sc.facade.ps.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl extends BaseService implements UserApiService {
	@Autowired
	private UserService userService;

	@Override
	public List<User> getUsers() {
		List<User> Users = userService.getUsers();
		return Users;
	}

	@Override
	public User getById(String id) {
		return userService.getById(id);
	}

	@Override
	public User update(User object) {
		return userService.update(object);
	}

	@Override
	public User save(User object) {		
		return userService.save(object);
	}
	
    @Override
    public User login(Login login) {
        User user = new User();
        mapper.map(login, user);
        return userService.login(user);
    }
}

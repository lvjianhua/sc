package org.sc.service.ps.service;

import java.util.List;

import org.sc.common.dao.service.BaseLogicServiceImpl;
import org.sc.service.ps.dao.UserDao;
import org.sc.facade.ps.model.table.User;
import org.sc.facade.ps.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl extends BaseLogicServiceImpl<User> implements UserService{
	public UserServiceImpl(@Autowired UserDao UserRepository) {
		super(UserRepository);
		this.UserRepository = UserRepository;
	}
	@Autowired
	private UserDao UserRepository;
	
	@Override
	public List<User> getUsers() {
		List<User> Users = UserRepository.findAll();
		return Users;
	}

	@Override
	public User findByUserName(String userName) {
		return UserRepository.findByUserName(userName);
	}

}

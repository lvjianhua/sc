package org.sc.service.ps.service;

import java.util.ArrayList;
import java.util.List;

import org.sc.common.dao.service.BaseLogicServiceImpl;
import org.sc.common.enmus.DelEnum;
import org.sc.common.exception.ServiceRunTimeException;
import org.sc.common.utils.ShaUtils;
import org.sc.service.ps.dao.UserDao;
import org.sc.service.ps.enums.ServiceErrorCode;
import org.sc.facade.ps.constant.UserStatus;
import org.sc.facade.ps.model.table.User;
import org.sc.facade.ps.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl extends BaseLogicServiceImpl<User> implements UserService{
	public UserServiceImpl(@Autowired UserDao userDao) {
		super(userDao);
		this.userDao = userDao;
	}
	@Autowired
	private UserDao userDao;
		
	@Override
	public List<User> getUsers() {
		List<User> Users = userDao.findAll();
		return Users;
	}

	@Override
	public User findByUserName(String userName) {
		return userDao.findByUserName(userName);
	}

    @Override
    public User login(User user) {
        List<User> users = new ArrayList<>();
        users = userDao.loginByUserName(user.getUserName(), ShaUtils.sha512(user.getPassword()));
        if (users.size() == 0) {//手机号和密码登录
            User queryParam = new User();
            queryParam.setDel(DelEnum.VALID.getType());
            queryParam.setPassword(ShaUtils.sha512(user.getPassword()));
            queryParam.setPhone(user.getUserName());
            users = userDao.findAll(Example.of(queryParam));
        }
       
        if (users.size() == 0) {
            throw new ServiceRunTimeException(ServiceErrorCode.WRONG_LOGIN);
        } else if (users.size() > 1) {
            throw new ServiceRunTimeException(ServiceErrorCode.WRONG_ACCOUNT);
        }

        User returnUser = users.get(0);
        if (UserStatus.FROZEN == returnUser.getStatus()) {
            throw new ServiceRunTimeException(ServiceErrorCode.FROZEN_ACCOUNT);
        }
        if (UserStatus.NO_MAIL == returnUser.getStatus()) {
            throw new ServiceRunTimeException(ServiceErrorCode.NEED_CHECK_EMAIL);
        }
        return returnUser;
    }
}

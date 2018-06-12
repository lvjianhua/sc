package org.sc.api.ps.service.impl;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.sc.facade.ps.model.attribute.UserExtendAttribute;
import org.sc.facade.ps.model.table.User;
import org.sc.api.ps.enums.ServiceErrorCode;
import org.sc.api.ps.model.param.Login;
import org.sc.api.ps.model.param.Register;
import org.sc.api.ps.service.UserApiService;
import org.sc.common.exception.ServiceRunTimeException;
import org.sc.common.service.BaseService;
import org.sc.common.utils.DES;
import org.sc.common.utils.StringUtils;
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
    
    @Override
    public void register(Register register) {
        User user = new User();
        // 校验
/*      if (StringUtils.isBlank(register.getPhone()) || StringUtils.isBlank(register.getMessageCode())) {
            throw new ServiceRunTimeException(ServiceErrorCode.WRONG_DATA);
        }
        // 判断验证码
        if (!messageCodeService.checkMessageCode(register.getPhone(), register.getMessageCode())) {
            throw new ServiceRunTimeException(ServiceErrorCode.WRONG_MESSAGE_CODE);
        }*/
        user.setUserName(register.getPhone());       
        mapper.map(register, user);
/*        if (StringUtils.isNotBlank(register.getWxOpenId())) {
            UserExtendAttribute extendAttribute = new UserExtendAttribute();
            extendAttribute.setWxOpenId(DES.decrypt(register.getWxOpenId(), userProp.getOpenIdDESKey()));
            user.setExtendAttribute(extendAttribute);
        }*/
        String userId = userService.addUser(user);
    }
    
    @Override
    public void checkUserAttribute(User user) {
        userService.checkUserAttribute(user);
    }
}

package org.sc.api.ps.service;

import java.util.List;

import org.sc.facade.ps.model.table.User;
import org.sc.api.ps.model.param.Login;
import org.sc.api.ps.model.param.Register;
import org.sc.common.service.BaseLogicService;

/**
 * User接口
 * 
 * @author lvjh
 *
 */
public interface UserApiService  extends BaseLogicService<User> {

	/**
	 * 获取用户列表
	 * 
	 * @return
	 */
	List<User> getUsers();
	
    /**
     * 登录
     *
     * @param login
     * @return
     */
    User login(Login login);

    /**
     * 注册账号
     *
     * @param register
     */
    void register(Register register);   
    
    /**
     * 检测用户用户名或手机或邮箱是否重复
     *
     * @param user
     */
    void checkUserAttribute(User user);
}

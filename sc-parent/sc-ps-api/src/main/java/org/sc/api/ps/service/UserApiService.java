package org.sc.api.ps.service;

import java.util.List;

import org.sc.facade.ps.model.table.User;
import org.sc.api.ps.model.param.Login;
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

}

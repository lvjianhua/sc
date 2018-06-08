package org.sc.api.ps.service;

import java.util.List;

import org.sc.facade.ps.model.table.User;
import org.sc.common.service.BaseLogicService;

/**
 * User接口
 * 
 * @author lvjh
 *
 */
public interface UserApiService  extends BaseLogicService<User> {

	List<User> getUsers();

}

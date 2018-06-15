package org.sc.jobs.services;

import org.sc.facade.ps.model.table.User;

/**
 * @author lv
 * @create 2017-09-15 11:26
 **/
public interface UserService {

    /**
     * 查询用户的信息
     */
    public User findByUserName(String userName);
}

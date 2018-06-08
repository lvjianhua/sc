package org.sc.service.ps.dao;

import org.sc.facade.ps.model.table.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
/**
 * User接口
 * 
 * @author lvjh
 *
 */
public interface UserDao extends JpaRepository<User, String>,JpaSpecificationExecutor<User> {
    /**
     * 按名称查询
     * 
     * @param name
     * @return
     */
	public User findByUserName(@Param(value="userName")String userName);
	
}

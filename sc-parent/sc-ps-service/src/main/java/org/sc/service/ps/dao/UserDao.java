package org.sc.service.ps.dao;

import java.util.List;

import org.sc.facade.ps.model.table.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
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
	
    @Query(value = "SELECT * FROM \"user\" WHERE del=0 AND extend_attribute->>'wxOpenId' = :wxOpenId", nativeQuery = true)
    List<User> getByOpenId(@Param("wxOpenId") String wxOpenId);
    
    @Query(value = "SELECT * FROM \"user\" WHERE del = 0 AND LOWER(user_name) = LOWER(:userName) AND password = :password", nativeQuery = true)
    List<User> loginByUserName(@Param("userName") String userName, @Param("password") String password);
 
    @Query(value = "SELECT COUNT(*) FROM \"user\" WHERE del = 0 AND LOWER(user_name) = LOWER(:userName)", nativeQuery = true)
    Integer checkUserName(@Param("userName") String userName);

}

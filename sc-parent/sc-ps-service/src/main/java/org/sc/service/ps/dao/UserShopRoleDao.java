package org.sc.service.ps.dao;

import org.sc.facade.ps.model.table.UserShopRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 用户与角色关系dao
 * Created by Sonic Wang on 2017/6/13.
 */
public interface UserShopRoleDao extends JpaRepository<UserShopRole, String> {

    @Query(value = "SELECT DISTINCT shop_id FROM user_shop_role " +
            "WHERE user_id = :userId AND shop_id IS NOT NULL " +
            "AND del = 0", nativeQuery = true)
    List<String> getShopByUser(@Param("userId") String userId);

    @Query(value = "SELECT DISTINCT shop_id FROM user_shop_role " +
            "WHERE user_id = :userId AND shop_id IS NOT NULL " +
            "AND del = 0 AND role_id = :roleId", nativeQuery = true)
    List<String> getOwnerShop(@Param("userId") String userId
            , @Param("roleId") String roleId);

    @Query(value = "SELECT DISTINCT user_id FROM user_shop_role " +
            "WHERE role_id = :roleId AND shop_id IS NOT NULL AND del = 0", nativeQuery = true)
    List<String> getShopUsers(@Param("roleId") String roleId);

    @Modifying
    @Query(value = "UPDATE UserShopRole u SET u.del = 1 WHERE u.userId = :userId AND u.shopId is null")
    int deleteUserRoles(@Param("userId") String userId);

    @Query(value = "FROM UserShopRole WHERE userId = :userId AND shopId IS NOT NULL " +
            "AND del = 0 AND roleId = :roleId")
    List<UserShopRole> getEntityByOwner(@Param("userId") String userId
            , @Param("roleId") String roleId);

    @Query(value = "FROM UserShopRole " +
            "WHERE userId = :userId AND shopId IS NOT NULL AND del = 0")
    List<UserShopRole> getEntityByUser(@Param("userId") String userId);
}

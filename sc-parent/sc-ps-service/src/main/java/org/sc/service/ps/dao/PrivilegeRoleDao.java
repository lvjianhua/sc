package org.sc.service.ps.dao;

import org.sc.facade.ps.model.table.PrivilegeRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Sonic Wang on 2017/7/4.
 */
public interface PrivilegeRoleDao extends JpaRepository<PrivilegeRole, String>{

    @Modifying
    @Query(value = "UPDATE privilege_role SET del=1 FROM privilege b " +
            "WHERE privilege_role.privilege_id=b.id AND b.url IS NULL AND role_id = :roleId", nativeQuery = true)
    int deleteRoleMenus(@Param("roleId") String roleId);

    @Modifying
    @Query(value = "UPDATE privilege_role SET del=1 FROM privilege b " +
            "WHERE privilege_role.privilege_id=b.id AND b.url IS NOT NULL AND role_id = :roleId", nativeQuery = true)
    int deleteRoleRestful(@Param("roleId") String roleId);

    @Modifying
    @Query(value = "UPDATE PrivilegeRole SET del = 1 WHERE privilegeId = :privilegeId")
    int deleteByPrivilege(@Param("privilegeId") String privilegeId);

    @Query(value = "SELECT DISTINCT roleId FROM PrivilegeRole WHERE del=0 AND privilegeId = :privilegeId")
    List<String> getPrivilegeRoles(@Param("privilegeId") String privilegeId);

}

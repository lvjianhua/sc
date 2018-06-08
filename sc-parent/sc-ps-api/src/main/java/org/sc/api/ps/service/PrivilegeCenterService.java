package org.sc.api.ps.service;

import org.sc.api.ps.model.vo.IdNameVo;
import org.sc.api.ps.model.vo.PrivilegeVo;
import org.sc.common.model.vo.Page;
import org.sc.facade.ps.model.param.QueryPrivilege;
import org.sc.facade.ps.model.param.RolePrivilege;
import org.sc.facade.ps.model.param.UserRole;
import org.sc.facade.ps.model.table.Privilege;
import org.sc.facade.ps.model.table.Role;

import java.util.List;
import java.util.Set;

/**
 * Created by Sonic Wang on 2017/8/24.
 */
public interface PrivilegeCenterService {

    /**
     * 获取所有可分配角色
     *
     * @return
     */
    List<Role> getAllRoles();

    /**
     * 设置用户的角色
     *
     * @param userRole
     */
    void setUserRoles(UserRole userRole);

    /**
     * 获取所有可分配权限
     *
     * @param queryPrivilege
     */
    Page<PrivilegeVo> getAllPrivileges(QueryPrivilege queryPrivilege);

    /**
     * 设置角色的权限
     *
     * @param rolePrivilege
     */
    void setRolePrivileges(RolePrivilege rolePrivilege);

    /**
     * 新增角色角色
     *
     * @param role
     */
    void addRole(Role role);

    /**
     * 新增权限
     *
     * @param privilege
     */
    void addPrivilege(Privilege privilege);

    /**
     * 修改角色
     *
     * @param role
     */
    void updateRole(Role role);

    /**
     * 修改权限
     *
     * @param privilege
     */
    void updatePrivilege(Privilege privilege);

    /**
     * 删除角色及其关联
     *
     * @param id
     */
    void deleteRole(String id);

    /**
     * 删除权限
     *
     * @param id
     */
    void deletePrivilege(String id);

    /**
     * 获取用户的角色
     *
     * @param userId
     * @return
     */
    List<Role> getUserRoles(String userId);

    /**
     * 获取角色的权限
     *
     * @param roleId
     * @param isMenu
     * @return
     */
    List<Privilege> getRolePrivileges(String roleId, Boolean isMenu);

    /**
     * 获取用户的菜单权限
     *
     * @return
     */
    Set<Privilege> getUserMenuPrivileges();

    /**
     * 根据角色编号获取用户
     *
     * @param roleCode
     * @return
     */
    List<IdNameVo> getUserByRoleCode(String roleCode);

    /**
     * 获取未登录可以访问的权限
     *
     * @return
     */
    List<Privilege> getAnonymousPrivileges();

}

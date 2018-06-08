package org.sc.facade.ps.service;

import org.sc.common.service.BaseLogicService;
import org.sc.facade.ps.urls.PrivilegeRoleUrls;
import org.sc.facade.ps.model.param.RolePrivilege;
import org.sc.facade.ps.model.table.PrivilegeRole;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by Sonic Wang on 2017/8/25.
 */
@FeignClient(value = "ps-service", path = "/privilegeRole")
public interface PrivilegeRoleService extends BaseLogicService<PrivilegeRole> {

    /**
     * 设置角色的权限
     *
     * @param rolePrivilege
     */
    @RequestMapping(value = PrivilegeRoleUrls.SET_ROLE_PRIVILEGES, method = {RequestMethod.POST})
    Integer setRolePrivileges(@RequestBody RolePrivilege rolePrivilege);

    /**
     * 根据条件查询角色权限列表
     *
     * @param privilegeRole
     * @return
     */
    @RequestMapping(value = PrivilegeRoleUrls.GET_BY_ENTITY, method = {RequestMethod.POST})
    List<PrivilegeRole> getByEntity(@RequestBody PrivilegeRole privilegeRole);

    /**
     * 通过权限id删除角色权限的关联关系
     *
     * @param privilegeId
     */
    @RequestMapping(value = PrivilegeRoleUrls.DELETE_BY_PRIVILEGE, method = {RequestMethod.POST})
    String deleteByPrivilege(@RequestParam("privilegeId") String privilegeId);

    /**
     * 根据权限查出所有角色，不传查出所有权限及对应的角色(角色去重)
     *
     * @param privilegeId
     * @return
     */
    @RequestMapping(value = PrivilegeRoleUrls.GET_PRIVILEGE_ROLES, method = {RequestMethod.GET})
    List<String> getPrivilegeRoles(@RequestParam("privilegeId") String privilegeId);
}

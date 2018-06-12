package org.sc.facade.ps.service;

import org.sc.common.model.vo.Page;
import org.sc.common.service.BaseLogicService;
import org.sc.facade.ps.model.param.QueryPrivilege;
import org.sc.facade.ps.model.table.Privilege;
import org.sc.facade.ps.model.vo.RolePrivilegeVo;
import org.sc.facade.ps.urls.PrivilegeUrls;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 权限接口
 * Created by Sonic Wang on 2017/4/26.
 */
@FeignClient(value = "ps-service", path = "/privilege")
public interface PrivilegeService extends BaseLogicService<Privilege> {

    /**
     * 获取所有角色和角色下的权限
     *
     * @return
     */
    @RequestMapping(value = PrivilegeUrls.GET_ROLE_PRIVILEGE, method = {RequestMethod.GET})
    List<RolePrivilegeVo> getRolePrivilege();

    /**
     * 获取所有可分配权限
     *
     * @param queryPrivilege
     * @return
     */
    @RequestMapping(value = PrivilegeUrls.GET_ALL_PRIVILEGES, method = {RequestMethod.POST})
    Page<Privilege> getAllPrivileges(@RequestBody QueryPrivilege queryPrivilege);

    /**
     * 添加权限
     *
     * @param privilege
     */
    @RequestMapping(value = PrivilegeUrls.ADD_PRIVILEGE, method = {RequestMethod.POST})
    String addPrivilege(@RequestBody Privilege privilege);

    /**
     * 获取角色的权限
     *
     * @param roleId
     * @param isMenu true只查菜单，false接口
     * @return
     */
    @RequestMapping(value = PrivilegeUrls.GET_BY_ROLEID, method = {RequestMethod.GET})
    List<Privilege> getByRoleId(@RequestParam("roleId") String roleId
            , @RequestParam("isMenu") boolean isMenu);

    /**
     * 删除权限及角色权限关系
     * @param privilege
     */
    @RequestMapping(value = PrivilegeUrls.DELETE_PRIVILEGE, method = {RequestMethod.POST})
    String deletePrivilege(Privilege privilege);
}

package org.sc.api.ps.controller;

import org.sc.api.ps.enums.ServiceErrorCode;
import org.sc.api.ps.model.param.IdParam;
import org.sc.api.ps.model.vo.IdNameVo;
import org.sc.api.ps.model.vo.PrivilegeVo;
import org.sc.api.ps.service.PrivilegeCenterService;
import org.sc.common.controller.BaseController;
import org.sc.common.model.vo.Page;
import org.sc.common.model.vo.Response;
import org.sc.common.utils.StringUtils;
import org.sc.common.utils.web.ResponseUtil;
import org.sc.facade.ps.model.param.QueryPrivilege;
import org.sc.facade.ps.model.param.RolePrivilege;
import org.sc.facade.ps.model.param.UserRole;
import org.sc.facade.ps.model.table.Privilege;
import org.sc.facade.ps.model.table.Role;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * Created by Sonic Wang on 2017/8/24.
 */
@Api(value = "PrivilegeCenterController", description = "权限控制中心api")
@RestController
@RequestMapping("/api/ps/privilegeCenter")
public class PrivilegeCenterController extends BaseController {

    @Autowired
    private PrivilegeCenterService privilegeCenterService;

    /**
     * 获取所有可分配角色
     *
     * @return
     */
    @ApiOperation(value = "获取所有可分配角色", httpMethod = "GET")
    @RequestMapping(value = "/getAllRoles", method = {RequestMethod.GET})
    public Response getAllRoles() {
        List<Role> roles = privilegeCenterService.getAllRoles();
        return ResponseUtil.ok(roles);
    }

    /**
     * 获取用户的角色
     *
     * @return
     */
    @ApiOperation(value = "获取用户的角色", httpMethod = "GET")
    @RequestMapping(value = "/getUserRoles", method = {RequestMethod.GET})
    public Response getUserRoles(String userId) {
        if (StringUtils.isBlank(userId)) {
            return ResponseUtil.error(ServiceErrorCode.WRONG_DATA);
        }
        List<Role> roles = privilegeCenterService.getUserRoles(userId);
        return ResponseUtil.ok(roles);
    }

    /**
     * 新增角色
     *
     * @return
     */
    @ApiOperation(value = "新增角色角色", httpMethod = "POST")
    @RequestMapping(value = "/addRole", method = {RequestMethod.POST})
    public Response addRole(@RequestBody Role role) {
        if (StringUtils.isBlank(role.getRoleName())
                || StringUtils.isBlank(role.getRoleCode())) {
            return ResponseUtil.error(ServiceErrorCode.WRONG_DATA);
        }
        privilegeCenterService.addRole(role);
        return ResponseUtil.ok(null, "添加成功！");
    }

    /**
     * 修改角色
     *
     * @return
     */
    @ApiOperation(value = "修改角色", httpMethod = "POST")
    @RequestMapping(value = "/updateRole", method = {RequestMethod.POST})
    public Response updateRole(@RequestBody Role role) {
        if (StringUtils.isBlank(role.getId())) {
            return ResponseUtil.error(ServiceErrorCode.WRONG_DATA);
        }
        privilegeCenterService.updateRole(role);
        return ResponseUtil.ok(null, "修改成功！");
    }

    /**
     * 删除角色及其关联
     *
     * @return
     */
    @ApiOperation(value = "删除角色及其关联", httpMethod = "POST")
    @RequestMapping(value = "/deleteRole", method = {RequestMethod.POST})
    public Response deleteRole(@RequestBody IdParam idParam) {
        if (StringUtils.isBlank(idParam.getId())) {
            return ResponseUtil.error(ServiceErrorCode.WRONG_DATA);
        }
        privilegeCenterService.deleteRole(idParam.getId());
        return ResponseUtil.ok(null, "删除成功！");
    }

    /**
     * 设置用户的角色
     *
     * @return
     */
    @ApiOperation(value = "设置用户的角色", httpMethod = "POST")
    @RequestMapping(value = "/setUserRoles", method = {RequestMethod.POST})
    public Response setUserRoles(@RequestBody UserRole userRole) {
        if (StringUtils.isBlank(userRole.getUserId())) {
            return ResponseUtil.error(ServiceErrorCode.WRONG_DATA);
        }
        privilegeCenterService.setUserRoles(userRole);
        return ResponseUtil.ok(null, "操作成功！");
    }

    /**
     * 获取所有可分配权限
     *
     * @return
     */
    @ApiOperation(value = "获取所有可分配权限", httpMethod = "POST")
    @RequestMapping(value = "/getAllPrivileges", method = {RequestMethod.POST})
    public Response getAllPrivileges(@RequestBody QueryPrivilege queryPrivilege) {
        Page<PrivilegeVo> allPrivileges = privilegeCenterService.getAllPrivileges(queryPrivilege);
        return ResponseUtil.ok(allPrivileges);
    }

    /**
     * 获取角色的权限
     *
     * @return
     */
    @ApiOperation(value = "获取角色的权限", httpMethod = "GET")
    @RequestMapping(value = "/getRolePrivileges", method = {RequestMethod.GET})
    public Response getRolePrivileges(String roleId, Boolean isMenu) {
        if (StringUtils.isBlank(roleId) || isMenu == null) {
            return ResponseUtil.error(ServiceErrorCode.WRONG_DATA);
        }
        List<Privilege> privileges = privilegeCenterService.getRolePrivileges(roleId, isMenu);
        return ResponseUtil.ok(privileges);
    }

    /**
     * 获取未登录可以访问的权限
     *
     * @return
     */
    @ApiOperation(value = "获取未登录可以访问的权限", httpMethod = "GET")
    @RequestMapping(value = "/getAnonymousPrivileges", method = {RequestMethod.GET})
    public Response getAnonymousPrivileges() {
        List<Privilege> privileges = privilegeCenterService.getAnonymousPrivileges();
        return ResponseUtil.ok(privileges);
    }

    /**
     * 获取用户的菜单权限
     *
     * @return
     */
    @ApiOperation(value = "获取用户的菜单权限", httpMethod = "GET")
    @RequestMapping(value = "/getUserMenuPrivileges", method = {RequestMethod.GET})
    public Response getUserMenuPrivileges() {
        Set<Privilege> privileges = privilegeCenterService.getUserMenuPrivileges();
        return ResponseUtil.ok(privileges);
    }

    /**
     * 新增权限
     *
     * @return
     */
    @ApiOperation(value = "新增权限", httpMethod = "POST")
    @RequestMapping(value = "/addPrivilege", method = {RequestMethod.POST})
    public Response addPrivilege(@RequestBody Privilege privilege) {
        if (StringUtils.isBlank(privilege.getPrivilegeName())) {
            return ResponseUtil.error(ServiceErrorCode.WRONG_DATA);
        }
        privilegeCenterService.addPrivilege(privilege);
        return ResponseUtil.ok(null, "添加成功！");
    }

    /**
     * 修改权限
     *
     * @return
     */
    @ApiOperation(value = "修改权限", httpMethod = "POST")
    @RequestMapping(value = "/updatePrivilege", method = {RequestMethod.POST})
    public Response updatePrivilege(@RequestBody Privilege privilege) {
        if (StringUtils.isBlank(privilege.getId())) {
            return ResponseUtil.error(ServiceErrorCode.WRONG_DATA);
        }
        privilegeCenterService.updatePrivilege(privilege);
        return ResponseUtil.ok(null, "修改成功！");
    }

    /**
     * 删除权限
     *
     * @return
     */
    @ApiOperation(value = "删除权限", httpMethod = "POST")
    @RequestMapping(value = "/deletePrivilege", method = {RequestMethod.POST})
    public Response deletePrivilege(@RequestBody IdParam idParam) {
        if (StringUtils.isBlank(idParam.getId())) {
            return ResponseUtil.error(ServiceErrorCode.WRONG_DATA);
        }
        privilegeCenterService.deletePrivilege(idParam.getId());
        return ResponseUtil.ok(null, "删除权限成功！");
    }

    /**
     * 设置角色的权限
     *
     * @return
     */
    @ApiOperation(value = "设置角色的权限", httpMethod = "POST")
    @RequestMapping(value = "/setRolePrivileges", method = {RequestMethod.POST})
    public Response setRolePrivileges(@RequestBody RolePrivilege rolePrivilege) {
        if (StringUtils.isBlank(rolePrivilege.getRoleId())
                || rolePrivilege.getIsMenu() == null) {
            return ResponseUtil.error(ServiceErrorCode.WRONG_DATA);
        }
        privilegeCenterService.setRolePrivileges(rolePrivilege);
        return ResponseUtil.ok(null, "操作成功！");
    }

    /**
     * 根据角色编号获取用户
     *
     * @return
     */
    @ApiOperation(value = "根据角色编号获取用户", httpMethod = "GET")
    @RequestMapping(value = "/getUserByRoleCode", method = {RequestMethod.GET})
    public Response getUserByRoleCode(String roleCode) {
        if (StringUtils.isBlank(roleCode)) {
            return ResponseUtil.error(ServiceErrorCode.WRONG_DATA);
        }
        List<IdNameVo> idNameVos = privilegeCenterService.getUserByRoleCode(roleCode);
        return ResponseUtil.ok(idNameVos);
    }
}

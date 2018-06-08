package org.sc.service.ps.controller;

import org.sc.service.ps.enums.ServiceErrorCode;
import org.sc.common.model.vo.Response;
import org.sc.common.utils.StringUtils;
import org.sc.common.utils.web.ResponseUtil;
import org.sc.common.dao.controller.BaseLogicController;
import org.sc.facade.ps.model.param.RolePrivilege;
import org.sc.facade.ps.model.table.PrivilegeRole;
import org.sc.facade.ps.service.PrivilegeRoleService;
import org.sc.facade.ps.urls.PrivilegeRoleUrls;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Sonic Wang on 2017/8/25.
 */
@Api(value = "PrivilegeRoleController", description = "角色权限相关服务")
@RestController
@RequestMapping("/privilegeRole")
public class PrivilegeRoleController extends BaseLogicController<PrivilegeRole> {

    private PrivilegeRoleService privilegeRoleService;

    @Autowired
    public PrivilegeRoleController(PrivilegeRoleService privilegeRoleService) {
        super(privilegeRoleService);
        this.privilegeRoleService = privilegeRoleService;
    }

    /**
     * 设置角色的权限
     *
     * @return
     */
    @ApiOperation(value = "设置角色的权限", httpMethod = "POST")
    @RequestMapping(value = PrivilegeRoleUrls.SET_ROLE_PRIVILEGES, method = {RequestMethod.POST})
    public Response setRolePrivileges(@RequestBody RolePrivilege rolePrivilege) {
        if (StringUtils.isBlank(rolePrivilege.getRoleId())
                || rolePrivilege.getIsMenu() == null) {
            return ResponseUtil.error(ServiceErrorCode.WRONG_DATA);
        }
        privilegeRoleService.setRolePrivileges(rolePrivilege);
        return ResponseUtil.ok();
    }

    /**
     * 根据条件查询角色权限列表
     *
     * @return
     */
    @ApiOperation(value = "根据条件查询角色权限列表", httpMethod = "POST")
    @RequestMapping(value = PrivilegeRoleUrls.GET_BY_ENTITY, method = {RequestMethod.POST})
    public Response getByEntity(@RequestBody PrivilegeRole privilegeRole) {
        List<PrivilegeRole> privilegeRoles = privilegeRoleService.getByEntity(privilegeRole);
        return ResponseUtil.ok(privilegeRoles);
    }

    /**
     * 根据权限查出所有角色，不传查出所有权限及对应的角色(角色去重)
     *
     * @return
     */
    @ApiOperation(value = "根据权限查出所有角色，不传查出所有权限及对应的角色(角色去重)", httpMethod = "GET")
    @RequestMapping(value = PrivilegeRoleUrls.GET_PRIVILEGE_ROLES, method = {RequestMethod.GET})
    public Response getPrivilegeRoles(String privilegeId) {
        List<String> privilegeRoles = privilegeRoleService.getPrivilegeRoles(privilegeId);
        return ResponseUtil.ok(privilegeRoles);
    }

}

package org.sc.service.ps.controller;

import org.sc.common.model.vo.Page;
import org.sc.common.model.vo.Response;
import org.sc.common.utils.StringUtils;
import org.sc.common.utils.web.ResponseUtil;
import org.sc.common.dao.controller.BaseLogicController;
import org.sc.facade.ps.model.param.QueryPrivilege;
import org.sc.facade.ps.model.table.Privilege;
import org.sc.facade.ps.model.table.Role;
import org.sc.facade.ps.model.vo.RolePrivilegeVo;
import org.sc.facade.ps.service.PrivilegeService;
import org.sc.facade.ps.urls.PrivilegeUrls;
import org.sc.facade.ps.urls.RoleUrls;
import org.sc.service.ps.enums.ServiceErrorCode;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户角色权限控制层
 * Created by Sonic Wang on 2017/4/26.
 */
@Api(value = "PrivilegeController", description = "权限相关服务")
@RestController
@RequestMapping("/privilege")
public class PrivilegeController extends BaseLogicController<Privilege> {

    private PrivilegeService privilegeService;

    @Autowired
    public PrivilegeController(PrivilegeService privilegeService) {
        super(privilegeService);
        this.privilegeService = privilegeService;
    }

    /**
     * 获取所有角色和角色下的权限
     *
     * @return
     */
    @ApiOperation(value = "获取所有角色和角色下的权限", httpMethod = "GET")
    @RequestMapping(value = PrivilegeUrls.GET_ROLE_PRIVILEGE, method = {RequestMethod.GET})
    public Response getRolePrivilege() {
        List<RolePrivilegeVo> rolePrivilegeVos = privilegeService.getRolePrivilege();
        return ResponseUtil.ok(rolePrivilegeVos);
    }

    /**
     * 获取角色的权限
     * isMenu:true只查菜单，false全部
     * @return
     */
    @ApiOperation(value = "获取角色的权限", httpMethod = "GET")
    @RequestMapping(value = PrivilegeUrls.GET_BY_ROLEID, method = {RequestMethod.GET})
    public Response getByRoleId(String roleId, boolean isMenu) {
        List<Privilege> privileges = privilegeService.getByRoleId(roleId, isMenu);
        return ResponseUtil.ok(privileges);
    }

    /**
     * 获取所有可分配权限
     *
     * @return
     */
    @ApiOperation(value = "获取所有可分配权限", httpMethod = "POST")
    @RequestMapping(value = PrivilegeUrls.GET_ALL_PRIVILEGES, method = {RequestMethod.POST})
    public Response getAllPrivileges(@RequestBody QueryPrivilege queryPrivilege) {
        Page<Privilege> privileges = privilegeService.getAllPrivileges(queryPrivilege);
        return ResponseUtil.ok(privileges);
    }

    /**
     * 添加权限
     *
     * @return
     */
    @ApiOperation(value = "添加权限", httpMethod = "POST")
    @RequestMapping(value = PrivilegeUrls.ADD_PRIVILEGE, method = {RequestMethod.POST})
    public Response save(@RequestBody Privilege privilege) {
        if (StringUtils.isBlank(privilege.getPrivilegeName())) {
            return ResponseUtil.error(ServiceErrorCode.WRONG_DATA);
        }
        privilegeService.addPrivilege(privilege);
        return ResponseUtil.ok();
    }

    /**
     * 删除权限及角色权限关系
     *
     * @return
     */
    @ApiOperation(value = "删除权限及角色权限关系", httpMethod = "POST")
    @RequestMapping(value = PrivilegeUrls.DELETE_PRIVILEGE, method = {RequestMethod.POST})
    public Response deletePrivilege(@RequestBody Privilege privilege) {
        if (StringUtils.isBlank(privilege.getId())) {
            return ResponseUtil.error(ServiceErrorCode.WRONG_DATA);
        }
        privilegeService.deletePrivilege(privilege);
        return ResponseUtil.ok();
    }

}

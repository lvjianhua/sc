package org.sc.service.ps.controller;

import org.sc.common.model.vo.Response;
import org.sc.common.utils.StringUtils;
import org.sc.common.utils.web.ResponseUtil;
import org.sc.common.dao.controller.BaseLogicController;
import org.sc.facade.ps.model.table.Role;
import org.sc.facade.ps.service.RoleService;
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
 * 用户角色控制层
 * Created by Sonic Wang on 2017/6/12.
 */
@Api(value = "RoleController", description = "角色相关服务")
@RestController
@RequestMapping("/role")
public class RoleController extends BaseLogicController<Role> {

    private RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        super(roleService);
        this.roleService = roleService;
    }

    @ApiOperation(value = "获取上级角色获取下级角色，不传上级角色查出全部", httpMethod = "GET")
    @RequestMapping(value = RoleUrls.GET_ROLES, method = {RequestMethod.GET})
    public Response getRoles(String parentId) {
        List<Role> roles = roleService.getRoles(parentId);
        return ResponseUtil.ok(roles);
    }

    @ApiOperation(value = "根据条件获取有效角色", httpMethod = "POST")
    @RequestMapping(value = RoleUrls.GET_BY_ENTITY, method = {RequestMethod.POST})
    public Response getByEntity(@RequestBody Role role) {
        List<Role> roles = roleService.getByEntity(role);
        return ResponseUtil.ok(roles);
    }

    @ApiOperation(value = "获取用户的角色", httpMethod = "GET")
    @RequestMapping(value = RoleUrls.GET_BY_USER_ID, method = {RequestMethod.GET})
    public Response getByUserId(String userId) {
        if (StringUtils.isBlank(userId)) {
            return ResponseUtil.error(ServiceErrorCode.WRONG_DATA);
        }
        List<Role> roles = roleService.getByUserId(userId);
        return ResponseUtil.ok(roles);
    }

    /**
     * 新增角色角色
     *
     * @return
     */
    @ApiOperation(value = "新增角色角色", httpMethod = "POST")
    @RequestMapping(value = RoleUrls.ADD_ROLE, method = {RequestMethod.POST})
    public Response save(@RequestBody Role role) {
        if (StringUtils.isBlank(role.getRoleName())) {
            return ResponseUtil.error(ServiceErrorCode.WRONG_DATA);
        }
        roleService.addRole(role);
        return ResponseUtil.ok();
    }
}

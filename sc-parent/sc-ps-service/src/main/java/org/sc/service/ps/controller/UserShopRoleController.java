package org.sc.service.ps.controller;

import org.sc.common.model.vo.Response;
import org.sc.common.utils.StringUtils;
import org.sc.common.utils.web.ResponseUtil;
import org.sc.facade.ps.model.param.QueryShop;
import org.sc.facade.ps.model.param.UserRole;
import org.sc.facade.ps.model.table.User;
import org.sc.facade.ps.model.table.UserShopRole;
import org.sc.facade.ps.service.UserShopRoleService;
import org.sc.facade.ps.urls.UserShopRoleUrls;
import org.sc.common.dao.controller.BaseLogicController;
import org.sc.service.ps.enums.ServiceErrorCode;

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
 * Created by Sonic Wang on 2017/6/21.
 */
@Api(value = "UserShopRoleController", description = "用户店铺角色相关服务")
@RestController
@RequestMapping("/userShopRole")
public class UserShopRoleController extends BaseLogicController<UserShopRole> {

    private UserShopRoleService userShopRoleService;

    @Autowired
    public UserShopRoleController(UserShopRoleService userShopRoleService) {
        super(userShopRoleService);
        this.userShopRoleService = userShopRoleService;
    }

    @ApiOperation(value = "获取用户对应的角色 下所有用户的id(包括他自己)", httpMethod = "GET")
    @RequestMapping(value = UserShopRoleUrls.GET_ROLE_USERS, method = {RequestMethod.GET})
    public Response getRoleUsers(String userId) {
        if (StringUtils.isBlank(userId)) {
            return ResponseUtil.error(ServiceErrorCode.WRONG_DATA);
        }
        Set<String> userIds = userShopRoleService.getRoleUsers(userId);
        return ResponseUtil.ok(userIds);
    }

    @ApiOperation(value = "根据条件获取有效的用户店铺角色的列表", httpMethod = "POST")
    @RequestMapping(value = UserShopRoleUrls.GET_BY_ENTITY, method = {RequestMethod.POST})
    public Response getByEntity(@RequestBody UserShopRole userShopRole) {
        List<UserShopRole> userShopRoles = userShopRoleService.getByEntity(userShopRole);
        return ResponseUtil.ok(userShopRoles);
    }


    @ApiOperation(value = "添加用户店铺角色", httpMethod = "POST")
    @RequestMapping(value = UserShopRoleUrls.ADD_USER_SHOP_ROLE, method = {RequestMethod.POST})
    public Response save(@RequestBody UserShopRole userShopRole) {
        if (StringUtils.isBlank(userShopRole.getUserId()) || StringUtils.isBlank(userShopRole.getRoleId())) {
            return ResponseUtil.error(ServiceErrorCode.WRONG_DATA);
        }
        userShopRoleService.addUserShopRole(userShopRole);
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "检测用户和店铺关系", httpMethod = "GET")
    @RequestMapping(value = UserShopRoleUrls.CHECK_USER_SHOP, method = {RequestMethod.GET})
    public Response checkUserShop(String userId, String shopId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(shopId)) {
            return ResponseUtil.error(ServiceErrorCode.WRONG_DATA);
        }
        Boolean flag = userShopRoleService.checkUserShop(userId, shopId);
        return ResponseUtil.ok(flag);
    }

    @ApiOperation(value = "获取店铺的店主id", httpMethod = "GET")
    @RequestMapping(value = UserShopRoleUrls.GET_SHOP_OWNER, method = {RequestMethod.GET})
    public Response getShopOwner(String shopId) {
        if (StringUtils.isBlank(shopId)) {
            return ResponseUtil.error(ServiceErrorCode.WRONG_DATA);
        }
        String userId = userShopRoleService.getShopOwner(shopId);
        return ResponseUtil.ok(userId);
    }

    /**
     * 设置用户的角色
     *
     * @return
     */
    @ApiOperation(value = "设置用户的角色", httpMethod = "POST")
    @RequestMapping(value = UserShopRoleUrls.SET_USER_ROLES, method = {RequestMethod.POST})
    public Response setUserRoles(@RequestBody UserRole userRole) {
        if (StringUtils.isBlank(userRole.getUserId())) {
            return ResponseUtil.error(ServiceErrorCode.WRONG_DATA);
        }
        userShopRoleService.setUserRoles(userRole);
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "检测用户是否有该角色", httpMethod = "GET")
    @RequestMapping(value = "checkByRoleCode", method = {RequestMethod.GET})
    public Response checkByRoleCode(String userId, String roleCode) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(roleCode)) {
            return ResponseUtil.error(ServiceErrorCode.WRONG_DATA);
        }
        return ResponseUtil.ok(userShopRoleService.checkByRoleCode(userId, roleCode));
    }
}

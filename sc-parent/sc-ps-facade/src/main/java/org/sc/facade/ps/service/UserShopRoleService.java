package org.sc.facade.ps.service;

import org.sc.common.service.BaseLogicService;
import org.sc.facade.ps.model.param.UserRole;
import org.sc.facade.ps.model.table.User;
import org.sc.facade.ps.model.table.UserShopRole;
import org.sc.facade.ps.urls.UserShopRoleUrls;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

/**
 * 用户店铺角色服务接口
 * Created by Sonic Wang on 2017/6/21.
 */
@FeignClient(value = "ps-service", path = "/userShopRole")
public interface UserShopRoleService extends BaseLogicService<UserShopRole> {

    /**
     * 获取用户对应的角色 下所有用户的id(包括他自己)
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = UserShopRoleUrls.GET_ROLE_USERS, method = {RequestMethod.GET})
    Set<String> getRoleUsers(@RequestParam("userId") String userId);

    /**
     * 根据条件获取有效的用户店铺角色的列表
     *
     * @param userShopRole
     * @return
     */
    @RequestMapping(value = UserShopRoleUrls.GET_BY_ENTITY, method = {RequestMethod.POST})
    List<UserShopRole> getByEntity(@RequestBody UserShopRole userShopRole);

    /**
     * 添加用户店铺角色
     *
     * @param userShopRole
     * @return
     */
    @RequestMapping(value = UserShopRoleUrls.ADD_USER_SHOP_ROLE, method = {RequestMethod.POST})
    String addUserShopRole(@RequestBody UserShopRole userShopRole);

    /**
     * 检测用户和店铺关系
     *
     * @param userId
     * @param shopId
     * @return
     */
    @RequestMapping(value = UserShopRoleUrls.CHECK_USER_SHOP, method = {RequestMethod.GET})
    Boolean checkUserShop(@RequestParam("userId") String userId, @RequestParam("shopId") String shopId);

    /**
     * 获取店铺的店主id
     *
     * @param shopId
     * @return
     */
    @RequestMapping(value = UserShopRoleUrls.GET_SHOP_OWNER, method = {RequestMethod.GET})
    String getShopOwner(@RequestParam("shopId") String shopId);

    /**
     * 去重获取有店铺的用户id
     *
     * @return
     */
    @RequestMapping(value = UserShopRoleUrls.GET_SHOP_USERS, method = {RequestMethod.GET})
    List<String> getShopUsers();

    /**
     * 设置用户的角色
     *
     * @param userRole
     */
    @RequestMapping(value = UserShopRoleUrls.SET_USER_ROLES, method = {RequestMethod.POST})
    Integer setUserRoles(@RequestBody UserRole userRole);

    /**
     * 检测用户是否有该角色
     *
     * @param userId
     * @param roleCode
     * @return
     */
    @RequestMapping(value = "checkByRoleCode", method = {RequestMethod.GET})
    boolean checkByRoleCode(@RequestParam("userId") String userId, @RequestParam("roleCode") String roleCode);
}

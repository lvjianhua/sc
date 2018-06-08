package org.sc.facade.ps.service;

import org.sc.common.service.BaseLogicService;
import org.sc.facade.ps.model.table.Role;
import org.sc.facade.ps.urls.RoleUrls;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 角色接口
 * Created by Sonic Wang on 2017/6/12.
 */
@FeignClient(value = "ps-service", path = "/role")
public interface RoleService extends BaseLogicService<Role> {

    /**
     * 获取所有角色
     *
     * @param parentId
     * @return
     */
    @RequestMapping(value = RoleUrls.GET_ROLES, method = {RequestMethod.GET})
    List<Role> getRoles(@RequestParam("parentId") String parentId);

    /**
     * 根据条件获取有效角色
     *
     * @param role
     * @return
     */
    @RequestMapping(value = RoleUrls.GET_BY_ENTITY, method = {RequestMethod.POST})
    List<Role> getByEntity(@RequestBody Role role);

    /**
     * 获取用户的角色
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = RoleUrls.GET_BY_USER_ID, method = {RequestMethod.GET})
    List<Role> getByUserId(@RequestParam("userId") String userId);

    /**
     * 新增角色角色
     *
     * @param role
     */
    @RequestMapping(value = RoleUrls.ADD_ROLE, method = {RequestMethod.POST})
    String addRole(@RequestBody Role role);
}

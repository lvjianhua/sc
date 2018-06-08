package org.sc.service.ps.service;

import org.sc.common.enmus.DelEnum;
import org.sc.common.exception.ServiceRunTimeException;
import org.sc.facade.ps.constant.RoleCode;
import org.sc.facade.ps.model.param.QueryShop;
import org.sc.facade.ps.model.param.UserRole;
import org.sc.facade.ps.model.table.Role;
import org.sc.facade.ps.model.table.User;
import org.sc.facade.ps.model.table.UserShopRole;
import org.sc.facade.ps.service.RoleService;
import org.sc.facade.ps.service.UserService;
import org.sc.facade.ps.service.UserShopRoleService;
import org.sc.common.dao.service.BaseLogicServiceImpl;
import org.sc.service.ps.dao.UserShopRoleDao;
import org.sc.service.ps.enums.ServiceErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Sonic Wang on 2017/6/21.
 */
@Service("userShopRoleService")
public class UserShopRoleServiceImpl extends BaseLogicServiceImpl<UserShopRole> implements UserShopRoleService {

    private UserShopRoleDao userShopRoleDao;

    @Autowired
    public UserShopRoleServiceImpl(UserShopRoleDao userShopRoleDao) {
        super(userShopRoleDao);
        this.userShopRoleDao = userShopRoleDao;
    }

    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;

    @Override
    public Set<String> getRoleUsers(String userId) {
        UserShopRole queryParam = new UserShopRole();
        queryParam.setUserId(userId);
        List<UserShopRole> userShopRoles = getByEntity(queryParam);
        Set<String> userIds = new TreeSet<>();

        for (UserShopRole userShopRole : userShopRoles) {
            UserShopRole queryUsers = new UserShopRole();
            queryUsers.setDel(DelEnum.VALID.getType());
            queryUsers.setRoleId(userShopRole.getRoleId());
            List<UserShopRole> users = userShopRoleDao.findAll(Example.of(queryUsers));
            users.forEach((user) -> userIds.add(user.getUserId()));
        }
        return userIds;
    }

    /**
     * 根据条件获取有效的用户店铺角色的列表
     *
     * @param userShopRole
     * @return
     */
    @Override
    public List<UserShopRole> getByEntity(UserShopRole userShopRole) {
        userShopRole.setDel(DelEnum.VALID.getType());
        List<UserShopRole> userShopRoles = userShopRoleDao.findAll(Example.of(userShopRole));
        return userShopRoles;
    }

    /**
     * 获取店主角色的id
     *
     * @return
     */
    private Role getShopOwnerRole() {
        Role queryRole = new Role();
        queryRole.setRoleCode(RoleCode.SHOP_OWNER);
        queryRole.setDel(DelEnum.VALID.getType());
        return roleService.getByEntity(queryRole).get(0);
    }

    @Override
    public String addUserShopRole(UserShopRole userShopRole) {
        if (!checkRepeatUserShopRole(userShopRole)) {
            throw new ServiceRunTimeException(ServiceErrorCode.REPEAT_DATA);
        }
        UserShopRole returnUserShopRole = save(userShopRole);
        if (returnUserShopRole == null) {
            throw new ServiceRunTimeException(ServiceErrorCode.WRONG_ADD);
        }
        return returnUserShopRole.getId();
    }

    @Override
    public Boolean checkUserShop(String userId, String shopId) {
        UserShopRole checkParam = new UserShopRole();
        checkParam.setUserId(userId);
        checkParam.setShopId(shopId);
        checkParam.setDel(DelEnum.VALID.getType());
        if (userShopRoleDao.exists(Example.of(checkParam))) {
            return true;
        }
        return false;
    }

    @Override
    public String getShopOwner(String shopId) {
        Role queryRole = new Role();
        queryRole.setRoleCode(RoleCode.SHOP_OWNER);
        queryRole.setDel(DelEnum.VALID.getType());
        Role returnRole = roleService.getByEntity(queryRole).get(0);

        UserShopRole queryParam = new UserShopRole();
        queryParam.setShopId(shopId);
        queryParam.setRoleId(returnRole.getId());
        queryParam.setDel(DelEnum.VALID.getType());
        UserShopRole userShopRole = userShopRoleDao.findOne(Example.of(queryParam));
        if (userShopRole == null) {
            throw new ServiceRunTimeException(ServiceErrorCode.NO_SHOP_OWNER);
        }
        return userShopRole.getUserId();
    }

    @Override
    public List<String> getShopUsers() {
        Role queryRole = new Role();
        queryRole.setRoleCode(RoleCode.SHOP_OWNER);
        queryRole.setDel(DelEnum.VALID.getType());
        Role returnRole = roleService.getByEntity(queryRole).get(0);

        return userShopRoleDao.getShopUsers(returnRole.getId());
    }

    @Transactional
    @Override
    public Integer setUserRoles(UserRole userRole) {
        userShopRoleDao.deleteUserRoles(userRole.getUserId());

        List<UserShopRole> userShopRoles = new ArrayList<>();
        for (String roleId : userRole.getRoleIds()) {
            UserShopRole userShopRole = new UserShopRole();
            userShopRole.setUserId(userRole.getUserId());
            userShopRole.setRoleId(roleId);
            userShopRoles.add(userShopRole);
        }

        userShopRoleDao.save(userShopRoles);
        return userShopRoles.size();
    }

    @Override
    public boolean checkByRoleCode(String userId, String roleCode) {
        Role queryRole = new Role();
        queryRole.setRoleCode(roleCode);
        queryRole.setDel(DelEnum.VALID.getType());
        Role returnRole = roleService.getByEntity(queryRole).get(0);

        UserShopRole checkParam = new UserShopRole();
        checkParam.setUserId(userId);
        checkParam.setRoleId(returnRole.getId());
        checkParam.setDel(DelEnum.VALID.getType());
        if (userShopRoleDao.exists(Example.of(checkParam))) {
            return true;
        }
        return false;
    }

    /**
     * 检测用户店铺角色是否重复
     *
     * @param userShopRole
     * @return
     */
    private boolean checkRepeatUserShopRole(UserShopRole userShopRole) {
        if (userShopRoleDao.exists(Example.of(userShopRole))) {
            return false;
        }
        return true;
    }
}

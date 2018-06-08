package org.sc.service.ps.service;

import org.sc.common.enmus.DelEnum;
import org.sc.common.exception.ServiceRunTimeException;
import org.sc.common.utils.StringUtils;
import org.sc.facade.ps.model.table.Role;
import org.sc.facade.ps.model.table.UserShopRole;
import org.sc.facade.ps.service.RoleService;
import org.sc.facade.ps.service.UserShopRoleService;
import org.sc.common.dao.service.BaseLogicServiceImpl;
import org.sc.service.ps.dao.RoleDao;
import org.sc.service.ps.enums.ServiceErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * 角色业务实现
 * Created by Sonic Wang on 2017/6/12.
 */
@Service("roleService")
public class RoleServiceImpl extends BaseLogicServiceImpl<Role> implements RoleService {

    private RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        super(roleDao);
        this.roleDao = roleDao;
    }

    @Autowired
    private UserShopRoleService userShopRoleService;

    @Override
    public List<Role> getRoles(String parentId) {
        Role queryParam = new Role();
        if (StringUtils.isNotBlank(parentId)) {
            queryParam.setParentId(parentId);
        }
        queryParam.setDel(DelEnum.VALID.getType());
        return roleDao.findAll(Example.of(queryParam), new Sort(Sort.Direction.ASC, "createTime"));
    }

    @Override
    public List<Role> getByEntity(Role role) {
        role.setDel(DelEnum.VALID.getType());
        return roleDao.findAll(Example.of(role));
    }

    @Override
    public List<Role> getByUserId(String userId) {
        List<Role> roles = new ArrayList<>();

        UserShopRole queryParam = new UserShopRole();
        queryParam.setUserId(userId);
        queryParam.setDel(DelEnum.VALID.getType());
        List<UserShopRole> userShopRoles = userShopRoleService.getByEntity(queryParam);
        userShopRoles.forEach(userShopRole -> roles.add(roleDao.findOne(userShopRole.getRoleId())));
        return roles;
    }

    @Override
    public String addRole(Role role) {
        if (isExisted(role)) {
            throw new ServiceRunTimeException(ServiceErrorCode.REPEAT_DATA);
        }

        Role returnRole = save(role);
        if (returnRole == null) {
            throw new ServiceRunTimeException(ServiceErrorCode.WRONG_ADD);
        }
        return returnRole.getId();
    }

    /**
     * 检测角色是否存在
     *
     * @param role
     * @return
     */
    private boolean isExisted(Role role) {
        Role checkParam = new Role();
        checkParam.setRoleName(role.getRoleName());
        checkParam.setDel(DelEnum.VALID.getType());
        if (roleDao.exists(Example.of(checkParam))) {
            return true;
        }
        return false;
    }


}

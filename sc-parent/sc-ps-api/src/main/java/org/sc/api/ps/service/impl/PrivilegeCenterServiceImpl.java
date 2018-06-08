package org.sc.api.ps.service.impl;

import org.sc.api.ps.enums.ServiceErrorCode;
import org.sc.api.ps.model.param.GroupLeaderParam;
import org.sc.api.ps.model.vo.IdNameVo;
import org.sc.api.ps.model.vo.PrivilegeVo;
import org.sc.api.ps.model.vo.SecurityConfigVo;
import org.sc.api.ps.service.PrivilegeCenterService;
import org.sc.api.ps.service.RedisService;
import org.sc.common.enmus.DelEnum;
import org.sc.common.exception.ServiceRunTimeException;
import org.sc.common.model.vo.Page;
import org.sc.common.service.BaseService;
import org.sc.common.utils.StringUtils;
import org.sc.facade.ps.constant.RoleCode;
import org.sc.facade.ps.model.param.QueryPrivilege;
import org.sc.facade.ps.model.param.RolePrivilege;
import org.sc.facade.ps.model.param.UserRole;
import org.sc.facade.ps.model.table.Privilege;
import org.sc.facade.ps.model.table.Role;
import org.sc.facade.ps.service.PrivilegeRoleService;
import org.sc.facade.ps.service.PrivilegeService;
import org.sc.facade.ps.service.RoleService;
import org.sc.facade.ps.service.UserService;
import org.sc.facade.ps.service.UserShopRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Sonic Wang on 2017/8/24.
 */
@Service("privilegeCenterService")
public class PrivilegeCenterServiceImpl extends BaseService implements PrivilegeCenterService {

    @Autowired
    private RoleService roleService;
    @Autowired
    private UserShopRoleService userShopRoleService;
    @Autowired
    private PrivilegeService privilegeService;
    @Autowired
    private PrivilegeRoleService privilegeRoleService;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisService redisService;

    @Override
    public List<Role> getAllRoles() {
        return roleService.getRoles(null);
    }

    @Override
    public void setUserRoles(UserRole userRole) {
        userShopRoleService.setUserRoles(userRole);
    }

    @Override
    public Page<PrivilegeVo> getAllPrivileges(QueryPrivilege queryPrivilege) {
        Page<Privilege> allPrivileges = privilegeService.getAllPrivileges(queryPrivilege);

        Page<PrivilegeVo> privilegeVos = mapper.map(allPrivileges, Page.class);
        List<PrivilegeVo> PrivilegeVoList = new ArrayList<>();
        for (Privilege privilege : allPrivileges.getContent()) {
            PrivilegeVo privilegeVo = mapper.map(privilege, PrivilegeVo.class);

            List<String> roleIds = privilegeRoleService.getPrivilegeRoles(privilegeVo.getId());
            List<String> roleNames = new ArrayList<>();
            roleIds.forEach(roleId -> roleNames.add(roleService.getById(roleId).getRoleName()));
            privilegeVo.setRoleNames(roleNames);

            PrivilegeVoList.add(privilegeVo);
        }

        privilegeVos.setContent(PrivilegeVoList);
        return privilegeVos;
    }

    @Override
    public void setRolePrivileges(RolePrivilege rolePrivilege) {
        privilegeRoleService.setRolePrivileges(rolePrivilege);

        setRedisPrivilege(rolePrivilege.getPrivilegeIds());
        setRedisPrivilege(rolePrivilege.getCancelPrivilegeIds());
    }

    /**
     * 设置redis里面的权限
     *
     * @param privilegeIds
     */
    private void setRedisPrivilege(List<String> privilegeIds) {
        if (privilegeIds == null) {
            return;
        }
        for (String privilegeId : privilegeIds) {
            Privilege privilege = privilegeService.getById(privilegeId);
            if (privilege != null && StringUtils.isNotBlank(privilege.getUrl())) {
                List<String> roles = privilegeRoleService.getPrivilegeRoles(privilegeId);
                List<SecurityConfigVo> roleVos = new ArrayList<>();
                roles.forEach(id -> roleVos.add(new SecurityConfigVo(roleService.getById(id).getRoleCode())));
                if (roleVos.size() == 0) {
                    redisService.del(privilege.getUrl());
                } else {
                    redisService.setList(privilege.getUrl(), roleVos);
                }
            }
        }
    }

    @Override
    public void addRole(Role role) {
        roleService.addRole(role);
    }

    @Override
    public void addPrivilege(Privilege privilege) {
        privilegeService.addPrivilege(privilege);
    }

    @Override
    public void updateRole(Role role) {
        roleService.update(role);
    }

    @Override
    public void updatePrivilege(Privilege privilege) {
        privilegeService.update(privilege);
    }

    @Override
    public void deleteRole(String id) {
        Role role = new Role();
        role.setId(id);
        role.setDel(DelEnum.NO_VALID.getType());
        roleService.update(role);
    }

    @Override
    public void deletePrivilege(String id) {
        Privilege privilege = new Privilege();
        privilege.setId(id);
        privilegeService.deletePrivilege(privilege);
    }

    @Override
    public List<Role> getUserRoles(String userId) {
        return roleService.getByUserId(userId);
    }

    @Override
    public List<Privilege> getRolePrivileges(String roleId, Boolean isMenu) {
        return privilegeService.getByRoleId(roleId, isMenu);
    }

    @Override
    public Set<Privilege> getUserMenuPrivileges() {
        Set<Privilege> privileges = new HashSet<>();
        List<Role> roles = roleService.getByUserId(getLoginUserId());
        for (Role role : roles) {
            List<Privilege> privilegeList = privilegeService.getByRoleId(role.getId(), true);
            privileges.addAll(privilegeList);
        }
        return privileges;
    }


    @Override
    public List<Privilege> getAnonymousPrivileges() {
        Role returnRole = getEntityByRoleCode(RoleCode.ROLE_ANONYMOUS);
        return privilegeService.getByRoleId(returnRole.getId(), false);
    }

    /**
     * 根据rolecode获取唯一的role对象
     *
     * @param roleCode
     * @return
     * @throws ServiceRunTimeException
     */
    private Role getEntityByRoleCode(String roleCode) throws ServiceRunTimeException {
        Role queryRole = new Role();
        queryRole.setRoleCode(roleCode);
        queryRole.setDel(DelEnum.VALID.getType());
        List<Role> returnRoles = roleService.getByEntity(queryRole);
        if (returnRoles.size() != 1) {
            throw new ServiceRunTimeException(ServiceErrorCode.EXCEPTION_ROLE);
        }
        return returnRoles.get(0);
    }

	@Override
	public List<IdNameVo> getUserByRoleCode(String roleCode) {
		// TODO Auto-generated method stub
		return null;
	}
}

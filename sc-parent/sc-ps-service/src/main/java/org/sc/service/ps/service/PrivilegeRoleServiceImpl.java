package org.sc.service.ps.service;

import org.sc.facade.ps.model.param.RolePrivilege;
import org.sc.facade.ps.model.table.PrivilegeRole;
import org.sc.facade.ps.service.PrivilegeRoleService;
import org.sc.common.dao.service.BaseLogicServiceImpl;
import org.sc.service.ps.dao.PrivilegeRoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sonic Wang on 2017/8/25.
 */
@Service("privilegeRoleService")
public class PrivilegeRoleServiceImpl extends BaseLogicServiceImpl<PrivilegeRole> implements PrivilegeRoleService {

    private PrivilegeRoleDao privilegeRoleDao;

    @Autowired
    public PrivilegeRoleServiceImpl(PrivilegeRoleDao privilegeRoleDao) {
        super(privilegeRoleDao);
        this.privilegeRoleDao = privilegeRoleDao;
    }

    @Transactional
    @Override
    public Integer setRolePrivileges(RolePrivilege rolePrivilege) {
        if (rolePrivilege.getIsMenu()) {//删除菜单的角色权限关系
            privilegeRoleDao.deleteRoleMenus(rolePrivilege.getRoleId());
        } else {//删除接口的角色权限关系
            privilegeRoleDao.deleteRoleRestful(rolePrivilege.getRoleId());
        }

        List<PrivilegeRole> privilegeRoles = new ArrayList<>();
        for (String privilegeId : rolePrivilege.getPrivilegeIds()) {
            PrivilegeRole addParam = new PrivilegeRole();
            addParam.setRoleId(rolePrivilege.getRoleId());
            addParam.setPrivilegeId(privilegeId);

            privilegeRoles.add(addParam);
        }

        privilegeRoleDao.save(privilegeRoles);
        return privilegeRoles.size();
    }

    @Override
    public List<PrivilegeRole> getByEntity(PrivilegeRole privilegeRole) {
        return privilegeRoleDao.findAll(Example.of(privilegeRole));
    }

    @Transactional
    @Override
    public String deleteByPrivilege(String privilegeId) {
        privilegeRoleDao.deleteByPrivilege(privilegeId);
        return privilegeId;
    }

    @Override
    public List<String> getPrivilegeRoles(String privilegeId) {
        return privilegeRoleDao.getPrivilegeRoles(privilegeId);
    }
}

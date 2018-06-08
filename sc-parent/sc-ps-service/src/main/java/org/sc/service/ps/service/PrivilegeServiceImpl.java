package org.sc.service.ps.service;

import org.sc.common.dao.service.BaseLogicServiceImpl;
import org.sc.common.enmus.DelEnum;
import org.sc.common.exception.ServiceRunTimeException;
import org.sc.common.model.vo.Page;
import org.sc.common.utils.StringUtils;
import org.sc.facade.ps.vo.RolePrivilegeVo;
import org.sc.facade.ps.model.param.QueryPrivilege;
import org.sc.facade.ps.model.table.Privilege;
import org.sc.facade.ps.model.table.PrivilegeRole;
import org.sc.facade.ps.model.table.Role;
import org.sc.facade.ps.service.PrivilegeRoleService;
import org.sc.facade.ps.service.PrivilegeService;
import org.sc.facade.ps.service.RoleService;
import org.sc.service.ps.dao.PrivilegeDao;
import org.sc.service.ps.enums.ServiceErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 权限控制实现类
 * Created by Sonic Wang on 2017/4/26.
 */
@Service("privilegeService")
public class PrivilegeServiceImpl extends BaseLogicServiceImpl<Privilege> implements PrivilegeService {

    private PrivilegeDao privilegeDao;

    public PrivilegeServiceImpl(PrivilegeDao privilegeDao) {
        super(privilegeDao);
        this.privilegeDao = privilegeDao;
    }

    @Autowired
    private RoleService roleService;
    @Autowired
    private PrivilegeRoleService privilegeRoleService;


    @Override
    public List<RolePrivilegeVo> getRolePrivilege() {
        List<RolePrivilegeVo> rolePrivilegeVos = new ArrayList<>();

        List<Role> roles = roleService.getRoles(null);
        for (Role role : roles) {
            RolePrivilegeVo rolePrivilegeVo = new RolePrivilegeVo();
            mapper.map(role, rolePrivilegeVo);

            List<Privilege> privileges = getByRoleId(role.getId(), false);

            rolePrivilegeVo.setPrivileges(privileges);
            rolePrivilegeVos.add(rolePrivilegeVo);
        }
        return rolePrivilegeVos;
    }

    /**
     * 获取角色的权限
     *
     * @param roleId
     * @param isMenu
     * @return
     */
    @Override
    public List<Privilege> getByRoleId(String roleId, boolean isMenu) {
        List<Privilege> privileges = new ArrayList<>();

        PrivilegeRole queryParam = new PrivilegeRole();
        queryParam.setRoleId(roleId);
        queryParam.setDel(DelEnum.VALID.getType());
        List<PrivilegeRole> privilegeRoles = privilegeRoleService.getByEntity(queryParam);
        for (PrivilegeRole privilegeRole : privilegeRoles) {
            Privilege privilege = privilegeDao.findOne(privilegeRole.getPrivilegeId());
            if (isMenu && StringUtils.isBlank(privilege.getUrl())) {
                privileges.add(privilege);
            } else if (!isMenu && StringUtils.isNotBlank(privilege.getUrl())){
                privileges.add(privilege);
            }
        }

        return privileges;
    }

    @Override
    public String deletePrivilege(Privilege privilege) {
        Privilege updateParam = new Privilege();
        updateParam.setId(privilege.getId());
        updateParam.setDel(DelEnum.NO_VALID.getType());
        Privilege returnPrivilege = update(updateParam);
        if (returnPrivilege == null) {
            throw new ServiceRunTimeException(ServiceErrorCode.WRONG_UPDATE);
        }

        privilegeRoleService.deleteByPrivilege(privilege.getId());
        return privilege.getId();
    }

    @Override
    public Page<Privilege> getAllPrivileges(QueryPrivilege queryPrivilege) {
        StringBuffer hql = new StringBuffer();
        Map<String, Object> params = new HashMap();
        hql.append(" FROM Privilege WHERE del = 0 ");
        if (StringUtils.isNotBlank(queryPrivilege.getPrivilegeName())) {
            hql.append("AND privilegeName LIKE CONCAT('%',:privilegeName,'%') ");
            params.put("privilegeName", queryPrivilege.getPrivilegeName());
        }
        if (StringUtils.isNotBlank(queryPrivilege.getUrl())) {
            hql.append("AND url LIKE CONCAT('%',:url,'%') ");
            params.put("url", queryPrivilege.getUrl());
        }
        if (queryPrivilege.getIsMenu() != null && queryPrivilege.getIsMenu()) {
            hql.append("AND url is null ");
        } else if (queryPrivilege.getIsMenu() != null && !queryPrivilege.getIsMenu()) {
            hql.append("AND url is not null ");
        }
        String countSql = "SELECT COUNT(*)" + hql;

        if (queryPrivilege.getIsMenu() != null && queryPrivilege.getIsMenu()) {
            hql.append("ORDER BY privilegeName ASC");
        } else if (queryPrivilege.getIsMenu() != null && !queryPrivilege.getIsMenu()) {
            hql.append("ORDER BY createTime DESC");
        }

        Page<Privilege> privilegePage = pageQuery(hql.toString(), countSql, params
                , queryPrivilege.getPage(), queryPrivilege.getSize(), false);
        return privilegePage;
    }

    @Override
    public String addPrivilege(Privilege privilege) {
        if (isExisted(privilege)) {
            throw new ServiceRunTimeException(ServiceErrorCode.REPEAT_DATA);
        }

        Privilege returnPrivilege = save(privilege);
        if (returnPrivilege == null) {
            throw new ServiceRunTimeException(ServiceErrorCode.WRONG_ADD);
        }
        return returnPrivilege.getId();
    }

    /**
     * 权限是否重复
     *
     * @param privilege
     * @return
     */
    private boolean isExisted(Privilege privilege) {
        Privilege checkParam = new Privilege();
        checkParam.setPrivilegeName(privilege.getPrivilegeName());
        checkParam.setUrl(privilege.getUrl());
        checkParam.setDel(DelEnum.VALID.getType());
        if (privilegeDao.exists(Example.of(checkParam))) {
            return true;
        }
        return false;
    }
    
   public static void main(String[] args) {
	   System.out.println("你妹的2:"+StringUtils.isBlank(""));
}
}

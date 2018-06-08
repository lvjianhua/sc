package org.sc.service.ps.dao;

import org.sc.facade.ps.model.table.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 角色dao
 * Created by Sonic Wang on 2017/6/12.
 */
public interface RoleDao extends JpaRepository<Role, String> {
}

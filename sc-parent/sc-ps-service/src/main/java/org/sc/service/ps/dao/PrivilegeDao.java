package org.sc.service.ps.dao;

import org.sc.facade.ps.model.table.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 权限dao
 * Created by Sonic Wang on 2017/4/26.
 */
public interface PrivilegeDao extends JpaRepository<Privilege, String> {

}

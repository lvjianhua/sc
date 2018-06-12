package org.sc.facade.ps.model.vo;

import lombok.Data;

import java.util.List;

import org.sc.facade.ps.model.table.Privilege;
import org.sc.facade.ps.model.table.Role;

/**
 * Created by Sonic Wang on 2017/7/4.
 */
@Data
public class RolePrivilegeVo extends Role{

    private List<Privilege> privileges;
}

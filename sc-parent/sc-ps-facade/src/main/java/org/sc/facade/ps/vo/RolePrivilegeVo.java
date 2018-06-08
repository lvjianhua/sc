package org.sc.facade.ps.vo;

import org.sc.facade.ps.model.table.Privilege;
import org.sc.facade.ps.model.table.Role;
import lombok.Data;

import java.util.List;

/**
 * Created by Sonic Wang on 2017/7/4.
 */
@Data
public class RolePrivilegeVo extends Role{

    private List<Privilege> privileges;
}

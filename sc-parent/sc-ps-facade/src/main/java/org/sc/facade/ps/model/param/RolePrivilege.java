package org.sc.facade.ps.model.param;

import lombok.Data;

import java.util.List;

/**
 * Created by Sonic Wang on 2017/8/25.
 */
@Data
public class RolePrivilege {

    private Boolean isMenu;

    private String roleId;

    private List<String> privilegeIds;

    private List<String> cancelPrivilegeIds;
}

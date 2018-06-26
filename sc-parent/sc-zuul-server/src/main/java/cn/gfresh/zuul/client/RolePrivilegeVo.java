package cn.gfresh.zuul.client;

import java.util.List;

import lombok.Data;

/**
 * Created by Sonic Wang on 2017/7/4.
 */
@Data
public class RolePrivilegeVo extends Role{

    private List<Privilege> privileges;
}

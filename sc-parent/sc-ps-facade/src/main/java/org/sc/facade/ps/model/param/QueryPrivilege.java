package org.sc.facade.ps.model.param;

import org.sc.common.model.param.PageParam;
import lombok.Data;

/**
 * Created by Sonic Wang on 2017/8/25.
 */
@Data
public class QueryPrivilege extends PageParam{

    /**
     * 权限名
     */
    private String privilegeName;

    /**
     * 链接
     */
    private String url;

    /**
     * true菜单，false接口，null全部
     */
    private Boolean isMenu;
}

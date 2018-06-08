package org.sc.facade.ps.model.param;

import org.sc.common.model.param.PageParam;
import lombok.Data;

/**
 * Created by Sonic Wang on 2017/8/29.
 */
@Data
public class QueryUser extends PageParam {

    private String userName;

    private String userType;
}

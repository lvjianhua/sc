package org.sc.facade.ps.model.param;

import lombok.Data;

/**
 * Created by Sonic Wang on 2017/7/24.
 */
@Data
public class QueryShop {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 是否是自己开的店
     */
    private boolean owner;
}

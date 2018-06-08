package org.sc.api.ps.model.param;

import lombok.Data;

/**
 * Created by Sonic Wang on 2018/5/15.
 */
@Data
public class GroupLeaderParam {

    private String userId;

    /**
     * true为设置
     * false为取消
     */
    private boolean set;
}

package org.sc.facade.ps.model.vo;

import lombok.Data;

/**
 * Created by Sonic Wang on 2018/4/2.
 */
@Data
public class CodeCountVo {

    private long count;

    private long availableCount;

    private long usedCount;
}

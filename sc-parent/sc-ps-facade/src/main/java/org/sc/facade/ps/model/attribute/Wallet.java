package org.sc.facade.ps.model.attribute;

import lombok.Data;

/**
 * Created by Sonic Wang on 2017/3/8.
 */
@Data
public class Wallet {

    /**
     * 当前余额，单位：毫(元*10000)
     */
    private Long money;

    /**
     * 当前积分(money*100)
     */
    private Long point;

    /**
     * 当前信用额度，单位：毫(元*10000)
     */
    private Long creditMoney;

    /**
     * 当前保证金，单位：毫(元*10000)
     */
    private Long deposit;

}

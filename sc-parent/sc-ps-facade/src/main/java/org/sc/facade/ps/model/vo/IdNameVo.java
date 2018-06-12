package org.sc.facade.ps.model.vo;

import lombok.Data;

/**
 * Created by Sonic Wang on 2017/10/10.
 */
@Data
public class IdNameVo {

    public IdNameVo() {
        super();
    }

    public IdNameVo(String id, String name) {
        this.id = id;
        this.name = name;
    }

    private String id;

    private String name;
}

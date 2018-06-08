package org.sc.common.enmus;

/**
 * 删除标志的值
 **/
public enum DelEnum {

    /**有效的*/
    VALID(0,"有效的"),

    /**无效的*/
    NO_VALID(1,"无效的");

    DelEnum(int type,String name){
        this.type = type;
        this.name = name;
    }
    public int type;
    public String name;


    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}

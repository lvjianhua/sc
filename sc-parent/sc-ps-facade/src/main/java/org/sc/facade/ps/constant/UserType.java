package org.sc.facade.ps.constant;

/**
 * Created by Sonic Wang on 2017/4/14.
 */
public enum UserType {

    UserAL("UserAL", "客户"),

    STAFF("STAFF", "内部员工"),

    WHOLESALE("WHOLESALE", "批发"),

    OTHER("OTHER", "其他"),

    RESTAURANT("RESTAURANT", "餐饮"),

    PLATFORM("PLATFORM", "电商"),

    AGENT("AGENT", "代理"),

    GROUP_PURCHASE("GROUP_PURCHASE", "团购");

    UserType(String type, String name) {
        this.type = type;
        this.name = name;
    }

    private String name;
    private String type;

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }

}

package org.sc.common.enmus;

/**
 * 语言枚举
 * 间隔符用下划线"_"，对象名与language要保持一致
 */
public enum LanguageEnum {

    /**
     *中文简体
     */
    zh_CN("zh_CN"),

    /**
     * 英文(默认)
     */
    en("en");

    public String language;

    LanguageEnum(String language) {
        this.language = language;
    }

}

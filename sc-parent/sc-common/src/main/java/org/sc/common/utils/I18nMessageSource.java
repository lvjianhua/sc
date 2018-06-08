package org.sc.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Prop国际化工具类
 * Created by Webb Dong on 2017/3/31.
 */
@Component("i18nMessageSource")
public class I18nMessageSource {
    @Autowired
    private MessageSource messageSource;

    private static final String CHINESE = "zh";

    private static final String ENGLISH = "en";

    /**
     * 获取国际化内容
     * @param key   key名
     * @param lang  语言标识
     * @return
     */
    public String getMessage(String key, String lang) {
        String value = null;
        if (CHINESE.equals(lang)) {
            value = messageSource.getMessage(key, null, Locale.SIMPLIFIED_CHINESE);
        } else if (ENGLISH.equals(lang)) {
            value = messageSource.getMessage(key, null, Locale.US);
        } else {
            throw new RuntimeException("未知语言:" + lang);
        }
        return value;
    }
}

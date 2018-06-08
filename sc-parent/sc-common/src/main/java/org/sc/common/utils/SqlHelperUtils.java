package org.sc.common.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sonic Wang on 2017/3/25.
 */
public class SqlHelperUtils {

    /**
     * 用map传值拼接进sql
     * @param sql
     * @param map
     * @return
     */
    public static String addSqlData(String sql, Map<String, Object> map) {
        for (String key : map.keySet()){
            Object value = map.get(key);
            sql.replaceAll("@{" + key + "}", value instanceof String ? "'" + value + "'" : value.toString());
        }
        return sql;
    }


    public static String addSqlData(String sql, Object object) {
        for (Field field : object.getClass().getDeclaredFields()){

//            field.setAccessible(true);
            String key = field.getName();
            Object value = null;
            try {
                value = field.get(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            sql.replaceAll("@{" + key + "}", value instanceof String ? "'" + value + "'" : value.toString());
        }
        return sql;
    }

}

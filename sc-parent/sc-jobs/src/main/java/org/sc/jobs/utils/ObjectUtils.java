package org.sc.jobs.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

public class ObjectUtils {

    /**
     * 通过对象得到所有的该对象所有定义的属性值
     *
     * @param obj 目标对象
     */
    public static HashMap<String, String> method(Object obj) {
        HashMap<String, String> hm = new HashMap<>();
        try {
            Class clazz = obj.getClass();
            Field[] fields = obj.getClass().getDeclaredFields();//获得属性
            for (Field field : fields) {
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
                Method getMethod = pd.getReadMethod();//获得get方法
                Object o = getMethod.invoke(obj);//执行get方法返回一个Object
                hm.put(field.getName(), String.valueOf(o));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hm;
    }

    /**
     * 通过对象和具体的字段名字获得字段的值
     *
     * @param obj   目的对象
     * @param filed 字段名
     * @return 通过字段名得到字段值
     */
    public static Object method(Object obj, String filed) {
        try {
            Class clazz = obj.getClass();
            PropertyDescriptor pd = new PropertyDescriptor(filed, clazz);
            Method getMethod = pd.getReadMethod();//获得get方法
            Object o = getMethod.invoke(obj);//执行get方法返回一个Object
            return o;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
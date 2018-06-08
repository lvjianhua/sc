package org.sc.common.utils.web;



import net.sourceforge.pinyin4j.PinyinHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * 字符传首字母排序
 *
 * @author run
 * @create 2017-05-19 14:36
 **/
public class PinyinComparator<T> implements Comparator<T> {
    private static final Logger logger = LoggerFactory.getLogger(PinyinComparator.class);

    private String fieldName;
    private Class clazz;
    private Field field;
    private String childFieldName;
    private Class childClazz;
    private Field childField;

    public PinyinComparator() {
    }

    public PinyinComparator(String fieldName) {
        super();
        this.fieldName = fieldName;

      /*  try {
            this.field = clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
           logger.error(e.getMessage(),e);
        }*/
    }

    public PinyinComparator(String fieldName, String childFieldName) {
        super();
        this.fieldName = fieldName;
        this.childFieldName = childFieldName;

       /* try {
            this.field = clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            logger.error(e.getMessage(),e);
        }*/
    }


    public int compare(T obj1, T obj2) {
        if (StringUtils.isEmpty(fieldName)) {
            char c1 = obj1.toString().charAt(0);
            char c2 = obj2.toString().charAt(0);
            return concatPinyinStringArray(
                    PinyinHelper.toHanyuPinyinStringArray(c1)).compareTo(
                    concatPinyinStringArray(PinyinHelper
                            .toHanyuPinyinStringArray(c2)));
        } else {
            char c1 = getFieldValue(obj1).charAt(0);
            char c2 = getFieldValue(obj2).charAt(0);
            System.out.print(PinyinHelper.toHanyuPinyinStringArray(c1));
            return concatPinyinStringArray(
                    PinyinHelper.toHanyuPinyinStringArray(c1)).compareTo(
                    concatPinyinStringArray(PinyinHelper
                            .toHanyuPinyinStringArray(c2)));
        }

    }

    private String concatPinyinStringArray(String[] pinyinArray) {
        StringBuffer pinyinSbf = new StringBuffer();
        if ((pinyinArray != null) && (pinyinArray.length > 0)) {
            for (int i = 0; i < pinyinArray.length; i++) {
                pinyinSbf.append(pinyinArray[i]);
            }
        }
        return pinyinSbf.toString();
    }

    /**
     * 获取属性值
     *
     * @param obj
     * @return
     */
    private String getFieldValue(T obj) {
        try {
            if (clazz == null) {
                this.clazz = obj.getClass();
            }
            if (field == null) {
                this.field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
            }

            Object childObj = field.get(obj);
            if (childFieldName == null) {
                return (String) childObj;
            }
            if (childClazz == null) {
                this.childClazz = childObj.getClass();
            }

            if (childClazz.equals(HashMap.class)) {
                Map map = (Map) childObj;
                return (String) map.get(childFieldName);
            } else {

                if (childField == null) {
                    this.childField = childClazz.getDeclaredField(childFieldName);
                    childField.setAccessible(true);
                }
                return (String) childField.get(childObj);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "";
    }

}

package org.sc.common.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ObjectUtil {

	/**
	 * 拷贝对象同名属性
	 * 
	 * @param copyObj被拷贝对象
	 * @param toObj目标对象
	 * @throws Exception
	 */
	public static void copySameProperties(Object copyObj, Object toObj) {

		List<Field> copyList = getFields(copyObj);
		List<Field> toList = getFields(toObj);
		for (Field field1 : copyList) {
			field1.setAccessible(true);
			for (Field field2 : toList) {
				field2.setAccessible(true);
				if (field2.getName().equals(field1.getName())) {
					try {
						field2.set(toObj, field1.get(copyObj));
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * 获取所有属性，包括父类
	 * 
	 * @param obj
	 * @return
	 */
	public static List<Field> getFields(Object obj) {
		List<Field> list = new ArrayList<>();
		Class<?> clazz = obj.getClass();
		while (clazz != null) {
			Field[] fiedArr = clazz.getDeclaredFields();
			list.addAll(Arrays.asList(fiedArr));
			Class<?> superClass = clazz.getSuperclass();
			if (superClass == null) {
				break;
			}
			clazz = superClass;
		}
		return list;
	}

}

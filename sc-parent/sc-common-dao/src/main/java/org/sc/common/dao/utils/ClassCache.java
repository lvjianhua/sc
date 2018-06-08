package org.sc.common.dao.utils;



import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * 记录扫描包的一些文件
 *
 * @author run create by 17/03/26
 * @version V1.0
 */
public class ClassCache {
	private static Logger logger = LogManager.getLogger(ClassCache.class);
	/**
	 * table 对象的 class 缓存
	 */
	private static final Map<String, List<Field>> modelFieldMap = new HashMap<String, List<Field>>();
	
	private static final Map<String, Class<?>> modelEntityMap = new HashMap<>();

	private static final Map<String, Map<String, Field>> modelSqlFieldMap = new HashMap<String, Map<String, Field>>();

	
	public static void init(){
		logger.info("init class size--------------------"+ClassCache.modelEntityMap.size());
		logger.info("init field size--------------------"+ClassCache.modelFieldMap.size());
		/*modelFieldMap.putAll(AnnotationScanUtils.scanClassFieldByAnnotation("cn.gfresh.facade", Entity.class));
		modelEntityMap.putAll(AnnotationScanUtils.scanClassByAnnotation("cn.gfresh.facade", Entity.class));*/
	}
	public static void setValueByClass(Class clz){
		AnnotationScanUtils.newFillClassField(clz, modelFieldMap, Entity.class);
		AnnotationScanUtils.newFillClass(clz, modelEntityMap, Entity.class);
	}
	
	/**根据table名获取对应的class*/
	public static Class<?> getClassByClassName(String name) {
		return modelEntityMap.get(StringUtils.lowerCase(name));
	}
	/** 查询出hql对应的field类型 */
	public static Field getClassFieldByClassName(String name, int index) {
		return modelFieldMap.get(StringUtils.lowerCase(name)).get(index - 1);
	}

	/** 查询出sql对应的field类型 */
	public static Field getClassFieldByClassName(String name, String sqlField) {
		sqlField = sqlField.replaceAll("_", "");
		if (modelSqlFieldMap.get(StringUtils.lowerCase(name)) != null) {
		} else {
			if (modelFieldMap.get(StringUtils.lowerCase(name)) != null) {
				List<Field> list = modelFieldMap.get(StringUtils.lowerCase(name));
				Map<String, Field> map = new HashMap<>();
				for (Field field : list) {
					map.put(StringUtils.lowerCase(field.getName()), field);
				}
				modelSqlFieldMap.put(StringUtils.lowerCase(name), map);
			} else {
				return null;
			}
		}

		return modelSqlFieldMap.get(StringUtils.lowerCase(name)).get(StringUtils.lowerCase(sqlField));
	}
}

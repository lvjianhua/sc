package org.sc.common.dao.utils;

import java.io.File;
import java.io.FileFilter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.sc.common.model.vo.BaseEntity;
import org.sc.common.utils.SpringContextUtils;

/**
*
* 注解扫描工具类
* 
* @author run create by 17/03/26
*
* @version V1.0
*/
public class AnnotationScanUtils {
	private static final Logger logger = LogManager.getLogger(AnnotationScanUtils.class);
	/** 点 */
	private static final String DOT = ".";

	/** class 文件后缀 */
	private static final String CLASS_EXT = ".class";

	private static AnnotationScanUtils instance = new AnnotationScanUtils();

	private AnnotationScanUtils() {
	}

	/**
	 * 根据注解获取class create by 17/03/26
	 * 
	 * @param className
	 *            ，String
	 * @param classes
	 *            ，Map<String,Class<?>>
	 * @param annotation
	 *            ，Class<? extends Annotation>
	 */
	private static void fillClass(String className, Map<String, Class<?>> classes,
			Class<? extends Annotation> annotation) {
		try {
			logger.info("class================================before"+className);
			if(className.indexOf("cn.gfresh") > 0){
				className = className.substring(className.indexOf("cn.gfresh"));
			}
			Class<?> clz = Class.forName(className);
			logger.info("class================================after"+className+"======================="+clz);
			if (annotation == null || clz.getAnnotation(annotation) != null) {
				String table = className.substring(className.lastIndexOf(DOT) + 1);
				String key = StringUtils.lowerCase(table);

				if (classes.containsKey(key)) {
				}

				classes.put(key, clz);
			}
		} catch (ClassNotFoundException e) {
			logger.error("class is error,className:"+className,e);
		}
	}
	
	public static void newFillClass(Class clz, Map<String, Class<?>> classes,
			Class<? extends Annotation> annotation) {
		try {
			if (annotation == null || clz.getAnnotation(annotation) != null) {
				String table = clz.getSimpleName();
				String key = StringUtils.lowerCase(table);
				
				if (classes.containsKey(key)) {
				}
				
				classes.put(key, clz);
			}
		} catch (Exception e) {
			logger.error("class is error,className:"+clz.getName(),e);
		}
	}

	/**
	 * 
	 * @Description: 根据class获取所有字段
	 * @param: @param
	 *             className
	 * @param: @param
	 *             classes
	 * @param: @param
	 *             annotation
	 * @return: void
	 * @throws:
	 * @author: shawn-gfresh
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	private static void fillClassField(String className, Map<String, List<Field>> classes,
			Class<? extends Annotation> annotation) {
		try {
			if(className.indexOf("org.sc") > 0){
				className = className.substring(className.indexOf("org.sc"));
			}
			Class<?> clz = Class.forName(className);
			if (annotation == null || clz.getAnnotation(annotation) != null) {
				String table = className.substring(className.lastIndexOf(DOT) + 1);
				String key = StringUtils.lowerCase(table);

				if (classes.containsKey(key)) {
				}


				Field[] fields = clz.getDeclaredFields();
               List<Field> excludefields = Arrays.asList(clz.getFields());
               Field[] superFields = clz.getSuperclass().getDeclaredFields();
               List<Field> excludeSuperFields = Arrays.asList(clz.getSuperclass().getFields());
               List<String> fieldList = new ArrayList<>();
				List<String> superFieldList = new ArrayList<>();
				for (Field field : fields) {
                   if (field.getName().equals("id") || excludefields.contains(field)) {
                       continue;
					}
					fieldList.add(field.getName());
				}
				for (Field field : superFields) {
                   if (field.getName().equals("id") || excludeSuperFields.contains(field)) {
                       continue;
					}
					superFieldList.add(field.getName());
				}

				Collections.sort(fieldList);
				Collections.sort(superFieldList);
				superFieldList.addAll(fieldList);
				List<Field> fieldsList = new ArrayList<>();

				Field idField = null;
				try {
					idField = clz.getDeclaredField("id");

				} catch (NoSuchFieldException e1) {
					try {
						idField = clz.getSuperclass().getDeclaredField("id");
					} catch (NoSuchFieldException e) {
						e1.printStackTrace();
						e.printStackTrace();
					}
				}

				fieldsList.add(idField);

				for (String str : superFieldList) {
					Field field = null;
					try {
						field = clz.getDeclaredField(str);
					} catch (Exception e) {
						try {
							field = clz.getSuperclass().getDeclaredField(str);
						} catch (NoSuchFieldException e1) {
							e1.printStackTrace();
							e.printStackTrace();
						}
					}
					fieldsList.add(field);
				}
				classes.put(key, fieldsList);
			}
		} catch (ClassNotFoundException e) {
			logger.error("class is error,className:"+className,e);
		}
	}
	
	public static void newFillClassField(Class clz, Map<String, List<Field>> classes,
			Class<? extends Annotation> annotation) {
		try {
			if (annotation == null || clz.getAnnotation(annotation) != null) {
				String table = clz.getSimpleName();
				String key = StringUtils.lowerCase(table);
				
				if (classes.containsKey(key)) {
				}
				
				
				Field[] fields = clz.getDeclaredFields();
				List<Field> excludefields = Arrays.asList(clz.getFields());
				Field[] superFields = clz.getSuperclass().getDeclaredFields();
				List<Field> excludeSuperFields = Arrays.asList(clz.getSuperclass().getFields());
				List<String> fieldList = new ArrayList<>();
				List<String> superFieldList = new ArrayList<>();
				for (Field field : fields) {
					if (field.getName().equals("id") || excludefields.contains(field)) {
						continue;
					}
					fieldList.add(field.getName());
				}
				for (Field field : superFields) {
					if (field.getName().equals("id") || excludeSuperFields.contains(field)) {
						continue;
					}
					superFieldList.add(field.getName());
				}
				
				Collections.sort(fieldList);
				Collections.sort(superFieldList);
				superFieldList.addAll(fieldList);
				List<Field> fieldsList = new ArrayList<>();
				
				Field idField = null;
				try {
					idField = clz.getDeclaredField("id");
					
				} catch (NoSuchFieldException e1) {
					try {
						idField = clz.getSuperclass().getDeclaredField("id");
					} catch (NoSuchFieldException e) {
						e1.printStackTrace();
						e.printStackTrace();
					}
				}
				
				fieldsList.add(idField);
				
				for (String str : superFieldList) {
					Field field = null;
					try {
						field = clz.getDeclaredField(str);
					} catch (Exception e) {
						try {
							field = clz.getSuperclass().getDeclaredField(str);
						} catch (NoSuchFieldException e1) {
							e1.printStackTrace();
							e.printStackTrace();
						}
					}
					fieldsList.add(field);
				}
				classes.put(key, fieldsList);
			}
		} catch (Exception e) {
			logger.error("class is error,className:"+clz.getName(),e);
		}
	}

	/**
	 * 根据注解获取jar 文件里面的class create by 17/03/26
	 * 
	 * @param filePath
	 *            ，String
	 * @param annotation
	 *            ，Class<? extends Annotation>
	 *
	 * @return Map<String,Class<?>>
	 */
	private static Map<String, Class<?>> processJarFile(String filePath, Map<String, Class<?>> classes,
			Class<? extends Annotation> annotation) {

		try {
			filePath = filePath.substring(5, filePath.indexOf("!"));

			for (ZipEntry entry : Collections.list(new ZipFile(filePath).entries())) {
				if (isClass(entry.getName())) {
					final String className = entry.getName().replace("/", DOT).replace(CLASS_EXT, "");

					fillClass(className, classes, annotation);
				}
			}
		} catch (Exception e) {
			logger.error("filePath is error,filePath:"+filePath,e);
		}

		return classes;
	}

	private static Map<String, List<Field>> processJarFileField(String filePath, Map<String, List<Field>> classes,
			Class<? extends Annotation> annotation) {

		try {
			filePath = filePath.substring(5, filePath.indexOf("!"));

			for (ZipEntry entry : Collections.list(new ZipFile(filePath).entries())) {
				if (isClass(entry.getName())) {
					final String className = entry.getName().replace("/", DOT).replace(CLASS_EXT, "");

					fillClassField(className, classes, annotation);
				}
			}
		} catch (Exception e) {
			logger.error("filePath is error,filePath:"+filePath,e);
		}

		return classes;
	}

	/**
	 * 根据注解获取calss create by 17/03/26
	 * 
	 * @param packageName
	 *            ，String
	 * @param annotation
	 *            ，Class<? extends Annotation>
	 *
	 * @return Map<String,Map<String,Class<?>>>
	 */
	public static Map<String, Class<?>> scanClassByAnnotation(String packageName,
			Class<? extends Annotation> annotation) {
		Map<String, Class<?>> classes = new HashMap<String, Class<?>>(0);
		try {
			/*try {
				ResourcePatternResolver rpr = new PathMatchingResourcePatternResolver();
				org.springframework.core.io.Resource[] res = rpr
						.getResources("classpath*:" + packageName.replace(DOT, FILE_SEPARATOR));

				for (org.springframework.core.io.Resource resource : res) {
					URL url = resource.getURL();
					String protocol = url.getProtocol();
					String filePath = URLDecoder.decode(url.getFile(), "UTF-8");

					if ("file".equals(protocol)) {
						scanClasses(filePath, packageName, classes, true, annotation);
					} else if ("jar".equals(protocol)) {
						processJarFile(filePath, classes, annotation);
					}
				}
			} catch (Exception e) {
			}*/
			/**使用spring 获取所有指定的注解内容*/
			Map<String, Object> beansWithAnnotationMap = SpringContextUtils.getApplicationContext().getBeansWithAnnotation(annotation);
			
			for(Map.Entry<String, Object> entry : beansWithAnnotationMap.entrySet()){  
				if(entry.getValue() instanceof BaseEntity){
					classes.put(entry.getValue().getClass().getName(), entry.getValue().getClass());
				}
			}
		} catch (Exception e) {
			logger.error("packageName is error,packageName:"+packageName,e);
		}

		return classes;
	}

	/**
	 * 
	 * @Description: 根据class获取所有字段
	 * @param: @param
	 *             packageName
	 * @param: @param
	 *             annotation
	 * @param: @return
	 * @return: Map<String,Class<?>>
	 * @throws:
	 * @author: shawn-gfresh
	 */
	public static Map<String, List<Field>> scanClassFieldByAnnotation(String packageName,
			Class<? extends Annotation> annotation) {
		Map<String, List<Field>> classes = new HashMap<String, List<Field>>(0);
		try {
			/*try {
				ResourcePatternResolver rpr = new PathMatchingResourcePatternResolver();
				org.springframework.core.io.Resource[] res = rpr
						.getResources("classpath*:" + packageName.replace(DOT, FILE_SEPARATOR));

				for (org.springframework.core.io.Resource resource : res) {
					URL url = resource.getURL();
					String protocol = url.getProtocol();
					String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
					
					if ("file".equals(protocol)) {
						scanClassesFields(filePath, packageName, classes, true, annotation);
					} else if ("jar".equals(protocol)) {
						processJarFileField(filePath, classes, annotation);
					}
				}*/
			Map<String, Object> beansWithAnnotationMap = SpringContextUtils.getApplicationContext().getBeansWithAnnotation(annotation);
			
			for(Map.Entry<String, Object> entry : beansWithAnnotationMap.entrySet()){  
				if(entry.getValue() instanceof BaseEntity){
					newFillClassField(entry.getValue().getClass(), classes, annotation);
				}
			}
		} catch (Exception e) {
			logger.error("packageName is error,packageName:"+packageName,e);
		}

		return classes;
	}

	/**
	 * 扫描calss w文件 create by 17/03/26
	 * 
	 * @param filePath
	 *            ，String
	 * @param packageName
	 *            ，String
	 * @param recursive
	 *            ，boolean
	 * @param annotation
	 *            ，Class<? extends Annotation>
	 *
	 * @return Map<String,Class<?>>
	 */
	private static Map<String, Class<?>> scanClasses(String filePath, String packageName, Map<String, Class<?>> classes,
			final boolean recursive, Class<? extends Annotation> annotation) {
		File dir = new File(filePath);

		if (!dir.exists() || !dir.isDirectory()) {
			return new HashMap<String, Class<?>>(0);
		}

		// 获取文件下所有文件
		File[] dirFiles = dir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				return (recursive && file.isDirectory()) || (file.getName().endsWith(CLASS_EXT));
			}
		});

		String prePackageName = packageName;
		
		for (File file : dirFiles) {
			if (file.isDirectory()) {
				packageName = fileToPackage(file.getPath(), prePackageName);
				// 如果是目录，继续扫描
				scanClasses(file.getPath(), packageName, classes, recursive, annotation);
			} else {
				String className = prePackageName + '.' + file.getName().substring(0, file.getName().length() - 6);

				fillClass(className, classes, annotation);
			}
		}

		return classes;
	}

	/**
	 * 
	 * @Description: 根据class获取所有字段
	 * @param: @param
	 *             filePath
	 * @param: @param
	 *             packageName
	 * @param: @param
	 *             classes
	 * @param: @param
	 *             recursive
	 * @param: @param
	 *             annotation
	 * @param: @return
	 * @return: Map<String,List<String>>
	 * @throws:
	 * @author: shawn-gfresh
	 */
	private static Map<String, List<Field>> scanClassesFields(String filePath, String packageName,
			Map<String, List<Field>> classes, final boolean recursive, Class<? extends Annotation> annotation) {
		File dir = new File(filePath);

		if (!dir.exists() || !dir.isDirectory()) {
			return new HashMap<String, List<Field>>(0);
		}

		// 获取文件下所有文件
		File[] dirFiles = dir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				return (recursive && file.isDirectory()) || (file.getName().endsWith(CLASS_EXT));
			}
		});

		String prePackageName = packageName;
		for (File file : dirFiles) {
			if (file.isDirectory()) {
				packageName = fileToPackage(file.getPath(), prePackageName);
				// 如果是目录，继续扫描
				scanClassesFields(file.getPath(), packageName, classes, recursive, annotation);
			} else {
				String className = prePackageName + '.' + file.getName().substring(0, file.getName().length() - 6);

				fillClassField(className, classes, annotation);
			}
		}

		return classes;
	}

	private static final String FILE_SEPARATOR = System.getProperty("file.separator");

	private static String fileToPackage(String filePath, String packageName) {
		String[] split = packageName.split("\\.");
		String startPackage = split[0];
		String subFilePath = filePath.substring(filePath.indexOf(startPackage));
		subFilePath = subFilePath.replaceAll("\\" + FILE_SEPARATOR, ".");
		return subFilePath;
		// return subFilePath.substring(0, subFilePath.lastIndexOf("."));
	}

	/**
	 * 判断文件名是否是class文件 create by 17/03/26
	 * 
	 * @param fileName
	 *            ，String
	 *
	 * @return boolean
	 */
	private static boolean isClass(String fileName) {
		return fileName.endsWith(CLASS_EXT);
	}

	/**
	 * 获取annotaionaScanUtil 对象
	 *
	 * @return AnnotationScanUtil
	 */
	public static AnnotationScanUtils getInstance() {
		return instance;
	}

}

package org.sc.common.utils;

import java.lang.reflect.Field;

/**
 * Created by Tony on 2017/5/4.
 */
public class ColumnReName {

    /**
     * 把给定的Entity对象的字段转换成查询语句需要的as
     */
    public static String getNeedColumn(Class<?> clazz){
    	 StringBuffer strColumn = new StringBuffer();
    	 
    	 for(; clazz!= Object.class; clazz = clazz.getSuperclass()) {  
             try {
            	 getFieldsFromClass(clazz,strColumn);
             } catch (Exception e) {  
                 //这里甚么都不能抛出去。  
                 //如果这里的异常打印或者往外抛，则就不会进入                  
             }
         }         
         
         return strColumn.toString();
    }
    
    private static void getFieldsFromClass(Class clazz, StringBuffer strColumn){
    	Field[] fs = clazz.getDeclaredFields();   	 	
        for(int i = 0 ; i < fs.length; i++){
	       	Field f = fs[i];
	        //String type = f.getType().toString().toLowerCase();//得到此属性的类型 
	        if("ID".equals(f.getName())){
	        	continue;
	        }
	       	if(strColumn.length() ==0){
	       		strColumn.append(f.getName()).append(" as ").append(f.getName());
	       	}
	       	else{
	       		strColumn.append(",").append(f.getName()).append(" as ").append(f.getName());
	       	}
        }
    }
    
}

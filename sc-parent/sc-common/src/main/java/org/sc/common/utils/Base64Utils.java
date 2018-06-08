package org.sc.common.utils;


import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * @ClassName: Base64Utils
 * @Description: Base64加密解密工具类
 * @author Kola 6089555
 * @date 2015年11月25日 下午3:55:38
 *       
 */
public class Base64Utils {
    
    private Base64Utils() {
    }
    
    /**
     * @Fields instance : 持有私有静态实例，防止被引用，此处赋值为null，目的是实现延迟加载
     */
    private static Base64Utils instance = null;
    
    /**
     * @Title: syncInit
     * @Description: 同步的获取实例
     * @author Kola 6089555
     * @date 2015年10月22日 下午6:39:40
     */
    private static synchronized void syncInit() {
        if (instance == null) {
            instance = new Base64Utils();
        }
    }
    
    /**
     * @Title: getInstance
     * @Description: 静态获取实例
     * @author Kola 6089555
     * @date 2015年10月22日 下午6:39:58
     * @return
     */
    public static Base64Utils getInstance() {
        if (instance == null) {
            syncInit();
        }
        return instance;
    }
    
    /**
     * @Title: deCodeUserId
     * @Description: 解密用户ID
     * @author Kola 6089555
     * @date 2015年11月25日 下午4:12:57
     * @param str
     * @return
     * @throws Exception
     */
    public Integer deCodeUserId(String str) throws Exception {
        try {
            String deCodeVal = deCoding(StringUtils.trim(str));
            String subVal = Pattern.compile("[^0-9]").matcher(deCodeVal).replaceAll("");
            return (Integer.valueOf(subVal) - 462) / 357;
        } catch (Exception e) {
            throw new Exception("deCodeUserId faliure....str:" + str + ";length:" + str.length(), e);
        }
    }
    
    /**
     * @Title: enCodeUserId
     * @Description: 加密用户ID
     * @author Kola 6089555
     * @date 2015年11月25日 下午4:40:05
     * @param userId
     * @return
     * @throws Exception
     */
    public String enCodeUserId(long userId) throws Exception {
        try {
            userId = userId * 357 + 462;
            String enCodeStr = "Es" + userId + "ohY";
            return enCoding(enCodeStr);
        } catch (Exception e) {
            throw new Exception("enCodeUserId faliure....userId:" + userId, e);
        }
    }
    
    /**
     * @Title: deCoding
     * @Description: base64解码
     * @author Kola 6089555
     * @date 2015年11月25日 下午4:18:44
     * @param str
     * @return
     * @throws Exception
     */
    public String deCoding(String str) throws Exception {
        try {
            String rot13Val = StringUtils.replace(Rot13.coding(str), "-_", "+/");
            String padVal = StringUtils.rightPad(rot13Val, rot13Val.length() % 4, "=");
            return new String(Base64.decodeBase64(padVal.getBytes()));
        } catch (Exception e) {
            throw new Exception("deCoding faliure....str:" + str, e);
        }
    }
    
    /**
     * @Title: enCoding
     * @Description: base64编码
     * @author Kola 6089555
     * @date 2015年11月25日 下午4:38:53
     * @param str
     * @return
     * @throws Exception
     */
    public String enCoding(String str) throws Exception {
        try {
            String enCodeVal = new String(Base64.encodeBase64(str.getBytes()));
            String replaceVal = StringUtils.replace(enCodeVal, "+/", "-_").replaceAll("=", "");
            return Rot13.coding(replaceVal);
        } catch (Exception e) {
            throw new Exception("enCoding faliure....str:" + str, e);
        }
    }
}

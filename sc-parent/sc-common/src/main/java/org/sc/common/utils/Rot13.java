package org.sc.common.utils;

/**
 * @ClassName: Rot13
 * @Description: Rot13编码解码
 * @author Kola 6089555
 * @date 2015年11月25日 下午3:24:47
 *       
 */
public class Rot13 {
    
    /**
     * @Title: coding
     * @Description: 编码解码
     * @author Kola 6089555
     * @date 2015年11月25日 下午3:57:51
     * @param str
     * @return
     * @throws Exception
     */
    public static String coding(String str) throws Exception {
        try {
            StringBuilder val = new StringBuilder();
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                if (c >= 'a' && c <= 'm')
                    c += 13;
                else if (c >= 'A' && c <= 'M')
                    c += 13;
                else if (c >= 'n' && c <= 'z')
                    c -= 13;
                else if (c >= 'N' && c <= 'Z')
                    c -= 13;
                val.append(c);
            }
            return val.toString();
        } catch (Exception e) {
            throw new Exception("rot13 coding faliure....", e);
        }
    }
    
}
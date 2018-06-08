package org.sc.common.utils;

import java.io.UnsupportedEncodingException;

public class CharByteConverter {

    /**
     * 全角转半角
     * 
     * @param str
     * @return
     * 
     * @author mjorcen
     * @email mjorcen@gmail.com
     * @dateTime Sep 27, 2014 2:51:50 PM
     * @version 1
     */
    @Deprecated
    public static final String toSingleByte(String str) {
        StringBuffer outStrBuf = new StringBuffer("");

        String Tstr = "";
        byte[] b = null;
        for (int i = 0; i < str.length(); i++) {
            Tstr = str.substring(i, i + 1);
            // 全角空格转换成半角空格
            if (Tstr.equals("　")) {
                outStrBuf.append(" ");
                continue;
            }
            try {
                b = Tstr.getBytes("unicode");
                // 得到 unicode 字节数据
                if (b[2] == -1) {
                    // 表示全角
                    b[3] = (byte) (b[3] + 32);
                    b[2] = 0;
                    outStrBuf.append(new String(b, "unicode"));
                } else {
                    outStrBuf.append(Tstr);
                }
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } // end for.
        return outStrBuf.toString();

    }

    /**
     * 半角转全角
     * 
     * @param str
     * @return
     * 
     * @author mjorcen
     * @email mjorcen@gmail.com
     * @dateTime Sep 27, 2014 2:52:06 PM
     * @version 1
     */
    @Deprecated
    public static final String toDoubleByte(String str) {
        StringBuffer outStrBuf = new StringBuffer("");
        String Tstr = "";
        byte[] b = null;
        for (int i = 0; i < str.length(); i++) {
            Tstr = str.substring(i, i + 1);
            if (Tstr.equals(" ")) {
                // 半角空格
                outStrBuf.append(Tstr);
                continue;
            }
            try {
                b = Tstr.getBytes("unicode");
                if (b[2] == 0) {
                    // 半角
                    b[3] = (byte) (b[3] - 32);
                    b[2] = -1;
                    outStrBuf.append(new String(b, "unicode"));
                } else {
                    outStrBuf.append(Tstr);
                }
                return outStrBuf.toString();
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        return outStrBuf.toString();
    }

    /**
     * 半角转全角
     * 
     * @param str
     * @return
     * 
     * @author mjorcen
     * @email mjorcen@gmail.com
     * @dateTime Sep 27, 2014 2:52:31 PM
     * @version 1
     */
    public static String ToSBC(String str) {
        char c[] = str.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == ' ') {
                c[i] = '\u3000';
            } else if (c[i] < '\177') {
                c[i] = (char) (c[i] + 65248);

            }
        }
        return new String(c);
    }

    /**
     * 全角转半角
     * 
     * @param str
     * @return
     * 
     * @author mjorcen
     * @email mjorcen@gmail.com
     * @dateTime Sep 27, 2014 2:52:50 PM
     * @version 1
     */
    public static String ToDBC(String str) {
        char c[] = str.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == '\u3000') {
                c[i] = ' ';
            } else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
                c[i] = (char) (c[i] - 65248);

            }
        }
        String returnString = new String(c);
        return returnString;
    }

    /**
     * 自动补全关键词
     * @param keyWord
     * @return
     */
    public static String  autoComplete(String keyWord){
    	String res ="";
    	if(keyWord!=null&&!keyWord.equals("")){
    		//全角补半角
        	keyWord = toSingleByte(keyWord.trim());
        	 String[] key = keyWord.split(" ");
        	 if(key!=null&&key.length>0){
        		for (int i = 0; i < key.length; i++) {
    				if(key[i]!=null&&!key[i].equals("")){
    					res+=(key[i]+"|");
    				}
    			}
        	 }
        	 
        	 //防止用户在前端输入带空格的关键词，带空格关键词pg检索会报错
        	 //需要把用户输入的空格换为|
        	 if(res!=null&&!res.equals("")){
        		 res = res.substring(0, res.length()-1);
        	 }	
    	}
    	 
    	return res;
    }
     
}
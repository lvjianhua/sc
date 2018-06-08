package org.sc.common.utils.web;



import javax.servlet.ServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * request工具类
 * @author Webb Dong
 *
 */
public class RequestUtils {
	/**
     * 获取请求Body
     *
     * @param request
     * @return
     */
    public static String getBodyString(ServletRequest request) {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = request.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                	throw new RuntimeException(e);
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                	throw new RuntimeException(e);
                }
            }
        }
        return sb.toString();
    }
    
    /**
     * 获取请求参数Map
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
	public static Map<String, String> getParamMap(ServletRequest request) {
    	Map<String, String> paramMap = new HashMap<>();
    	Map<String, String[]> parameterMap = request.getParameterMap();
    	for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
    		paramMap.put(entry.getKey(), entry.getValue()[0]);
		}
    	return paramMap;
    }
    
	@SuppressWarnings("rawtypes")
	public static Map<String, String> handleRequestMap(ServletRequest request) {
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		return params;
	}
    
    private RequestUtils() {}
}

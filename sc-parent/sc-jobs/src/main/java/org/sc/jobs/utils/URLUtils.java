package org.sc.jobs.utils;



import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.sc.common.utils.StringUtils;

/**
 * @author run
 * @create 2017-06-10 13:41
 **/
public class URLUtils {

    /**
     * 请求
     *
     * @param httpUrl
     * @param params
     * @return
     */
    public static String get(String httpUrl, Map<String, Object> params) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        StringBuffer httpArg = new StringBuffer();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            httpArg.append(entry.getKey() + "=" + entry.getValue() + "&");
        }
        if (!StringUtils.isEmpty(httpArg)) {
            httpUrl = httpUrl + "?" + httpArg.delete(httpArg.length() - 1, httpArg.length());
        }

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            // 填入apikey到HTTP header
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}

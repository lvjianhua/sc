package org.sc.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 流处理工具类
 * 
 * @author DongWenBin
 *
 */
public class StreamUtils {
	/**
	 * 读取流中的数据，并且转换为字符串
	 * @param inputStream
	 * @return
	 */
	public static String readInputStream(InputStream inputStream) {
		try {
			StringBuffer sb = new StringBuffer();
			String s;
			BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			while ((s = in.readLine()) != null) {
				sb.append(s);
			}
			return sb.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private StreamUtils() {
	}
}

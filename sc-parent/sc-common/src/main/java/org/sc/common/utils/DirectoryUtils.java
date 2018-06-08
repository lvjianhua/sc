package org.sc.common.utils;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 目录工具类
 * @author Webb Dong
 *
 */
public class DirectoryUtils {
	/**
	 * 根据当前时间生成目录文件名
	 * @return
	 */
	public static String generateDirectoryByDate() {
		// 图片名称生成策略
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Date now = new Date();
		// 文件命名
		String fileNameFormat = df.format(now);
		Random r = new Random();
		fileNameFormat += (r.nextInt(900) + 100);
		
		df = new SimpleDateFormat("yyyyMM");
		
		StringBuilder sb = new StringBuilder();
		
		return sb.append(df.format(now)).append(File.separator).append(fileNameFormat).toString();
	}
	
	private DirectoryUtils() {}
}

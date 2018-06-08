package org.sc.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5工具类
 * @author DongWenBin
 *
 */
public class MD5Utils {
	/**
	 * MD5加密转换成16进制数
	 * @param plainText
	 * @return
	 */
	public static String md5Encrypt(String plainText) {
		try {
			return hexString(encrypt(plainText));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 十六进制
	 * @param bytes
	 * @return
	 */
	public static String hexString(byte[] bytes) {
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			int val = ((int) bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	/**
	 * MD5加密
	 * @param info
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] encrypt(String info) throws NoSuchAlgorithmException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		byte[] srcBytes = info.getBytes();
		// 使用srcBytes更新摘要
		md5.update(srcBytes);
		// 完成哈希计算，得到result
		byte[] resultBytes = md5.digest();
		return resultBytes;
	}
	
	private MD5Utils() {
	}
}

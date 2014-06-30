package com.zxw.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5 加密
 * 
 * @author 作者: zxw E-mail: veond@163.com
 * @since 创建时间：2014-4-22 下午4:21:07
 * 
 */

public class MD5Util {

	/**
	 * 字符串转成MD5
	 * 
	 * @param str
	 * @return
	 */
	public static String toMD5(String str) {
		StringBuffer result = new StringBuffer();
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		byte[] byteArray = messageDigest.digest();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
				result.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			}else {
				result.append(Integer.toHexString(0xFF & byteArray[i]));
			}
		}
		return result.toString().toUpperCase();
	}

}

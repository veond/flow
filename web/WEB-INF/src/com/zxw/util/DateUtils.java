/**
 * 日期工具
 */

package com.zxw.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	
	/**
	 * 格式：yyyy-MM-dd HH:mm:ss
	 */
	public static String DATE_PATTERN_YMDHMS = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 格式化日期
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formart(Date date, String pattern) {
		if(date == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

}

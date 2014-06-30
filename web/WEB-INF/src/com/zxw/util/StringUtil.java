package com.zxw.util;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	
	/**
	 * 获得uuid 作为数据库的key 
	 * @return
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString();		
	}

	/**
	 * 判断传入的 str 是否是null (当值为null 或者 str.trim() =="" 返回 true, 其它返回false) 
	 * @param str
	 * @return
	 */
	public static boolean isEntity(String str) {
		if(str == null || str.trim().equals("")) {
			return false;
		}
		return true;
	}
	
	/**
	 * 将数据库的字段名称，转换成 model 里面的变量名称 （如：create_time 转 createTime）
	 * @param columnName
	 * @return
	 */
	public static String columnToVarName(String columnName) {
		if(columnName == null || columnName.trim().equals("")) {
			return "";			
		}
		String temp = "";
		String[] strs = columnName.trim().split("_");
		if(strs.length <= 0) {
			return columnName.trim();
		}
		for(int i=1; i<strs.length; i++) {
			temp += strs[i].replaceFirst(strs[i].substring(0,1), strs[i].substring(0,1).toUpperCase());			
		}
		return (strs[0]+temp);
	}

	/**
	 * 根据model中的字段名，转换出对应SQL的字段名 (如：createTime 转 create_time)
	 * @param modelVar
	 * @return
	 */
	public static String varNameToColumn(String modelVar) {
		if(modelVar == null || modelVar.trim().equals("")) {
			return "";			
		}
		Pattern p = Pattern.compile("[A-Z]");
		Matcher m = p.matcher(modelVar);
		while(m.find()) {
			modelVar = modelVar.replaceFirst(m.group(), "_"+m.group().toLowerCase());
		}
		return (modelVar);
	}

	/**
	 * 首字母转成大写
	 * @param str
	 * @return
	 */
	public static String toFirstUpperCase(String str) {
		if(str != null && !"".equals(str)) {
			char[] chs = str.trim().toCharArray();
			if(chs[0] >= 'a' && chs[0] <= 'z') {
				chs[0] -= 32;				
				return new String(chs);
			} 
			return str;
		}
		return "";
	}


	/**
	 * 首字母转成小写
	 * @param str
	 * @return
	 */
	public static String toFirstLowerCase(String str) {
		if(str != null && !"".equals(str)) {
			char[] chs = str.trim().toCharArray();
			if(chs[0] >= 'A' && chs[0] <= 'Z') {
				chs[0] += 32;				
				return new String(chs);
			} 
			return str;
		}
		return "";
	}
	
	
}

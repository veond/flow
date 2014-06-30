package com.zxw.util; 

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;


/** 
 * 工作项里面条件解析
 * 	借助java 自带的javaScript语法解析引擎，来执行 javascript 里面的条件判断语句（data>2 && data2==2）， 这样的方法获得执行结果
 * 	 详细关于： JavaScript引擎介绍请见：http://wenku.baidu.com/link?url=OgwlvmznfUu5MtV4S19FPWV44lIHVe1_25Kfsez1JDEpANLoilNI1x3p7saUC2MFdZnM-DHSZEKGMiKHw2BtCxsInUw4yYGdN9lHLTem_KK
 *  java自带的ScriptEngine 低层引用 Mozilla+Rhino 一个开源的项目，这个项目主要是让java 和 javascript 进行交互使用 ， 在JS语法里面可以直接使用java的对象或是实现java的接口，或是在java里面解析js的方法或是变量等等任何东西.....  
 * @author 作者: zxw    
 *		E-mail: veond@163.com
 * @since 创建时间：2014-3-7 下午4:15:24 
 * 
 */

public class ConditionScriptParse {
	
	/**
	 * JS 解析引擎
	 */
	public static ScriptEngine jsEngine = new ScriptEngineManager().getEngineByName("js");
	
	
	/**
	 * 比较表达式的数据是否为真
	 * @param conditionStr  表达式     data>3 && data2==5
	 * @param dataMap 表达式里面的数据   [data->4, data2->5] : result true
	 * @return 
	 * @throws ScriptException
	 */
	public static boolean eavl(String conditionStr, Map<String, Object> dataMap) {
		if(conditionStr!=null && !conditionStr.trim().equals("") && dataMap != null && dataMap.size()>0) {
			Set<Entry<String, Object>> entrys = dataMap.entrySet();
			Iterator<Entry<String, Object>> it = entrys.iterator();
			Entry<String, Object> tempEntry = null;
			while(it.hasNext()) {
				tempEntry = it.next();
				jsEngine.put(tempEntry.getKey(), tempEntry.getValue());
			}
			try {
				return Boolean.parseBoolean(jsEngine.eval(conditionStr).toString());
			} catch (ScriptException e) {
				e.printStackTrace();
			}
		}
		return false;	
	}

}



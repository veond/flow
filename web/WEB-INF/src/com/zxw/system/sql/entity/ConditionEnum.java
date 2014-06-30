package com.zxw.system.sql.entity; 

/** 
 * SQL 条件块的枚举
 * @author 作者: zxw    
 *		E-mail: veond@163.com
 * @since 创建时间：2014-3-13 下午5:39:11 
 * 
 */

public enum ConditionEnum {
	
	GT(">"), GT_EQUAL(">="), LT("<"), LT_EQUAL("<="), EQUAL("="), NOT_EQUAL("!="), IN(" IN"), NOT_IN(" NOT IN"), DEFAULT("");
	
	private String value = "";
	
	ConditionEnum(String value) {
		this.value = value;		
	}

	@Override
	public String toString() {
		return this.value;
	}
}



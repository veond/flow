package com.zxw.system.sql.entity; 

/** 
 * SQL 反转的连接类型
 * @author 作者: zxw    
 *		E-mail: veond@163.com
 * @since 创建时间：2014-3-13 下午5:25:01 
 * 
 */
public enum ConnectionEnum {
	
	AND(" AND "), OR(" OR "), NULL(" ");
	
	private String value = "";
	
	ConnectionEnum(String value) {
		this.value = value;				
	}
	
	@Override
	public String toString() {
		return value;		
	}
	

}



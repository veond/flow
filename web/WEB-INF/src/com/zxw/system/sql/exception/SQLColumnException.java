package com.zxw.system.sql.exception; 

/** 
 * SQL 字段异常
 * @author 作者: zxw    
 *		E-mail: veond@163.com
 * @since 创建时间：2014-4-16 下午5:54:07 
 * 
 */

public class SQLColumnException extends RuntimeException {
	
	private static final long serialVersionUID = 3924352473884705583L;

	public SQLColumnException() {
		super();
	}

	public SQLColumnException(String message) {
		super(message);
	}
}



package com.zxw.system.sql.exception; 

/** 
 * 数据初始化异常
 * @author 作者: zxw    
 *		E-mail: veond@163.com
 * @since 创建时间：2014-3-11 下午1:51:55 
 * 
 */

public class SQLReverseInitException extends RuntimeException {

	private static final long serialVersionUID = 795823788333763938L;

	public SQLReverseInitException() {
		super();
	}

	public SQLReverseInitException(String message) {
		super(message);
	}
	

}



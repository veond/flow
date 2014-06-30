package com.zxw.system.exception; 

/** 
 * 判断条件为null时异常
 * @author 作者: zxw    
 *		E-mail: veond@163.com
 * @since 创建时间：2014-3-6 下午2:19:38 
 * 
 */

public class ConditionsIsNullException extends RuntimeException {

	private static final long serialVersionUID = 1536820864367312375L;

	public ConditionsIsNullException() {
		super();
	}
	
	public ConditionsIsNullException(String message) {
		super(message);
	}
	

}



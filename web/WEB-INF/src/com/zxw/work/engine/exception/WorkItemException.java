package com.zxw.work.engine.exception; 

/** 
 * work item 数据异常
 * @author 作者: zxw    
 *		E-mail: veond@163.com
 * @since 创建时间：2014-3-11 上午10:40:12 
 * 
 */

public class WorkItemException extends RuntimeException {

	private static final long serialVersionUID = -7846756046316929704L;
	
	public WorkItemException() {
		super();
	}
	
	public WorkItemException(String message) {
		super(message);
	}
	

}



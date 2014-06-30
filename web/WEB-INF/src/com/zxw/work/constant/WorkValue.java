package com.zxw.work.constant; 

/** 
 * 工作流 常量值
 * @author 作者: zxw    
 *		E-mail: veond@163.com
 * @since 创建时间：2014-3-11 下午3:35:24 
 * 
 */

public interface WorkValue {
	
	/*************** 工作流程状态 **********************/
	/**
	 * 流程状态， 活动状态
	 */
	public static final int FLOW_STATUS_ACTIVITY = 1;
	
	/**
	 * 流程状态， 结束状态
	 */
	public static final int FLOW_STATUS_FINISH = 2;
	
	
	/*************** 工作项节点类型 **********************/
	/**
	 * 开始节点
	 */
	public static final int WORK_ITEM_TYPE_START = 0;
	
	/**
	 * 结束节点
	 */
	public static final int WORK_ITEM_TYPE_END = 1;
	
	/**
	 * 连接线
	 */
	public static final int WORK_ITEM_TYPE_LINK = 2;
	
	/**
	 * 手工活动
	 */
	public static final int WORK_ITEM_TYPE_HANDWORK = 3; 
	
	
	/*************** form 表单 ***********************/
	/**
	 * form url forward (forward 时，不能解析外部的连接, forward时需要配制出JSP的绝对路径)
	 */
	public static final int WORK_FORM_FORWARD = 1;
	
	/**
	 * form url 重定向  (重定向，可以直接定位到外部的连接)
	 */
	public static final int WORK_FORM_REDIRECT = 2; 
	
	
	
}



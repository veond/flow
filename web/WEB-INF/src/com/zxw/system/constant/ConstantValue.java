package com.zxw.system.constant;

/**
 * 静态 常量值
 * @author 19lou-zxw
 * 
 * 规则：
 * 	如：状态值， 都是以 status 开头的 、   user信息值都是以user开头的,  以此类推
 * 
 *
 */
public interface ConstantValue {
	
	/**
	 * 正常状态
	 */
	public int STATUS_NORMAL = 1;
	
	/**
	 * 删除状态 
	 */
	public int STATUS_DELETE = -1;
	
	/**
	 * 根节点的父ID值
	 */
	public String TREE_ROOT_ID = "root";
	
	
	
	/***************** 用户信息 常量 ************************/
	/**
	 * 用户默认的密码
	 */
	public String USER_DEFAULT_PASS = "123456";
	/**
	 * 用户 正常状态
	 */
	public int USER_STATUS_NORMAL = 1;
	/**
	 * 用户 锁定状态
	 */
	public int USER_STATUS_LOCK = 0;
	/**
	 * 用户删除状态
	 */
	public int USER_STATUS_DELETE = -1;
	
	
	
	/************** sqlsession 的名称*********************/
	/**
	 * 组织机构 库的sqlsession 
	 */
	public String SQLSESSION_ORGANIZATION = "organization_sqlSession";

	/**
	 * 工作流引擎的数据库
	 */
	public String SQLSESSION_WORK = "work_sqlSession"; 
	
	
	/************** 小图标的常量值 *******************/
	/**
	 * 男性小图标
	 */
	public String ICON_USER_MALE= "images/user_male.png";


	/************** 用户信息 ********************/
	/**
	 * session 中用户信息的 key
	 */
	public String CURRENT_USER_KEY = "currentUser";

}




















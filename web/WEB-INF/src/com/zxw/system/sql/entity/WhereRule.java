package com.zxw.system.sql.entity;


/**
 * where 条件块的东西， 包含了比较规则及后面的连接规则 （and 还是 or的）
 * 
 * @author 19lou-zxw 
 * 
 */
public class WhereRule {

	/**
	 * where 条件块东西
	 * 
	 * @param column
	 *            比较的数据库字段名
	 * @param condition
	 *            判断条件
	 * @param value (1)此值为null时代表 column 转成 #{变量},
	 *            (2)比较的值，需要自己拼接格式， 如：${tt}, (aa, bb, ccc), 'aa', ${dd} , aa
	 * @param connect
	 *            与后面的连接规则
	 */
	public WhereRule(String column, ConditionEnum condition, String value, ConnectionEnum connect) {
		super();
		this.column = column;
		this.condition = condition;
		this.connect = connect;
		this.value = value;
	}
	
	/**
	 * 比较的数据库字段名
	 */
	private String column = "";

	/**
	 * 比较条件如： > 、 < 、 >= 、 <= .... 等等 , 默认为""
	 */
	private ConditionEnum condition = ConditionEnum.DEFAULT;

	/**
	 * 此值为null时代表 column 转成 #{变量}
	 * 比较的值，需要自己拼接格式， 如：${tt}, (aa, bb, ccc), 'aa', ${dd} , aa
	 */
	private String value = "";

	/**
	 * 后面连接条件 and 、 or
	 */
	private ConnectionEnum connect = ConnectionEnum.NULL;

	public ConditionEnum getCondition() {
		return condition;
	}

	public void setCondition(ConditionEnum condition) {
		this.condition = condition;
	}

	public ConnectionEnum getConnect() {
		return connect;
	}

	public void setConnect(ConnectionEnum connect) {
		this.connect = connect;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	
}

/**
 *  下拉列表选择框，对应的数据模型
 */

package com.zxw.system.extjs;

import java.io.Serializable;

public class SelectedOption implements Serializable{
	
	private static final long serialVersionUID = 8414443033746240583L;

	/**
	 * 显示的option名称
	 */
	private String optionName = "";
	
	/**
	 * 对应的option值
	 */
	private Object optionValue = null;

	public String getOptionName() {
		return optionName;
	}

	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}

	public Object getOptionValue() {
		return optionValue;
	}

	public void setOptionValue(Object optionValue) {
		this.optionValue = optionValue;
	}
	
	

}

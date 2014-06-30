package com.zxw.work.engine.model;

import java.io.Serializable;

import com.zxw.system.model.BaseModel;
import com.zxw.work.constant.WorkValue;

/**
 * 表单处理信息
 * 
 * @author 作者: zxw E-mail: veond@163.com
 * @since 创建时间：2014-4-16 下午5:28:51
 * 
 */

public class WorkForm extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1152587863459836378L;

	private String id = "";
	private String url = ""; // '处理表单的URL',
	private int formType = WorkValue.WORK_FORM_FORWARD;  //表单处理跳转类型，forward 、 redirect

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getFormType() {
		return formType;
	}

	public void setFormType(int formType) {
		this.formType = formType;
	}

}

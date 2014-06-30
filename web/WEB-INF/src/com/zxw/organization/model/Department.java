package com.zxw.organization.model;

import com.zxw.system.model.BaseModel;

/**
 * 部门
 * 
 * @author 19lou-zxw
 * 
 */
public class Department extends BaseModel {

	private String id = "";
	private String name = ""; // 部门名称
	private String parentId = "";   //上级部门ID

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

}

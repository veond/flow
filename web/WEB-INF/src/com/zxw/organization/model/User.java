package com.zxw.organization.model;

import java.io.Serializable;

import com.zxw.system.constant.ConstantValue;
import com.zxw.system.model.BaseModel;

public class User extends BaseModel implements Serializable{
	private static final long serialVersionUID = -6238037529650254992L;
	
	private String id = "";
	private String deptId = "";
	private String roleId = "";
	private String username = "";
	private String loginname = "";
	private String userpass = "";
	private int status = ConstantValue.USER_STATUS_NORMAL; // 用户的状态 -1为删除、
															// 0为锁定状态、 1为正常状态

	/******** 显示所用 ***********/
	private String roleName = ""; // 角色名称
	private String deptName = "";// 部门名称

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getUserpass() {
		return userpass;
	}

	public void setUserpass(String userpass) {
		this.userpass = userpass;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

}

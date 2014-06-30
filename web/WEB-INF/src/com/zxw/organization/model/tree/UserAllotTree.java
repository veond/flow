/**
 * 角色管理里面， 分配用户的窗体的 tree
 */

package com.zxw.organization.model.tree;

import java.io.Serializable;

import com.zxw.system.extjs.CheckedTree;

public class UserAllotTree extends CheckedTree implements Serializable {

	private static final long serialVersionUID = -4153738223791894095L;

	private String roleId = ""; //角色ID

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}

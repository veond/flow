/**
 * 角色 信息
 */

package com.zxw.organization.model;

import java.io.Serializable;

import com.zxw.system.model.BaseModel;

public class Role extends BaseModel implements Serializable{

	private static final long serialVersionUID = 3247234384823759523L;
	
	private String id = "";
	private String name = "";

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

}

package com.zxw.work.engine.model;

import java.io.Serializable;

import com.zxw.system.model.BaseModel;

/**
 * 工作项 关联前质节点
 * 
 * @author 19lou-zxw
 * 
 */
public class PreposeItem extends BaseModel implements Serializable {

	private static final long serialVersionUID = 873418852233188403L;

	private String id = "";
	private String itemId = ""; // 关联工作项ID
	private String preposeId = ""; // 前置ID （就是工作项里面的ID）

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getPreposeId() {
		return preposeId;
	}

	public void setPreposeId(String preposeId) {
		this.preposeId = preposeId;
	}

}

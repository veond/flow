package com.zxw.work.engine.model;

import java.io.Serializable;

import com.zxw.system.model.BaseModel;

/**
 * 工作项 关联后置节点
 * 
 * @author 19lou-zxw
 * 
 */
public class PostpositionItem extends BaseModel implements Serializable {

	private static final long serialVersionUID = 321381434205892241L;

	private String id = "";
	private String itemId = ""; // 关联工作项ID
	private String postpositionId = ""; // 后置ID （就是工作项里面的ID）

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

	public String getPostpositionId() {
		return postpositionId;
	}

	public void setPostpositionId(String postpositionId) {
		this.postpositionId = postpositionId;
	}

}

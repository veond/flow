/**
 * 对应工作模型， 存放模型每个节点的信息  
 */

package com.zxw.work.engine.model;

import java.io.Serializable;
import java.util.List;

import com.zxw.system.model.BaseModel;

/**
 * 工作项类型中有个“连接线” 这个主要是记录后置节点的条件的
 * 
 * @author 19lou-zxw
 * 
 */

public class WorkItem extends BaseModel implements Serializable {

	private static final long serialVersionUID = 7123177391710555412L;

	private String id = "";
	private String name = ""; // 节点名称
	private String workModelId = ""; // 此节点对应模型ID
	private String workFormId = ""; // 此节点对应处理表单ID
	private int itemType = 0; // 工作项的类型 0：开始 1： 结束 2：连接线 3：手工活动
	private String itemCondition = ""; // 节点条件
	private String partakeUser = ""; // 参数者

	/****** 关联信息 ( 前后节点列表 ) ********/
	private List<PreposeItem> preposeList_ = null;
	private List<PostpositionItem> postpositionList_ = null;
	/******* 前后置节点对应的item 信息 *********/
	private List<WorkItem> preposeItemList_ = null;
	private List<WorkItem> postposItemList_ = null;

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

	public String getWorkModelId() {
		return workModelId;
	}

	public void setWorkModelId(String workModelId) {
		this.workModelId = workModelId;
	}

	public int getItemType() {
		return itemType;
	}

	public void setItemType(int itemType) {
		this.itemType = itemType;
	}

	public String getItemCondition() {
		return itemCondition;
	}

	public void setItemCondition(String itemCondition) {
		this.itemCondition = itemCondition;
	}

	public String getPartakeUser() {
		return partakeUser;
	}

	public void setPartakeUser(String partakeUser) {
		this.partakeUser = partakeUser;
	}

	public List<PreposeItem> getPreposeList_() {
		return preposeList_;
	}

	public void setPreposeList_(List<PreposeItem> preposeList_) {
		this.preposeList_ = preposeList_;
	}

	public List<PostpositionItem> getPostpositionList_() {
		return postpositionList_;
	}

	public void setPostpositionList_(List<PostpositionItem> postpositionList_) {
		this.postpositionList_ = postpositionList_;
	}

	public List<WorkItem> getPreposeItemList_() {
		return preposeItemList_;
	}

	public void setPreposeItemList_(List<WorkItem> preposeItemList_) {
		this.preposeItemList_ = preposeItemList_;
	}

	public List<WorkItem> getPostposItemList_() {
		return postposItemList_;
	}

	public void setPostposItemList_(List<WorkItem> postposItemList_) {
		this.postposItemList_ = postposItemList_;
	}

	public String getWorkFormId() {
		return workFormId;
	}

	public void setWorkFormId(String workFormId) {
		this.workFormId = workFormId;
	}

}

package com.zxw.work.engine.model;

import com.zxw.system.model.BaseModel;
import com.zxw.work.constant.WorkValue;

import java.io.Serializable;

/**
 * 待办工作流信息
 * @author 19lou-zxw
 *
 */
public class WorkTodo extends BaseModel implements Serializable {

	private static final long serialVersionUID = -3543496991040071828L;

	private String id = "";
	private String workModelId = "";
	private String workItemId = "";
	private String workItemName = "";
	private String processInstance = "";
	private String partakeUser = "";
	private int status = WorkValue.FLOW_STATUS_ACTIVITY;  //流程状态

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWorkModelId() {
		return workModelId;
	}

	public void setWorkModelId(String workModelId) {
		this.workModelId = workModelId;
	}

	public String getWorkItemId() {
		return this.workItemId;
	}

	public void setWorkItemId(String workItemId) {
		this.workItemId = workItemId;
	}

	public String getWorkItemName() {
		return workItemName;
	}

	public void setWorkItemName(String workItemName) {
		this.workItemName = workItemName;
	}

	public String getProcessInstance() {
		return processInstance;
	}

	public void setProcessInstance(String processInstance) {
		this.processInstance = processInstance;
	}

	public String getPartakeUser() {
		return partakeUser;
	}

	public void setPartakeUser(String partakeUser) {
		this.partakeUser = partakeUser;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
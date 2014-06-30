package com.zxw.work.engine.model;

import java.io.Serializable;
import java.util.Date;

import com.zxw.system.model.BaseModel;
import com.zxw.work.constant.WorkValue;

/**
 * 已办工作流 信息
 * 
 * @author 19lou-zxw
 * 
 */
public class WorkHaveTodo extends BaseModel implements Serializable {
	
	private static final long serialVersionUID = -1518181311161282069L;
	
	private String id = "";
	private String workModelId = "";
	private String workItemId = "";
	private String workItemName = "";
	private String processInstance = "";
	private String partakeUser = "";
	private int status = WorkValue.FLOW_STATUS_ACTIVITY;  //流程状态
	private Date handleDate = null; // 处理时间

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
		return workItemId;
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

	public Date getHandleDate() {
		return handleDate;
	}

	public void setHandleDate(Date handleDate) {
		this.handleDate = handleDate;
	}

}
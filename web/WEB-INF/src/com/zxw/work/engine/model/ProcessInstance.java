/**
 * 流程实例  （记录当前流程关联模型、 当前所处的节点位置）
 */

package com.zxw.work.engine.model;

import java.io.Serializable;

import com.zxw.system.model.BaseModel;

public class ProcessInstance extends BaseModel implements Serializable {

	private static final long serialVersionUID = -1798582277422601461L;
	
	private String id = "";
	private String workModelId = ""; // 关联模型ID
	private String workItemId = ""; // 当前流程所在节点ID

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

	public String getWorKItemId() {
		return workItemId;
	}

	public void setWorKItemId(String worKItemId) {
		this.workItemId = worKItemId;
	}

}

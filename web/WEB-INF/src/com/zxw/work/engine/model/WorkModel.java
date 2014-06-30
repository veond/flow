/**
 * 这里面定义各种工作流 模型  (其实就是工作流的一个总的信息)
 */

package com.zxw.work.engine.model;

import java.io.Serializable;

import com.zxw.system.model.BaseModel;

public class WorkModel extends BaseModel implements Serializable {

	private static final long serialVersionUID = 2298537433268984329L;

	private String id = "";
	private String name = "";
	private String filePath = ""; // 工作流生成的文件位置

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

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}

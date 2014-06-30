/**
 *  基础model 类
 */

package com.zxw.system.model;

import java.io.Serializable;
import java.util.Date;

import com.zxw.util.DateUtils;

public class BaseModel implements Serializable{
	
	private static final long serialVersionUID = -6520999614516036914L;
	
	private Date updateAt = null; // 更新时间
	private Date createAt = new Date(); // 创建时间
	private String remark = ""; // 备注信息
	
	/*** date 日期显示的字符串，只为显示所用 ***/
	private String updateAtStr = "";
	private String createAtStr = "";

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUpdateAtStr() {
		if(this.updateAt != null) {
			return DateUtils.formart(updateAt, "yyyy-MM-dd HH:mm:ss");
		}
		return updateAtStr;
	}

	public void setUpdateAtStr(String updateAtStr) {
		this.updateAtStr = updateAtStr;
	}

	public String getCreateAtStr() {
		if(this.createAt != null) {
			return DateUtils.formart(createAt, DateUtils.DATE_PATTERN_YMDHMS);
		}
		return createAtStr;
	}

	public void setCreateAtStr(String createAtStr) {
		this.createAtStr = createAtStr;
	}

}

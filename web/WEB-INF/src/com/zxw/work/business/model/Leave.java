/**
 * 工作流引擎 请假流程
 */

package com.zxw.work.business.model;

import java.io.Serializable;
import java.util.Date;

import com.zxw.system.model.BaseModel;
import com.zxw.util.DateUtils;

/**
 * @author 19lou-zxw
 *
 */
public class Leave extends BaseModel implements Serializable {
	private static final long serialVersionUID = -2824401872987487906L;

	private String id = "";
	private String msg = ""; // 请假信息
	private Date startDate = null; // 请假开始日期
	private Date endDate = null; // 请假开始日期
	private int leaveDay = 0; // 请假天数
	private String leaveUserId = ""; // 请假者ID
	private String leaveUserName = ""; // 请假者 名称
	private String processInstance = ""; // 流程实例ID
	
	//时间显示用
	private String startDateStr_ = "";
	private String endDateStr_ = "";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getLeaveDay() {
		return leaveDay;
	}

	public void setLeaveDay(int leaveDay) {
		this.leaveDay = leaveDay;
	}

	public String getLeaveUserId() {
		return leaveUserId;
	}

	public void setLeaveUserId(String leaveUserId) {
		this.leaveUserId = leaveUserId;
	}

	public String getLeaveUserName() {
		return leaveUserName;
	}

	public void setLeaveUserName(String leaveUserName) {
		this.leaveUserName = leaveUserName;
	}

	public String getProcessInstance() {
		return processInstance;
	}

	public void setProcessInstance(String processInstance) {
		this.processInstance = processInstance;
	}

	public String getStartDateStr_() {
		return DateUtils.formart(startDate, DateUtils.DATE_PATTERN_YMDHMS);
	}

	public void setStartDateStr_(String startDateStr_) {
		this.startDateStr_ = startDateStr_;
	}

	public String getEndDateStr_() {
		return DateUtils.formart(endDate, DateUtils.DATE_PATTERN_YMDHMS);
	}

	public void setEndDateStr_(String endDateStr_) {
		this.endDateStr_ = endDateStr_;
	}

}

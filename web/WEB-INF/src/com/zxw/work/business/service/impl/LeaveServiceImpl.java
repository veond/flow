package com.zxw.work.business.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.zxw.organization.model.User;
import com.zxw.system.constant.ConstantValue;
import com.zxw.system.service.BaseService;
import com.zxw.system.web.JsonResult;
import com.zxw.system.web.PageInfo;
import com.zxw.util.StringUtil;
import com.zxw.work.business.mapper.LeaveMapper;
import com.zxw.work.business.model.Leave;
import com.zxw.work.business.service.LeaveService;
import com.zxw.work.engine.model.ProcessInstance;
import com.zxw.work.engine.service.WorkEngineService;

@Service
public class LeaveServiceImpl extends BaseService implements LeaveService {
	
	@Autowired
	private WorkEngineService workEngineService;
	
	/**
	 * 请假模型ID 
	 */
	private @Value("#{WorkModelId.leave_id}") String leaveModelId = "";
	
	/**
	 * 请假流程名称
	 */
	private @Value("#{WorkModelId.leave_name}") String leaveModelName = "";

	
	public LeaveMapper getMapper() {
		return super.getMapper(ConstantValue.SQLSESSION_WORK, LeaveMapper.class);
	}

	@Override
	public PageInfo getPageInfo(int start, int limit) {
		PageInfo pageInfo = new PageInfo();
		if(limit<=0 || start<0 || start>limit) {
			return pageInfo;
		}
		int count = this.getMapper().getCount();
		pageInfo.setTotalCount(count);
		if(count > 0 && count>start) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("start", start);
			param.put("limit", limit);
			pageInfo.setItems(this.getMapper().getList(start, limit));
		}
		return pageInfo;
	}

	/*@Override  此流程暂不提供删除
	public JsonResult batDelete(List<String> idList) {
		if(idList != null && idList.size()>0) {
			String ids = "";
			for(String id : idList) {
				ids += "'"+id+"',";
			}
			//根据ID，获得对应实例ID
			List<String> processIds = this.getMapper().getByProcessIdsByIds(ids);  
			//删除实例信息
			workEngineService.batDelProcessByIds(processIds);
			//删除待办
			
			//删除已办
			
			//删除请假流程信息
			this.getMapper().batDelete(ids.substring(0, ids.length()-1));
		}
		return new JsonResult(true, "删除成功", null);
	}*/

	/**
	 * 先增请假工作流程
	 */
	@Override
	public JsonResult addOrUpdate(Leave leave, User user) {
		if(user == null) {
			return new JsonResult(false, "未登录，请重新登录...");
		}
		JsonResult jr = new JsonResult(false, "");
		if(StringUtil.isEntity(leave.getId())) {  //更新
			this.getMapper().update(leave);			
			jr.setIsSuccess(true);
			jr.setMsg("更新成功");
		}else {  //新加
			//启动一条请假的工作流
			ProcessInstance pi = workEngineService.startWork(leaveModelId, user, leaveModelName);
			if(pi == null) {
				jr.setMsg("流程启动失败.... 请重试");
				return jr;				
			}
			leave.setProcessInstance(pi.getId());
			leave.setLeaveUserId(user.getId());
			leave.setLeaveUserName(user.getUsername());
			//增加请假详情
			this.getMapper().insert(leave);
			
			jr.setIsSuccess(true);
			jr.setData(leave.getId());
			jr.setMsg("增加成功");
		}
		return jr;
	}


}

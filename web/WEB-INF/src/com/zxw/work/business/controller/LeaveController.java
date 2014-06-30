/**
 * 工作流引擎 请假流程
 */

package com.zxw.work.business.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zxw.organization.model.User;
import com.zxw.system.web.BaseController;
import com.zxw.system.web.JsonResult;
import com.zxw.system.web.PageInfo;
import com.zxw.work.business.model.Leave;
import com.zxw.work.business.service.LeaveService;


@Controller
@RequestMapping("/work/leave")
public class LeaveController extends BaseController {
	
	@Autowired
	private LeaveService leaveService = null;
	
	@RequestMapping("/getPageInfo")
	@ResponseBody
	public PageInfo getPageInfo(@RequestParam(required=true) int start, 
			@RequestParam(required=true) int limit) {
		return leaveService.getPageInfo(start, limit);
	}
	
	/* 
	 * 此业务暂不提供删除
	@RequestMapping("/batDelete")
	@ResponseBody
	public JsonResult batDelete(@RequestBody List<String> idList) {
		return leaveService.batDelete(idList);
	}*/
	
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public JsonResult addOrUpdate(@RequestBody Leave leave, HttpServletRequest request) {
		return leaveService.addOrUpdate(leave, super.getCurrentUser(request));
	}
	
	
	

}

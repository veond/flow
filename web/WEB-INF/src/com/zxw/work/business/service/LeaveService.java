package com.zxw.work.business.service;

import java.util.List;

import com.zxw.organization.model.User;
import com.zxw.system.web.JsonResult;
import com.zxw.system.web.PageInfo;
import com.zxw.work.business.model.Leave;


public interface LeaveService {

	PageInfo getPageInfo(int start, int limit);

//	JsonResult batDelete(List<String> idList);

	JsonResult addOrUpdate(Leave leave, User user);



}

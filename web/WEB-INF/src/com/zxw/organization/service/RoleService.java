package com.zxw.organization.service;

import java.util.List;

import com.zxw.organization.model.Role;
import com.zxw.system.extjs.TreePanel;
import com.zxw.system.web.JsonResult;
import com.zxw.system.web.PageInfo;

public interface RoleService {
	
	public Role getById(String id);

	public JsonResult addOrUpdate(Role role);

	public PageInfo getPageInfo(int start, int limit);

	public JsonResult batDelete(List<String> roleIds);

	public List<TreePanel> getTree();

}

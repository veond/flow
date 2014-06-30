/**
 * 角色信息
 */

package com.zxw.organization.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zxw.organization.model.Role;
import com.zxw.organization.service.RoleService;
import com.zxw.system.extjs.TreePanel;
import com.zxw.system.web.JsonResult;
import com.zxw.system.web.PageInfo;

@Controller
@RequestMapping("/organization/role")
public class RoleController {

	@Autowired
	private RoleService roleService = null;
	
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public JsonResult addOrUpdate(@RequestBody Role role) {
		return roleService.addOrUpdate(role);
	}
	
	@RequestMapping("/getPageInfo")
	@ResponseBody
	public PageInfo getPageInfo(@RequestParam(required=true) int start, 
			@RequestParam(required=true) int limit) {
		return roleService.getPageInfo(start, limit);
	}
	
	@RequestMapping("/batDelete")
	@ResponseBody
	public JsonResult batDelete(@RequestBody List<String> roleIds) {
		return roleService.batDelete(roleIds);
	}
	

	/**
	 * 得到角色列表 只有一级
	 * @return
	 */
	@RequestMapping("/get_tree.html")
	@ResponseBody
	public List<TreePanel> roleTree() {
		return roleService.getTree();
	}

}

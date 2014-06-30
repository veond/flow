package com.zxw.organization.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zxw.organization.model.Department;
import com.zxw.organization.service.DepartmentService;
import com.zxw.system.extjs.TreePanel;
import com.zxw.system.web.JsonResult;


/**
 * 部门 控制器
 * @author 19lou-zxw
 *
 */

@Controller
@RequestMapping("/organization/dept")
public class DepartmentController {
	
	@Autowired
	private DepartmentService departmentService = null;
	
	
	/**
	 * 得到部门树
	 * @return
	 */
	@RequestMapping("/get_tree.html")
	@ResponseBody
	public List<TreePanel> departmentTree() {
		return departmentService.getDeptTree();
	}
	
	/**
	 * 增加部门
	 * @param dept
	 * @return
	 */
	@RequestMapping(value="/add", method=RequestMethod.POST)
	@ResponseBody
	public JsonResult addDept(HttpServletRequest request, @RequestBody Department dept) {
		return departmentService.addDept(dept);
	}
	
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	@ResponseBody
	public JsonResult updateDept(HttpServletRequest request, @RequestBody Department dept) {
		return departmentService.updateDept(dept, false);
	}
	
	@RequestMapping(value="/updateName", method=RequestMethod.POST)
	@ResponseBody
	public JsonResult updateDeptName(HttpServletRequest request, @RequestBody Department dept) {
		return departmentService.updateDept(dept, true);
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	@ResponseBody
	public JsonResult deleteDept(HttpServletRequest request, @RequestParam(required=true) String fd_id) {
		return departmentService.deleteDeptById(fd_id);
	}
	
	

}

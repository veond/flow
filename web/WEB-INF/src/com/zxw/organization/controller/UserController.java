/**
 * 用户信息
 */

package com.zxw.organization.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zxw.organization.model.User;
import com.zxw.organization.model.tree.UserAllotTree;
import com.zxw.organization.service.UserService;
import com.zxw.system.web.JsonResult;
import com.zxw.system.web.PageInfo;

@Controller
@RequestMapping("/organization/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public JsonResult addUser(@RequestBody User user) {
		return userService.addOrUpdateUser(user);
	}
	
	@RequestMapping("/getPageInfo")
	@ResponseBody
	public PageInfo getPageInfoByDept(@RequestParam(required=true) int start, 
			@RequestParam(required=true) int limit,
			@RequestParam(required=true) String deptId) {
		return userService.getPageInfoByDept(start, limit, deptId);
	}
	
	@RequestMapping("/getPageInfoByRole")
	@ResponseBody
	public PageInfo getPageInfoByRole(@RequestParam(required=true) int start, 
			@RequestParam(required=true) int limit,
			@RequestParam(required=true) String roleId) {
		return userService.getPageInfoByRole(start, limit, roleId);
	}
	
	@RequestMapping("/batDelete")
	@ResponseBody
	public JsonResult batDelete(@RequestBody List<String> userIds) {
		return userService.batDelete(userIds);
	}
	
	/**
	 * 移除用户角色
	 * @param userIds
	 * @return
	 */
	@RequestMapping("/removeRole")
	@ResponseBody
	public JsonResult removeRole(@RequestBody List<String> userIds) {
		return userService.removeRole(userIds);
	}
	
	/**
	 * 更新指定用户到指定的部门下
	 * @param userIds
	 * @param moveDeptId
	 * @return
	 */
	@RequestMapping("/updateUsersDept")
	@ResponseBody
	public JsonResult updateUsersDept(@RequestParam String userIds, @RequestParam String moveDeptId) {
		return userService.updateUsersDept(userIds, moveDeptId);
	}
	
	@RequestMapping("/getAllUser")
	@ResponseBody
	public List<User> getAllUser() {
		return userService.getAllUser();
	}
	
	@RequestMapping("/allotUserToRole")
	@ResponseBody
	public JsonResult allotUserToRole(@RequestParam String roleId, @RequestParam List<String> ids) {
		return userService.allotUserToRole(roleId, ids);
	}
	
	/**
	 * 取出用户树，包含 部门节点
	 * @param roleId 当前的角色ID
	 * @return
	 */
	@RequestMapping("/getUserTreeInDept")
	@ResponseBody
	public List<?> getUserTreeInDept(@RequestParam String roleId) {
		return userService.getUserTreeInDept(roleId);
	}
	
	

}

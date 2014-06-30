package com.zxw.organization.service;

import java.util.List;

import com.zxw.organization.model.User;
import com.zxw.organization.model.tree.UserAllotTree;
import com.zxw.system.web.JsonResult;
import com.zxw.system.web.PageInfo;

public interface UserService {

	public JsonResult addOrUpdateUser(User user);

	/**
	 * 根据 起始位 和 每页数量取得, 指定部门下用户的 pageInfo
	 * @param start
	 * @param limit
	 * @param deptId
	 * @return
	 */
	public PageInfo getPageInfoByDept(int start, int limit, String deptId);

	/**
	 * 指删除指定 用户
	 * @param userIds
	 * @return
	 */
	public JsonResult batDelete(List<String> userIds);

	/**
	 * 更新指定用户到指定的部门下
	 * @param userIds,   多个用户ID， 以 , 分隔
	 * @param moveDeptId
	 * @return
	 */
	public JsonResult updateUsersDept(String userIds, String deptId);

	/**
	 * 根据角色取用户列表
	 * @param start
	 * @param limit
	 * @param roleId
	 * @return
	 */
	public PageInfo getPageInfoByRole(int start, int limit, String roleId);

	public List<User> getAllUser();

	public JsonResult allotUserToRole(String roleId, List<String> userIds);

	/**
	 * 取出用户树，包含 部门节点
	 * @param roleId 当前的角色ID
	 * @return
	 */
	public List<?> getUserTreeInDept(String roleId);

	/**
	 * 移除用户角色
	 * @param userIds
	 * @return
	 */
	public JsonResult removeRole(List<String> userIds);

	public User getUserByNameAndPass(String username, String userpass);


}

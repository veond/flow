package com.zxw.organization.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxw.organization.mapper.DepartmentMapper;
import com.zxw.organization.mapper.UserMapper;
import com.zxw.organization.model.Department;
import com.zxw.organization.model.Role;
import com.zxw.organization.model.User;
import com.zxw.organization.model.tree.UserAllotTree;
import com.zxw.organization.service.DepartmentService;
import com.zxw.organization.service.RoleService;
import com.zxw.organization.service.UserService;
import com.zxw.system.constant.ConstantValue;
import com.zxw.system.extjs.CheckedTree;
import com.zxw.system.extjs.TreePanel;
import com.zxw.system.service.BaseService;
import com.zxw.system.web.JsonResult;
import com.zxw.system.web.PageInfo;
import com.zxw.util.MD5Util;
import com.zxw.util.StringUtil;

@Service
public class UserServiceImpl extends BaseService implements UserService {

	/**
	 * 获得mapper,
	 * @return
	 */
	private UserMapper getMapper() {
		return this.getMapper(ConstantValue.SQLSESSION_ORGANIZATION, UserMapper.class);
	}
	
	@Autowired
	private DepartmentService deptService = null;
	
	@Autowired
	private RoleService roleService = null;

	@Override
	public JsonResult addOrUpdateUser(User user) {
		JsonResult jr = new JsonResult();
		if(user == null || !StringUtil.isEntity(user.getLoginname()) || !StringUtil.isEntity(user.getUsername())) {
			jr.setMsg("user信息中存在空值");
			return jr;
		}
		//要保证 登录帐号是唯一的（loginname）, 根据loginName取出user, 再比较状态
		User tempUser = this.getMapper().getByLoginName(user.getLoginname());
		if(tempUser != null && !tempUser.getId().equals(user.getId())) {//登录不存在  或 状态为删除时才可以增加
			jr.setMsg("\""+user.getLoginname()+"\"此登录名已存在");
			return jr;
		}
		if(StringUtil.isEntity(user.getId())) {  //更新
			user.setUserpass(MD5Util.toMD5(user.getUserpass()));
			this.getMapper().update(user);						
		}else {
			user.setId(StringUtil.getUUID());
			if(!StringUtil.isEntity(user.getUserpass())) {
				user.setUserpass(ConstantValue.USER_DEFAULT_PASS);
			}
			user.setUserpass(MD5Util.toMD5(user.getUserpass()));
			this.getMapper().add(user);		
		}
		jr.setIsSuccess(true);
		jr.setMsg("用户操作成功");
		return jr;
	}

	/**
	 * 根据 起始位 和 每页数量取得, 指定部门下用户的 pageInfo
	 * @param start
	 * @param limit
	 * @param deptId
	 * @return
	 */
	@Override
	public PageInfo getPageInfoByDept(int start, int limit, String deptId) {
		PageInfo pageInfo = new PageInfo();
		if(limit<=0 || start<0 || start>limit || !StringUtil.isEntity(deptId)) {
			return pageInfo;
		}
		Department dept = deptService.getById(deptId);
		if(dept != null) {
			int count = this.getMapper().getCountByDept(deptId.trim());
			pageInfo.setTotalCount(count);
			if(count > 0 && count>start) {
				Map<String, Role> roleMap = new HashMap<String, Role>();
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("start", start);
				param.put("limit", limit);
				param.put("deptId", deptId.trim());
				List<User> users = this.getMapper().getListByDept(param);
				if(users != null && users.size()>0) {
					Iterator<User> it = users.iterator();
					while(it.hasNext()) {
						User temp = it.next();
						temp.setDeptName(dept.getName());
						//获得角色
						if(StringUtil.isEntity(temp.getRoleId())) {
							if(roleMap.get(temp.getRoleId()) == null) {
								roleMap.put(temp.getRoleId(), roleService.getById(temp.getRoleId()));							
							}
							if(roleMap.get(temp.getRoleId()) != null) {
								temp.setRoleName(roleMap.get(temp.getRoleId()).getName());							
							}
						}
					}
					pageInfo.setItems(users);
				}
			}
		}
		return pageInfo;
	}

	/**
	 * 批量删除
	 */
	@Override
	public JsonResult batDelete(List<String> userIds) {
		if(userIds != null && userIds.size()>0) {
			Iterator<String> it = userIds.iterator();
			while(it.hasNext()) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("userStatus", ConstantValue.USER_STATUS_DELETE);
				param.put("userId", it.next());
				this.getMapper().updateStatus(param);			
			}
		}
		return new JsonResult(true, "删除成功", null);
	}
	
	/**
	 * 批量删除用户角色
	 */
	@Override
	public JsonResult removeRole(List<String> userIds) {
		if(userIds != null && userIds.size()>0) {
			Iterator<String> it = userIds.iterator();
			while(it.hasNext()) {
				this.getMapper().removeRole(it.next());			
			}
		}
		return new JsonResult(true, "删除成功", null);
	}

	/**
	 * 更新指定用户到指定的部门下
	 * @param userIds,   多个用户ID， 以 , 分隔
	 * @param moveDeptId
	 * @return
	 */
	@Override
	public JsonResult updateUsersDept(String userIds, String deptId) {
		JsonResult jr = new JsonResult(false, "不能存在空值", null);
		if(StringUtil.isEntity(userIds) && StringUtil.isEntity(deptId)) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("userIds", userIds.trim());
			param.put("deptId", deptId.trim());
			this.getMapper().updateUsersDept(param);
			jr.setIsSuccess(true);
			jr.setMsg("移动成功");
		}
		return jr;
	}

	@Override
	public PageInfo getPageInfoByRole(int start, int limit, String roleId) {
		PageInfo pageInfo = new PageInfo();
		if(limit<=0 || start<0 || start>limit || !StringUtil.isEntity(roleId)) {
			return pageInfo;
		}
		Role role = roleService.getById(roleId);
		if(role != null) {
			int count = this.getMapper().getCountByRole(roleId.trim());
			pageInfo.setTotalCount(count);
			if(count > 0 && count>start) {
				Map<String, Department> deptMap = new HashMap<String, Department>();
				List<User> users = this.getMapper().getListByRole(start, limit, roleId);
				if(users != null && users.size()>0) {
					Iterator<User> it = users.iterator();
					while(it.hasNext()) {
						User temp = it.next();
						temp.setRoleName(role.getName());
						//获得角色
						if(StringUtil.isEntity(temp.getDeptId())) {
							if(deptMap.get(temp.getDeptId()) == null) {
								deptMap.put(temp.getDeptId(), deptService.getById(temp.getDeptId()));							
							}
							if(deptMap.get(temp.getDeptId()) != null) {
								temp.setDeptName(deptMap.get(temp.getDeptId()).getName());							
							}
						}
					}
					pageInfo.setItems(users);
				}
			}
		}
		return pageInfo;
	}

	@Override
	public List<User> getAllUser() {
		return this.getMapper().getAllUser();
	}

	@Override
	public JsonResult allotUserToRole(String roleId, List<String> userIds) {
		if(StringUtil.isEntity(roleId) && userIds != null && userIds.size()>0) {
			//先删除此角色下的所有用户，再重新增加进去
			this.getMapper().rollbackAllRole(roleId);
			for (String id : userIds) {
				this.getMapper().allotUserToRole(roleId, id);
			}			
		}
		return new JsonResult(true, "", "");
	}

	/**
	 * 取出用户树，包含 部门节点
	 * @param roleId 当前的角色ID
	 * @return
	 */
	@Override
	public List<?> getUserTreeInDept(String roleId) {
		DepartmentMapper deptMapper = this.getMapper(ConstantValue.SQLSESSION_ORGANIZATION, DepartmentMapper.class);
		List<Department> deptList = deptMapper.getByParentId(ConstantValue.TREE_ROOT_ID);
		return generUserAllotTree(deptList, roleId);
	}
	private List generUserAllotTree(List<Department> deptList, String roleId) {
		List treeList = new ArrayList();
		if(deptList != null && deptList.size() > 0) {
			for (Department dept : deptList) {
				TreePanel tempTree = new TreePanel();
				//根据父部门去取子部门
				List<Department> tempDepts = this.getMapper(ConstantValue.SQLSESSION_ORGANIZATION, DepartmentMapper.class).getByParentId(dept.getId());
				List<UserAllotTree> deptTree = null;
				if(tempDepts != null && tempDepts.size()>0) {
					deptTree = this.generUserAllotTree(tempDepts, roleId);
				}
				//根据部门去取用户
				List<UserAllotTree> tempList = this.getMapper().getUserByDeptNoRole(dept.getId(), roleId);
				if(tempList == null) {
					tempList = new ArrayList<UserAllotTree>();					
				}else {
					//设置用户的小图标样式，方便区分 及 已经选择过的 设置为选中状态
					for (UserAllotTree tempUser : tempList) {
						tempUser.setIcon(ConstantValue.ICON_USER_MALE);
						if(tempUser.getRoleId().equals(roleId)) {
							tempUser.setChecked(true);							
						}
					}
				}
				//如果此节点下面全是用户的话，则此部门上的复选框显示出来, 否则不显示 (并且这个节点要是部门节点)
				if((deptTree == null || deptTree.size()==0) && tempList.size()>0) {
					tempTree = new CheckedTree();					
				}
				if(deptTree != null) {
					tempList.addAll(deptTree);
				}
				//将 部门封装到 节点里面
				tempTree.setId(dept.getId());
				tempTree.setText(dept.getName());
				//如果下面有用户或是子部门的话 (判断是否是叶子节点)
				if(tempList.size() > 0) {
					tempTree.setLeaf(false);					
					tempTree.setChildren(tempList);
				}
				treeList.add(tempTree);
			}			
		}
		return treeList;		
	}

	@Override
	public User getUserByNameAndPass(String username, String userpass) {
		if(StringUtil.isEntity(username) && StringUtil.isEntity(userpass)) {
			return this.getMapper().getUserByNameAndPass(username, MD5Util.toMD5(userpass));
		}
		return null;
	}


}

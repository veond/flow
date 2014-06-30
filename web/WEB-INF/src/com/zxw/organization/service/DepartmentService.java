package com.zxw.organization.service;

import java.util.List;

import com.zxw.organization.model.Department;
import com.zxw.system.extjs.TreePanel;
import com.zxw.system.web.JsonResult;

/**
 * 部门service
 * @author 19lou-zxw
 *
 */
public interface DepartmentService {

	/**
	 * 获得所有
	 * @return
	 */
	List<Department> getAllDept();

	/**
	 * 增加部门
	 * @param dept
	 * @return
	 */
	JsonResult addDept(Department dept);

	/**
	 * 获得部门tree
	 * @return
	 */
	List<TreePanel> getDeptTree();

	/**
	 * 列新
	 * @param dept
	 * @param isUpateName  是否只更新名称
	 * @return
	 */
	JsonResult updateDept(Department dept, boolean isUpateName);

	JsonResult deleteDeptById(String fd_id);

	/**
	 * 根据ID取dept
	 * @param deptId
	 * @return
	 */
	Department getById(String deptId);

}

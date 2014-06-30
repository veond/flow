package com.zxw.organization.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.zxw.organization.mapper.DepartmentMapper;
import com.zxw.organization.model.Department;
import com.zxw.organization.service.DepartmentService;
import com.zxw.system.constant.ConstantValue;
import com.zxw.system.extjs.TreePanel;
import com.zxw.system.service.BaseService;
import com.zxw.system.web.JsonResult;
import com.zxw.util.StringUtil;

/**
 * 部门service
 * @author 19lou-zxw
 *
 */
@Service
public class DepartmentServiceImpl extends BaseService implements DepartmentService {
	
	/**
	 * 获得mapper,
	 * @return
	 */
	private DepartmentMapper getMapper() {
		return this.getMapper(ConstantValue.SQLSESSION_ORGANIZATION, DepartmentMapper.class);
	}

	/**
	 * 获得所有
	 * @return
	 */
	public List<Department> getAllDept() {
		List<Department> deptList = this.getMapper().getAll();
		if(deptList == null) {
			deptList = new ArrayList<Department>();
		}
		
		return deptList;
	}

	@Override
	public JsonResult addDept(Department dept) {
		JsonResult res = new JsonResult(false, "不能增加空值", null);
		if(dept != null && !"".equals(dept.getName().trim())) {
			if(this.getMapper().getByName(dept.getName()) == null) {
				dept.setId(StringUtil.getUUID());
				dept.setCreateAt(new Date());
				this.getMapper().add(dept);
				res.setIsSuccess(true);
				res.setMsg("增加成功");
			}else {
				res.setMsg("部门名称已经存在");
			}			
		}
		return res;
	}
	
	
	@Override
	public List<TreePanel> getDeptTree() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("parentId", ConstantValue.TREE_ROOT_ID);
		return this.createDeptTree(this.getMapper().getChildrens(param));
	}
	
	
	/**
	 * 递归出 tree 下面的所有子节点
	 * @param rootDepts
	 * @return
	 */
	private List<TreePanel> createDeptTree(List<Department> rootDepts) {
		List<TreePanel> childrenList = new ArrayList<TreePanel>();
		
		if(rootDepts != null && rootDepts.size()>0) {
			Map<String, Object> param = new HashMap<String, Object>();
			
			Iterator<Department> it = rootDepts.iterator();
			TreePanel tree = null;
			Department dept = null;
			List<Department> chDepts = null;
			while(it.hasNext()) {
				tree = new TreePanel();
				dept = it.next();						
				tree.setText(dept.getName());
				tree.setId(dept.getId());
				//找下面的子节点
				param.put("parentId", dept.getId());
				chDepts = this.getMapper().getChildrens(param);
				if(chDepts != null && chDepts.size()>0) {
					tree.setLeaf(false);
					tree.setChildren(createDeptTree(chDepts));
				}
				childrenList.add(tree);
			}			
		}
		
		return childrenList;
	}

	@Override
	public JsonResult updateDept(Department dept, boolean isUpateName) {
		JsonResult res = new JsonResult(false, "修改的数据存在空值", null);
		if(dept != null && !"".equals(dept.getName().trim()) && dept.getId()!=null && !dept.getId().trim().equals("")) {
			if(this.getMapper().getByName(dept.getName()) == null) {
				dept.setCreateAt(new Date());
				if(isUpateName) {
					this.getMapper().updateName(dept);
				}else {
					this.getMapper().update(dept);
				}
				res.setIsSuccess(true);
				res.setMsg("修改成功");
			}else {
				res.setMsg("部门名称已经存在");
			}			
		}
		return res;
	}

	@Override
	public JsonResult deleteDeptById(String fd_id) {
		JsonResult res = new JsonResult(false, "删除部门信息错误", fd_id);
		if(fd_id != null && !"".equals(fd_id)) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("parentId", fd_id);
			List<Department> childrens = this.getMapper().getChildrens(param);
			if(childrens!=null && childrens.size()>0) {
				res.setMsg("请先删除子部门！");
			}else {
				this.getMapper().deleteById(fd_id);
				res.setIsSuccess(true);			
				res.setMsg("删除部门成功");
			}
		}
		return res;
	}

	@Override
	public Department getById(String deptId) {
		if(deptId != null && !"".equals(deptId.trim())) {
			return this.getMapper().getById(deptId.trim());
		}
		return null;
	}

}

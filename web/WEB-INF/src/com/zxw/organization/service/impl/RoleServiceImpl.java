package com.zxw.organization.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.zxw.organization.mapper.RoleMapper;
import com.zxw.organization.model.Role;
import com.zxw.organization.service.RoleService;
import com.zxw.system.constant.ConstantValue;
import com.zxw.system.extjs.TreePanel;
import com.zxw.system.service.BaseService;
import com.zxw.system.web.JsonResult;
import com.zxw.system.web.PageInfo;
import com.zxw.util.StringUtil;

@Service
public class RoleServiceImpl extends BaseService implements RoleService {

	private RoleMapper getMapper(){
		return this.getMapper(ConstantValue.SQLSESSION_ORGANIZATION, RoleMapper.class);		
	}
	
	@Override
	public Role getById(String id) {
		if(id != null && !"".equals(id.trim())) {
			return this.getMapper().getById(id.trim());
		}
		return null;
	}

	@Override
	public JsonResult addOrUpdate(Role role) {
		JsonResult jr = new JsonResult(false, "信息有误", null);
		if(role == null || !StringUtil.isEntity(role.getName())) {
			jr.setMsg("不能存在空值");			
		}else {
			if(StringUtil.isEntity(role.getId())) {
				this.getMapper().update(role);				
				jr.setMsg("修改成功");
			}else {
				role.setId(StringUtil.getUUID());
				this.getMapper().add(role);
				jr.setMsg("增加成功");
			}
			jr.setIsSuccess(true);
		}		
		return jr;
	}

	@Override
	public PageInfo getPageInfo(int start, int limit) {
		PageInfo pageInfo = new PageInfo();
		if(limit<=0 || start<0 || start>limit) {
			return pageInfo;
		}
		int count = this.getMapper().getCount();
		pageInfo.setTotalCount(count);
		if(count > 0 && count>start) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("start", start);
			param.put("limit", limit);
			pageInfo.setItems(this.getMapper().getList(start, limit));
		}
		return pageInfo;
	}

	@Override
	public JsonResult batDelete(List<String> roleIds) {
		if(roleIds != null && roleIds.size()>0) {
			String ids = "";
			for(String id : roleIds) {
				ids += "'"+id+"',";
			}
			this.getMapper().batDelete(ids.substring(0, ids.length()-1));
		}
		return new JsonResult(true, "删除成功", null);
	}

	@Override
	public List<TreePanel> getTree() {
		return this.getMapper().getTree();
	}
	

}

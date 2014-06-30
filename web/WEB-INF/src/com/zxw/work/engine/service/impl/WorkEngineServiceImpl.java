package com.zxw.work.engine.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.zxw.organization.model.User;
import com.zxw.system.constant.ConstantValue;
import com.zxw.system.extjs.SelectedOption;
import com.zxw.system.extjs.TreePanel;
import com.zxw.system.service.BaseService;
import com.zxw.system.web.JsonResult;
import com.zxw.system.web.PageInfo;
import com.zxw.util.ConditionScriptParse;
import com.zxw.util.StringUtil;
import com.zxw.work.constant.WorkValue;
import com.zxw.work.engine.exception.WorkItemException;
import com.zxw.work.engine.mapper.WorkEngineMapper;
import com.zxw.work.engine.model.PostpositionItem;
import com.zxw.work.engine.model.PreposeItem;
import com.zxw.work.engine.model.ProcessInstance;
import com.zxw.work.engine.model.WorkForm;
import com.zxw.work.engine.model.WorkItem;
import com.zxw.work.engine.model.WorkTodo;
import com.zxw.work.engine.service.WorkEngineService;

@Service
public class WorkEngineServiceImpl extends BaseService implements WorkEngineService {
	
	public WorkEngineMapper getMapper() {
		return super.getMapper(ConstantValue.SQLSESSION_WORK, WorkEngineMapper.class);
	}

	@Override
	public List<SelectedOption> getAllWorkModel_selected() {
		return this.getMapper().getAllWorkModel_selected();
	}

	/**
	 * 获得所有的工作模型 列表
	 * @return
	 */
	@Override
	public List<TreePanel> getModelTree() {
		List<TreePanel> modelTree = this.getMapper().getModelTree();
		//加载 此模型下面的所有项
		if(modelTree != null && modelTree.size() > 0) {
			for(TreePanel tree : modelTree) {
				tree.setLeaf(false);
				tree.setChildren(this.getMapper().getItemsByModelId_tree(tree.getId()));
			}
		}
		return modelTree;
	}
	
	/**
	 * 获得工作项的下拉选择框的数据
	 * @return
	 */
	@Override
	public List<SelectedOption> getAllWorkItem_selected() {
		return this.getMapper().getAllWorkItem_selected();
	}

	@Override
	public JsonResult addOrUpdateWorkItem(WorkItem workItem) {
		//先增加 work item
		if(workItem != null) {
			if(!StringUtil.isEntity(workItem.getId())) {  //增加
				workItem.setId(StringUtil.getUUID());
				this.getMapper().addWorkItem(workItem);
			}else {
				this.getMapper().updateWorkItem(workItem);
			}
			//现在因为此功能为了方便增加数据用的，所以不管理增加 还是 更新 前后节点都会新加的
			if(workItem.getPreposeList_() != null && workItem.getPreposeList_().size() > 0) {
				for(PreposeItem preposeItem : workItem.getPreposeList_()) {
					if(StringUtil.isEntity(preposeItem.getPreposeId())) {
						preposeItem.setId(StringUtil.getUUID());
						preposeItem.setItemId(workItem.getId());
						this.getMapper().addPreposeItem(preposeItem);
					}
				}				
			}
			if(workItem.getPostpositionList_() != null && workItem.getPostpositionList_().size() > 0) {
				for(PostpositionItem postpositionItem : workItem.getPostpositionList_()) {
					if(StringUtil.isEntity(postpositionItem.getPostpositionId())) {
						postpositionItem.setId(StringUtil.getUUID());
						postpositionItem.setItemId(workItem.getId());
						this.getMapper().addPostpositionItem(postpositionItem);
					}
				}
				
			}
			return new JsonResult(true, "成功");
		}
		return new JsonResult("参数不能为空");
	}


	/**
	 * 启动指定模型的工作流程 （流程实例里面的当前节点应该定位到 “开始”的下一个节点）
	 * @param modelId
	 * @param user 流程的用户信息
	 * @param modelName 流程名称
	 * @return
	 */
	@Override
	public ProcessInstance startWork(String modelId, User user, String modelName) {
		if(!StringUtil.isEntity(modelId)) {
			return null;			
		}
		//启动节点项
		WorkItem startItem = this.getMapper().getStartNode(modelId);
		if(startItem == null) {
			return null;
		}
		//创建工作流实例
		ProcessInstance pi = new ProcessInstance();
		//设置所在节点
		WorkItem nextItem = this.nextWorkItem(startItem, null); 
		pi.setWorKItemId(nextItem.getId());
		pi.setWorkModelId(modelId);
		this.getMapper().addProcessInstance(pi);
		//创建 待办工作流程
		WorkTodo todo = new WorkTodo();
		todo.setPartakeUser(user.getId());
		todo.setProcessInstance(pi.getId());
		todo.setWorkItemId(nextItem.getId());
		todo.setWorkItemName(modelName + "{" + nextItem.getName() + "}");
		todo.setWorkModelId(modelId);
		this.getMapper().addWorkTodo(todo);
		return pi;
	}

	/**
	 * 根据当前工作项, 获得下一个工作项 (此方法后期可能会重载出返回多个子节点的方法)
	 * @param thisWorkItem
	 * @return
	 */
	@Override
	public WorkItem nextWorkItem(WorkItem thisWorkItem, Map<String, Object> conditionParam) {
		if(thisWorkItem != null && StringUtil.isEntity(thisWorkItem.getId())) {
			//取得后置节点
			List<WorkItem> postItems = this.getMapper().getPostpositionItem(thisWorkItem.getId());
			//获得特定的子节点
			if(postItems != null && postItems.size()>0) {
				String parentId = postItems.get(0).getId();
				if(postItems.size() == 1) {  //如果只有一个节点
					//如果是连接线, 则取下一个节点， 如果一条连接下存在多个节点，说明数据异常，直接抛出异常
					if(postItems.get(0).getItemType() == WorkValue.WORK_ITEM_TYPE_LINK) {
						return this.nextWorkItem(postItems.get(0), conditionParam);
					}
					return postItems.get(0);
				}else {  //如果有多个节点，根据条件 循环找出子节点
					Iterator<WorkItem> it = postItems.iterator();
					WorkItem tempItem = null;
					while(it.hasNext()) {
						tempItem = it.next();
						if(tempItem.getItemType() == WorkValue.WORK_ITEM_TYPE_LINK) {
							if(ConditionScriptParse.eavl(tempItem.getItemCondition(), conditionParam)) {
								return this.nextWorkItem(tempItem, conditionParam);
							}
						}else {
							throw new WorkItemException("子节点数据异常，多个子节点时此子节点类型必须为连接线，因为这样才能判断出具体哪个子节点！“public WorkItem nextWorkItem()” -- （父ID："+parentId+"）  -- （当前节点ID："+tempItem.getId()+"    type："+tempItem.getItemType()+"） -- （子节点个数："+postItems.size()+"）");
						}
					}
				} 
			}			
		}
		return null;
	}

	/**
	 * 分页,根据用户ID获得此用户下所有的代办信息
	 * @param userid
	 * @param start
	 * @param limit
	 * @return
	 */
	@Override
	public PageInfo todoWorkPageInfoByUser(String userid, int start, int limit) {
		PageInfo pageInfo = new PageInfo();
		if(!StringUtil.isEntity(userid) || limit<=0 || start<0 || start>limit) {
			return pageInfo;						
		}	
		pageInfo.setTotalCount(this.getMapper().countTodoWorkPageInfoByUser(userid));
		if(pageInfo.getTotalCount()>0 && pageInfo.getTotalCount()>start) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("start", start);
			param.put("limit", limit);
			param.put("partakeUser", userid);
			pageInfo.setItems(this.getMapper().todoWorkPageInfoByUser(param));				
		}
		return pageInfo;
	}

	
	/**
	 * 根据item id 获得工作项对应的 表单 URL地址
	 * @param item
	 * @return
	 */
	@Override
	public String getItemForm(String item) {
		WorkItem wi = this.getMapper().getItemById(item);
		if (wi != null) {
			WorkForm wf = this.getMapper().getFormById(wi.getWorkFormId());
			// 前缀
			String urlPrefix = "";
			switch (wf.getFormType()) {
				case WorkValue.WORK_FORM_FORWARD:
					urlPrefix = "forward:";
					break;
				case WorkValue.WORK_FORM_REDIRECT:
					urlPrefix = "redirect:";
					break;
				default:
					break;
			}
			return wf != null ? urlPrefix + wf.getUrl() : null;
		}
		return null;
	}

}

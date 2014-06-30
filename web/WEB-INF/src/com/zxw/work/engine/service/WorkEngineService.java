package com.zxw.work.engine.service;

import java.util.List;
import java.util.Map;

import com.zxw.organization.model.User;
import com.zxw.system.extjs.SelectedOption;
import com.zxw.system.extjs.TreePanel;
import com.zxw.system.web.JsonResult;
import com.zxw.system.web.PageInfo;
import com.zxw.work.engine.model.ProcessInstance;
import com.zxw.work.engine.model.WorkItem;

public interface WorkEngineService {

	/**
	 * 获得工作模型的下拉选择框的数据
	 * @return
	 */
	List<SelectedOption> getAllWorkModel_selected();

	/**
	 * 获得所有的工作模型 列表
	 * @return
	 */
	List<TreePanel> getModelTree();

	/**
	 * 获得工作项的下拉选择框的数据
	 * @return
	 */
	List<SelectedOption> getAllWorkItem_selected();

	/**
	 * 增加 或更新工作项
	 * @return
	 */
	JsonResult addOrUpdateWorkItem(WorkItem workItem);

	/**
	 * 启动指定模型的工作流程 （流程实例里面的当前节点应该定位到 “开始”的下一个节点）
	 * @param modelId
	 * @param user 流程的用户信息
	 * @param modelName 流程名称
	 * @return
	 */
	ProcessInstance startWork(String modelId, User user, String modelName);
	
	/**
	 * 根据当前工作项, 获得下一个工作项
	 * @param thisWorkItem
	 * @param conditionParam 如果没有，可以为null
	 * @return
	 */
	WorkItem nextWorkItem(WorkItem thisWorkItem, Map<String, Object> conditionParam);

	
	/**
	 * 分页,根据用户ID获得此用户下所有的代办信息
	 * @param userid
	 * @param start
	 * @param limit
	 * @return
	 */
	PageInfo todoWorkPageInfoByUser(String userid, int start, int limit);

	/**
	 * 根据item id 获得工作项对应的 表单 URL地址
	 * @param item
	 * @return
	 */
	String getItemForm(String item);


}

package com.zxw.work.engine.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

import com.zxw.system.extjs.SelectedOption;
import com.zxw.system.extjs.TreePanel;
import com.zxw.work.constant.WorkValue;
import com.zxw.work.engine.mapper.sql.provider.ProcessInstanceSQLProvider;
import com.zxw.work.engine.mapper.sql.provider.WorkFormSQLProvider;
import com.zxw.work.engine.mapper.sql.provider.WorkItemSQLProvider;
import com.zxw.work.engine.mapper.sql.provider.WorkTodoSQLProvider;
import com.zxw.work.engine.model.PostpositionItem;
import com.zxw.work.engine.model.PreposeItem;
import com.zxw.work.engine.model.ProcessInstance;
import com.zxw.work.engine.model.WorkForm;
import com.zxw.work.engine.model.WorkItem;
import com.zxw.work.engine.model.WorkTodo;

public interface WorkEngineMapper {

	/**
	 * 获得工作模型的下拉选择框的数据
	 * 
	 * @return
	 */
	@Select("SELECT id,name FROM work_model")
	@Results(value = {
			@Result(id=true, property="optionValue", column = "id", javaType=String.class, jdbcType=JdbcType.VARCHAR),
			@Result(property="optionName", column="name", javaType=String.class, jdbcType=JdbcType.VARCHAR) })
	List<SelectedOption> getAllWorkModel_selected();

	/**
	 * 获得所有的工作模型 列表
	 * @return
	 */
	@Select("SELECT id,name FROM work_model")
	@Results(value = {
			@Result(id=true, property="id", column = "id", javaType=String.class, jdbcType=JdbcType.VARCHAR),
			@Result(property="text", column="name", javaType=String.class, jdbcType=JdbcType.VARCHAR) })
	List<TreePanel> getModelTree();

	/**
	 * 获得工作项的下拉选择框的数据
	 * @return
	 */
	@Select("SELECT id,name FROM work_item")
	@Results(value = {
			@Result(id=true, property="optionValue", column = "id", javaType=String.class, jdbcType=JdbcType.VARCHAR),
			@Result(property="optionName", column="name", javaType=String.class, jdbcType=JdbcType.VARCHAR) })
	List<SelectedOption> getAllWorkItem_selected();

	/**
	 * 增加工作项
	 * @param workItem
	 */
	@Insert("INSERT INTO work_item SET id=#{id}, name=#{name}, work_model_id=#{workModelId}, item_type=#{itemType}, item_condition=#{itemCondition}, partake_user=#{partakeUser}, create_at=#{createAt}, remark=#{remark}")
	void addWorkItem(WorkItem workItem);

	/**
	 * 更新工作项
	 * @param workItem
	 */
	@Update("UPDATE work_item SET name=#{name}, work_model_id=#{workModelId}, item_type=#{itemType}, item_condition=#{itemCondition}, partake_user=#{partakeUser}, remark=#{remark} WHERE id=#{id}")
	void updateWorkItem(WorkItem workItem);
	
	/**
	 * 增加工作流前置节点
	 * @param preposeItem
	 */
	@Insert("INSERT INTO prepose_item SET id=#{id}, item_id=#{itemId}, prepose_id=#{preposeId}, create_at=#{createAt}, remark=#{remark}")
	void addPreposeItem(PreposeItem preposeItem);

	/**
	 * 增加工作流 后置节点
	 * @param postpositionItem
	 */
	@Insert("INSERT INTO postposition_item SET id=#{id}, item_id=#{itemId}, postposition_id=#{postpositionId}, create_at=#{createAt}, remark=#{remark}")
	void addPostpositionItem(PostpositionItem postpositionItem);

	/**
	 * 根据 工作模型ID， 获得此模型下的所有节点对应的 tree
	 * @param id
	 * @return
	 */
	@Select("SELECT id,name FROM work_item WHERE work_model_id=#{modelId} ORDER BY create_at")
	@Results(value = {
			@Result(id=true, property="id", column = "id", javaType=String.class, jdbcType=JdbcType.VARCHAR),
			@Result(property="text", column="name", javaType=String.class, jdbcType=JdbcType.VARCHAR) })
	List<TreePanel> getItemsByModelId_tree(String modelId);

	/**
	 * 取指定工作类型的开始节点
	 * @param modelId
	 * @return
	 */
	@Select("SELECT * FROM work_item WHERE work_model_id=#{modelId} AND item_type="+WorkValue.WORK_ITEM_TYPE_START)
	@ResultMap("work.ItemMapper")
	WorkItem getStartNode(String modelId);

	/**
	 * 增加工作流程实例
	 * @param pi
	 * @return
	 */
	@InsertProvider(type=ProcessInstanceSQLProvider.class, method="insert")
	void addProcessInstance(ProcessInstance pi);

	/**
	 * 根据ID，获得此节点下面所有的子节点
	 * @param itemId
	 * @return
	 */
	@Select("SELECT * FROM work_item WHERE id IN(SELECT postposition_id FROM postposition_item WHERE item_id=#{itemId})")
	@ResultMap("work.ItemMapper")
	List<WorkItem> getPostpositionItem(String itemId);

	/**
	 * 插入 待办流程
	 * @param todo
	 */
	@InsertProvider(type=WorkTodoSQLProvider.class, method="insert")
	void addWorkTodo(WorkTodo todo);

	/**
	 * 分页,根据用户ID获得此用户下所有的代办信息 总数
	 * @param userid
	 * @return
	 */
	@SelectProvider(type=WorkTodoSQLProvider.class, method="countTodoWorkPageInfoByUser")
	int countTodoWorkPageInfoByUser(String userid);

	/**
	 * 分页,根据用户ID获得此用户下所有的代办信息
	 * @param userid
	 * @param start
	 * @param limit
	 * @return
	 */
	@SelectProvider(type=WorkTodoSQLProvider.class, method="todoWorkPageInfoByUser")
	List<WorkTodo> todoWorkPageInfoByUser(Map<String, Object> param);

	/**
	 * 根据ID获取 
	 * @param item
	 * @return
	 */
	@SelectProvider(type=WorkItemSQLProvider.class, method="selectById")
	WorkItem getItemById(String id);

	/**
	 * 根据表单ID，获得表单
	 * @param workFormId
	 * @return
	 */
	@SelectProvider(type=WorkFormSQLProvider.class, method="selectById")
	WorkForm getFormById(String workFormId);
	
	

}









package com.zxw.work.engine.mapper.sql.provider; 

import java.util.Date;
import java.util.Map;

import com.zxw.system.sql.entity.ConditionEnum;
import com.zxw.system.sql.entity.ConnectionEnum;
import com.zxw.system.sql.entity.WhereRule;
import com.zxw.system.sql.entity.WhereRuleList;
import com.zxw.system.sql.reverse.SQLReverse;
import com.zxw.util.StringUtil;
import com.zxw.work.engine.model.WorkTodo;

/** 
 * 待办流程的SQL　provider
 * @author 作者: zxw    
 *		E-mail: veond@163.com
 * @since 创建时间：2014-3-11 下午5:59:39 
 * 
 */

public class WorkTodoSQLProvider extends SQLReverse {
	
	public WorkTodoSQLProvider() {
		super("work_todo", WorkTodo.class);
	}

	/**
	 * 增加
	 * @param todo
	 * @return
	 */
	public String insert(WorkTodo todo) {
		todo.setId(StringUtil.getUUID());
		todo.setCreateAt(new Date());
		return super.insertSQL();
	}
	
	/**
	 * 分页,根据用户ID获得此用户下所有的代办信息 总数
	 * @param userid
	 * @return
	 */
	public String countTodoWorkPageInfoByUser(String userid) {
		WhereRuleList whereRules = new WhereRuleList();
		whereRules.add(new WhereRule("partake_user", ConditionEnum.EQUAL, "#{userid}", null));
		return super.selectCountSQL(whereRules);
	}
	
	/**
	 * 分页,根据用户ID获得此用户下所有的代办信息
	 * @param userid
	 * @param start
	 * @param limit
	 * @return
	 */
	public String todoWorkPageInfoByUser(Map<String, Object> param) {
		WhereRuleList whereRules = new WhereRuleList();
		whereRules.add(new WhereRule("partake_user", ConditionEnum.EQUAL, null, ConnectionEnum.NULL));
		return super.selectPageInfoSQL(whereRules, new Integer(param.get("start").toString()), new Integer(param.get("limit").toString()));
	}
	

}



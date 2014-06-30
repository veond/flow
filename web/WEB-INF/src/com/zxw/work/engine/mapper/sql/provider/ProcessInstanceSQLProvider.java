/**
 * 工作流实例的 sql  provider
 */

package com.zxw.work.engine.mapper.sql.provider;

import java.util.Date;
import java.util.LinkedHashMap;

import com.zxw.system.sql.entity.ConditionEnum;
import com.zxw.system.sql.entity.ConnectionEnum;
import com.zxw.system.sql.entity.WhereRule;
import com.zxw.system.sql.entity.WhereRuleList;
import com.zxw.system.sql.reverse.SQLReverse;
import com.zxw.util.StringUtil;
import com.zxw.work.engine.model.ProcessInstance;

public class ProcessInstanceSQLProvider {
	
	private static SQLReverse sqlReverse = null;

	static {
		System.out.println("-----------ProcessInstanceSQLProvider-------------");
		sqlReverse = new SQLReverse("process_instance", ProcessInstance.class);
	}

	/**
	 * 增加
	 * @param pi
	 * @return
	 */
	public String insert(ProcessInstance pi) {
		pi.setId(StringUtil.getUUID());
		pi.setCreateAt(new Date());
		return sqlReverse.insertSQL();
	}

	/**	
	 * 更新
	 * @param pi
	 * @return
	 */
	public String updateById(ProcessInstance pi) {
		WhereRuleList whereRules = new WhereRuleList();
		whereRules.add(new WhereRule("id", ConditionEnum.EQUAL, null, ConnectionEnum.NULL));
		return sqlReverse.updateSQL(whereRules, null);
	}


	

}

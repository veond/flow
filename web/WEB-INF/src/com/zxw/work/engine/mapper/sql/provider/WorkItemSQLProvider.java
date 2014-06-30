package com.zxw.work.engine.mapper.sql.provider; 

import com.zxw.system.sql.reverse.SQLReverse;
import com.zxw.work.engine.model.WorkItem;

/** 
 *
 * @author 作者: zxw    
 *		E-mail: veond@163.com
 * @since 创建时间：2014-4-16 下午5:32:10 
 * 
 */

public class WorkItemSQLProvider extends SQLReverse {

	public WorkItemSQLProvider() {
		super("work_item", WorkItem.class);
	}
	
	


}



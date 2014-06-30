package com.zxw.work.engine.mapper.sql.provider; 

import com.zxw.system.sql.reverse.SQLReverse;
import com.zxw.work.engine.model.WorkForm;

/** 
 *
 * @author 作者: zxw    
 *		E-mail: veond@163.com
 * @since 创建时间：2014-4-16 下午6:08:32 
 * 
 */

public class WorkFormSQLProvider extends SQLReverse {

	public WorkFormSQLProvider() {
		super("work_form", WorkForm.class);
	}

}



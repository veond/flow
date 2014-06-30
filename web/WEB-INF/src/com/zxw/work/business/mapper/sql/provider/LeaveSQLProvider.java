package com.zxw.work.business.mapper.sql.provider; 

import java.util.ArrayList;
import java.util.List;

import com.zxw.system.sql.entity.WhereRuleList;
import com.zxw.system.sql.reverse.SQLReverse;
import com.zxw.util.StringUtil;
import com.zxw.work.business.model.Leave;

/** 
 * 请假流程的SQL
 * @author 作者: zxw    
 *		E-mail: veond@163.com
 * @since 创建时间：2014-3-7 下午1:12:53 
 * 
 */

public class LeaveSQLProvider extends SQLReverse{
	
	public LeaveSQLProvider() {
		super("work_leave", Leave.class);
	}


	/**
	 * 增加
	 * @param pi
	 * @return
	 */
	public String insert(Leave leave) {
		leave.setId(StringUtil.getUUID());
		return super.insertSQL();
	}

	/**
	 * 更新请假信息   (只更新：msg, start_date, end_date, leave_day, remark)
	 * @param leave
	 * @return 
	 */
	public String update(Leave leave) {
		List<String> noUpdateColumns = new ArrayList<String>();
		noUpdateColumns.add("msg");
		noUpdateColumns.add("start_date");
		noUpdateColumns.add("end_date");
		noUpdateColumns.add("leave_day");
		noUpdateColumns.add("remark");
		String str =  super.updateSQL(null, noUpdateColumns, true);
		System.out.println(str);
		return str;
	}
		

}



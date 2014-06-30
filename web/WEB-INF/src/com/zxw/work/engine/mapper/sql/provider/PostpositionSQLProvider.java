package com.zxw.work.engine.mapper.sql.provider; 

import com.zxw.system.sql.reverse.SQLReverse;
import com.zxw.work.engine.model.PostpositionItem;

/** 
 * 后置节点 SQL语句
 * @author 作者: zxw    
 *		E-mail: veond@163.com
 * @since 创建时间：2014-3-11 上午11:08:35 
 * 
 */

public class PostpositionSQLProvider {
	
	private static SQLReverse sqlReverse = new SQLReverse("postposition_item", PostpositionItem.class);

	/**
	 * 根据ID，获得此节点下面所有的子节点对应的 workitem
	 * @param itemId
	 * @return
	 */
	public String getPostpositionItem() {
		return "SELECT * FROM work_item WHERE id IN(SELECT postposition_id FROM postposition_item WHERE item_id=#{itemId})";
	}

}



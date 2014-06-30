package com.zxw.system.sql.entity; 

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/** 
 * SQL语句中连接条件的信息的集合  (此集合主要提供一个查询指定column 是否存在的方法)
 * @author 作者: zxw    
 *		E-mail: veond@163.com
 * @since 创建时间：2014-3-14 上午10:12:24 
 * 
 */

public class WhereRuleList implements Serializable{

	private static final long serialVersionUID = 4864750940308727777L;
	
	/**
	 * 内容区
	 */
	private List<WhereRule> whereRules = new ArrayList<WhereRule>();
	
	/**
	 * 增加
	 * @param whereRule
	 * @return
	 */
	public boolean add(WhereRule whereRule) {
		return whereRules.add(whereRule);		
	}
	
	/**
	 * 上对象是否存在
	 * @param obj
	 * @return
	 */
	public boolean contains(Object obj) {
		return indexOf(obj) >= 0;		
	}

	/**
	 * 获得此对象首个存在的位置
	 * @param obj
	 * @return
	 */
	public int indexOf(Object obj) {
		if(obj instanceof WhereRule) {
			String temp = ((WhereRule) obj).getColumn();
			for(int i=0; i<whereRules.size(); i++) {
				if(temp.equals(whereRules.get(i).getColumn())) {
					return i;
				}
			}						
		}else if(obj instanceof String) {
			for(int i=0; i<whereRules.size(); i++) {
				if(obj.toString().equals(whereRules.get(i).getColumn())) {
					return i;
				}
			}			
		}else {
			return whereRules.indexOf(obj);
		}
		return -1;
	}

	/**
	 * 数量
	 * @return
	 */
	public int size() {
		return whereRules.size();
	}

	/**
	 * 遍历
	 * @return
	 */
	public Iterator<WhereRule> iterator() {
		return whereRules.iterator();
	}
	
	

}



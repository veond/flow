/**
 * 简单的SQL语句反转
 * 
 * 1、效率上可能有点低， 因为每次都要查数据库才行，期待后期优化
 */

package com.zxw.system.sql.reverse;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.zxw.system.exception.ConditionsIsNullException;
import com.zxw.system.sql.entity.ConditionEnum;
import com.zxw.system.sql.entity.ConnectionEnum;
import com.zxw.system.sql.entity.WhereRule;
import com.zxw.system.sql.entity.WhereRuleList;
import com.zxw.system.sql.exception.SQLColumnException;
import com.zxw.system.sql.exception.SQLReverseInitException;
import com.zxw.util.StringUtil;

public class SQLReverse {
	
	/**
	 * 表名
	 */
	private String tableName = "";
	
	/**
	 * 数据库字段的list
	 */
	private List<String> columnList = null;
	
	/**
	 * 转换之后的model里面的 变量名称list 
	 */
	private List<String> modelVarList = null;
	
	/** 初始化后的两个list 提供出去， 方便其它地方外面使用 **/
	public List<String> getColumnList() {
		return columnList;
	}

	public List<String> getModelVarList() {
		return modelVarList;
	}
	
	/**
	 * 当类型为class时，递归为两个list 设置值 (不是以 _ 结尾的字段才会去反转成表字段)   (updateAtStr 和 createAtStr 因为是之前写的，所以保留吧 )
	 * @param tableName
	 */ 
	private void recursionInitList(Class<?> modelCls) {
		if(!modelCls.getName().equals(Object.class.getName())) {
			Field[] fields = modelCls.getDeclaredFields();
			for(Field f : fields) {
				if(!f.getName().endsWith("_") && !"serialVersionUID".equals(f.getName()) && !"updateAtStr".equals(f.getName()) && !"createAtStr".equals(f.getName())) {
					modelVarList.add(f.getName());							
					columnList.add(StringUtil.varNameToColumn(f.getName()));
				}
			}
			this.recursionInitList(modelCls.getSuperclass());
		}
	}

	/**
	 * 验证数据是否初始化
	 * @return
	 */
	public boolean validateData() {
		if(tableName== null || "".equals(tableName) || columnList == null || modelVarList == null || columnList.size()<=0 || modelVarList.size() <= 0 || modelVarList.size()!=columnList.size()) {
			throw new SQLReverseInitException("sql reverse 表字段 或 model变量列表，初始化异常！ （tableName："+tableName+"）");
		}
		return true;
	}


	/**
	 * 直接将字段名列表传进来的 （为防止 columnList 和 modelVarList 不一至，所以将此方法停止使用，请使用 SQLReverse(String tableName, List<String> list, boolean isColumnList) --- 2014-4-25）
	 * @param tableName
	 * @param columnList
	 * @param modelVarList
	 */
	@Deprecated 
	public SQLReverse(String tableName, List<String> columnList,
			List<String> modelVarList) {
		super();
		this.tableName = tableName;
		this.columnList = columnList;
		this.modelVarList = modelVarList;
	}
	
	/**
	 * 根据 变量转成字段，或是根据 字段转变量
	 * @param tableName
	 * @param list
	 * @param isColumnList  是否是字段列表 
	 */
	public SQLReverse(String tableName, List<String> list, boolean isColumnList) {
		super();
		this.tableName = tableName;
		if(isColumnList) {
			this.columnList = list;
			//根据字段列表，反转出
			if(list!=null && list.size()>0) {
				this.modelVarList = new LinkedList<String>();
				for(String column : list) {
					modelVarList.add(StringUtil.columnToVarName(column));
				}			
			}
		}else {
			this.modelVarList = list;
			//根据字段列表，反转出
			if(list!=null && list.size()>0) {
				this.columnList = new LinkedList<String>();
				for(String modelVar : list) {
					columnList.add(StringUtil.varNameToColumn(modelVar));
				}			
			}
		}
	}
	
	/**
	 * 根据 model 的 class 类，通过反射生成两个list
	 * @param tableName
	 * @param modelCls 注意：类里面的 字段名以 _ 结尾的不会被反转
	 */
	public SQLReverse(String tableName, Class<?> modelCls) {
		super();
		this.tableName = tableName;
		modelVarList = new LinkedList<String>();
		columnList = new LinkedList<String>();
		this.recursionInitList(modelCls);
	}
	/**
	 * 根据数据库连接生成两个list的值得
	 * @param connection
	 * @param tableName
	 */
	public SQLReverse(String tableName, Connection connection) {
		this.tableName = tableName;
		columnList = new LinkedList<String>();
		modelVarList = new LinkedList<String>();
		String querySql = "SELECT * FROM "+tableName+" LIMIT 0,0";
		try {
			ResultSetMetaData rsmd = connection.createStatement().executeQuery(querySql).getMetaData();
			for(int i=1; i<=rsmd.getColumnCount(); i++) {
				columnList.add(rsmd.getColumnName(i));
				modelVarList.add(StringUtil.columnToVarName(rsmd.getColumnName(i)));
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 拼接where块的内容 
	 * @param whereRules
	 * @return
	 */
	public String whereSplitJointSQL(WhereRuleList whereRules) {
		if(whereRules==null || whereRules.size()<=0) {
			return "";			
		}
		StringBuffer sb = new StringBuffer(" WHERE");
		Iterator<WhereRule> it = whereRules.iterator();
		WhereRule temp = null;
		while(it.hasNext()) {
			temp = it.next();
			sb.append(" " + temp.getColumn()+temp.getCondition());  //字段
			if(temp.getValue() == null) {
				temp.setValue("#{"+StringUtil.columnToVarName(temp.getColumn())+"}");
			}
			sb.append((temp.getCondition()==ConditionEnum.IN || temp.getCondition()==ConditionEnum.NOT_IN) ? "("+temp.getValue()+")" : temp.getValue());  //值
			sb.append((temp.getConnect() == null) ? "" : temp.getConnect()); //连接
		}
		return sb.toString();		
	}

	/**
	 * 增加的SQL
	 */
	public String insertSQL() {
		StringBuffer sql = new StringBuffer();
		if(validateData()) {
			sql.append("INSERT INTO "+tableName+" SET ");
			for(int i=0; i<columnList.size(); i++) {
				sql.append(columnList.get(i)+"=#{"+modelVarList.get(i)+"}");
				if((i+1) < columnList.size()) {
					sql.append(", ");					
				}
			}			
		}		
		return sql.toString();
	}

	/**
	 * 根据指定字段的条件进行更新操作 
	 * @param whereRules  （where 块的值，如果此值为null 或是 0 ， 则：就是 where id = #{id}）
	 * @param noUpdateColumns  不需要更新的字段 (条件判断不包含在这里面)
	 * @return
	 */
	public String updateSQL(WhereRuleList whereRules, List<String> noUpdateColumns) {
		if(whereRules == null || whereRules.size()<=0) {
			whereRules = new WhereRuleList();
			whereRules.add(new WhereRule("id", ConditionEnum.EQUAL, null, null));
		}
		StringBuffer sql = new StringBuffer();
		boolean isUpateColumn = false;  //是否有要更新的字段值
		if(validateData()) {
			sql.append("UPDATE "+tableName+" SET ");
			for(int i=0; i<columnList.size(); i++) {
				if((noUpdateColumns == null || !noUpdateColumns.contains(columnList.get(i))) && !whereRules.contains(columnList.get(i))) {
					isUpateColumn = true;
					sql.append(columnList.get(i)+"=#{"+modelVarList.get(i)+"}, ");
				}
			}
			if(isUpateColumn) {
				sql = new StringBuffer(sql.substring(0, (sql.length()-2)));
				sql.append(this.whereSplitJointSQL(whereRules));
			}else {
				throw new SQLColumnException("没有要更新的SQL字段.....");
			}
		}		
		return sql.toString();
	} 
	
	/**
	 * 根据指定字段的条件进行更新操作 
	 * @param whereRules  （where 块的值，如果此值为null 或是 0 ， 则：就是 where id = #{id}）
	 * @param columns  字段列表
	 * @param isRequire  指定 columns 是否需要还是不需要的
	 * @return
	 */
	public String updateSQL(WhereRuleList whereRules, List<String> columns, boolean isRequire) {
		if(!isRequire) {
			return updateSQL(whereRules, columns);
		}
		if(columns==null || columns.size()==0) {
			throw new SQLColumnException("没有要更新的字段....");			
		}
		if(whereRules == null || whereRules.size()<=0) {
			whereRules = new WhereRuleList();
			whereRules.add(new WhereRule("id", ConditionEnum.EQUAL, null, null));
		}
		StringBuffer sql = new StringBuffer();
		if(validateData()) {
			sql.append("UPDATE "+tableName+" SET ");
			for(int i=0; i<columns.size(); i++) {
				sql.append(columns.get(i)+"=#{"+StringUtil.columnToVarName(columns.get(i))+"}, ");
			}
			sql = new StringBuffer(sql.substring(0, (sql.length()-2)));
			sql.append(this.whereSplitJointSQL(whereRules));
		}		
		return sql.toString();		
	}

	
	/**
	 * 取所有字段值的 select 前缀  (这个方法返回 如： SELECT t_name AS name FROM table;  这样省去了再单独写出 字段和变量 的映射关系)
	 * @return
	 */
	public String selectPrefixSQL() {
		if(this.validateData()) {
			StringBuffer sql = new StringBuffer("SELECT ");
			for(int i=0; i<columnList.size(); i++) {
				sql.append(columnList.get(i)+" AS " + modelVarList.get(i)+", ");				
			}
			return sql.substring(0, sql.length()-2)+" FROM "+tableName;
		}
		return "";
	}
	
	
	/**
	 * 取所有字段值的 select 前缀  (这个方法返回 如： SELECT t_name AS name FROM table;  这样省去了再单独写出 字段和变量 的映射关系)
	 * @return
	 */
	public String selectPrefixSQL(List<String> noGetColumns) {
		if(this.validateData()) {
			boolean isGetColumns = false;  //是否有要取的字段值
			StringBuffer sql = new StringBuffer("SELECT ");
			for(int i=0; i<columnList.size(); i++) {
				if(noGetColumns == null || noGetColumns.size()==0 || !noGetColumns.contains(columnList.get(i))) {
					isGetColumns = true;
					sql.append(columnList.get(i)+" AS " + modelVarList.get(i)+", ");				
				}
			}
			if(isGetColumns) {
				return sql.substring(0, sql.length()-2)+" FROM "+tableName;
			}else {
				throw new SQLColumnException("没有要查询的SQL字段.....");
			}
		}
		return "";
	}

	/**
	 * count 总数
	 * @param whereMap
	 * @return
	 */
	public String selectCountSQL(WhereRuleList whereRules) {
		StringBuffer sb = new StringBuffer("SELECT COUNT(id) FROM "+tableName+" ");
		sb.append(this.whereSplitJointSQL(whereRules));
		return sb.toString();
	}
	
	/**
	 * 简单的分页列表
	 * @return
	 */
	public String selectPageInfoSQL() {
		return this.selectPrefixSQL() + pageLimitSQL();
	}
	
	/**
	 * 分页列表
	 * @param whereRules 判断条件列表
	 * @param start 起始位
	 * @param limit 结束位
	 * @return
	 */
	public String selectPageInfoSQL(WhereRuleList whereRules, int start, int limit) {
		return this.selectPrefixSQL() + this.whereSplitJointSQL(whereRules) + pageLimitSQL(start, limit);
	}

	/**
	 * 分页的最后面那一小段  (" LIMIT #{start}, #{limit}")
	 * @return
	 */
	public String pageLimitSQL() {
		return " LIMIT #{start}, #{limit}";
	}
	
	/**
	 * 分页的最后面那一小段  (" LIMIT 1,10")
	 * @param start 起始位
	 * @param limit 要取的数量
	 * @return
	 */
	public String pageLimitSQL(int start, int limit) {
		return " LIMIT "+start+", "+limit;
	}

	/**
	 * 根据给定条件去查询
	 * @return
	 */
	public String selectByConditions(WhereRuleList whereRules, List<String> noGetColumns) {
		return this.selectPrefixSQL(noGetColumns) + this.whereSplitJointSQL(whereRules);
	}
	
	/**
	 * 根据ID查询
	 * @return
	 */
	public String selectById() {
		WhereRuleList whereRules = new WhereRuleList();
		whereRules.add(new WhereRule("id", ConditionEnum.EQUAL, "#{id}", null));
		return selectByConditions(whereRules, null);
	}


}

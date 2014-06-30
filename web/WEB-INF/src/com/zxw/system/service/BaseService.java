package com.zxw.system.service;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * service 的基类,  通过实现 ApplicationContextAware 接口， 让spring容器在启动的时候自动将context对象的引用传给我
 * 主要功能： 
 * 		- 获得 Mapper对象  （DAO）
 * 
 * 
 * 修改：
 *    - 2013-4-18： 不需要通过context获得sessionFactory，而是直接在配件文件中将 sqlSession注入到 map里面， 这样的话不会出现sqlsession一直打开状态
 * 
 * @author 19lou-zxw
 *
 */
public class BaseService {
	
	/**
	 * sql session 的map
	 */
	@Autowired
	private Map<String, SqlSession> sqlSessionMap = null;

	
	/**
	 * 根据 sqlSession的名称， 获得指定的 mapper
	 * @param sqlSessionName 
	 * @param mapperClass   要获得的mapper 
	 * @return mapperClass<T>
	 */
	public <T> T getMapper(String sqlSessionName, Class<T> mapperClass) {
		return sqlSessionMap.get(sqlSessionName).getMapper(mapperClass);
	}
	
	

}

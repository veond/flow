package com.zxw.organization.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.zxw.organization.model.Role;
import com.zxw.system.extjs.TreePanel;

/**
 * 此处配制的缓存机制还没有具体搞明白，先放着！  2013-12-5  by zxw  ??????
 *  此缓存好像是 session域的
 * @author 19lou-zxw
 *
 */
@CacheNamespace(size=1000)  //(当执行C、U、D操作时，此下的所有select 缓存会被全部清空) 定义在该命名空间内允许使用内置缓存，最大值为1000个对象引用，读写默认是开启的，缓存内省刷新时间为默认3600000毫秒，写策略是拷贝整个对象镜像到全新堆（如同CopyOnWriteList）因此线程安全。 
public interface RoleMapper {

	@Select("SELECT * FROM role WHERE id=#{roleId}")
	@Options(useCache = true, flushCache = false, timeout = 1000)//一些查询的选项开关，比如useCache = true表示本次查询结果被缓存以提高下次查询速度，flushCache = false表示下次查询时不刷新缓存，timeout = 10000表示查询结果缓存10000秒。
	@ResultMap("organization.RoleMapper")  //model 和 字段 映射的xml  mapper
	Role getById(String roleId);
	
	@Update("UPDATE role SET name=#{name}, update_at=now(), remark=#{remark} WHERE id=#{id}")
	@Options(flushCache = true)  //对于需要更新数据库的操作，需要重新刷新缓存flushCache = true使缓存同步。
	void update(Role role);

	@Insert("INSERT INTO role SET id=#{id}, name=#{name}, create_at=#{createAt}, remark=#{remark}")
	@Options(flushCache = true)
	void add(Role role);

	@Select("SELECT COUNT(ID) FROM role")
	@Options(useCache = true, flushCache = false, timeout = 1000)
	int getCount();

	@Select("SELECT * FROM role ORDER BY create_at DESC LIMIT #{start},#{limit}")
	@Options(useCache = true, flushCache = false, timeout = 1000)
	@ResultMap("organization.RoleMapper")
	List<Role> getList(@Param("start") int start, @Param("limit") int limit);   //@Param 为这两参数起一个别名，这样多个参数可以在SQL中通过别名调用到

	/**
	 * 批量删除，多个id 以 "," 分隔
	 * @param ids
	 */
	@Delete("DELETE FROM role WHERE id IN (${ids})")
	@Options(flushCache = true)
	void batDelete(@Param("ids") String ids);

	/**
	 *  获得所有角色的，转换成树
	 * @return
	 */
	@Select("SELECT * FROM role")
	@ResultMap("organization.RoleTreeMapper")
	List<TreePanel> getTree();
	
	

}

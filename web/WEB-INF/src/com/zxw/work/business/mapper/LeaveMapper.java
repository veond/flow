/**
 * 请假流程
 */

package com.zxw.work.business.mapper;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.zxw.organization.model.Role;
import com.zxw.work.business.mapper.sql.provider.LeaveSQLProvider;
import com.zxw.work.business.model.Leave;

@CacheNamespace(size=1000)  
public interface LeaveMapper {
	
	@Select("SELECT COUNT(id) FROM work_leave")
	@Options(useCache = true, flushCache = false, timeout = 1000)
	int getCount();

	@SelectProvider(type=LeaveSQLProvider.class, method="selectPageInfoSQL")
	@Options(useCache = true, flushCache = false, timeout = 1000)
	List<Leave> getList(@Param("start") int start, @Param("limit") int limit);   //@Param 为这两参数起一个别名，这样多个参数可以在SQL中通过别名调用到

	/**
	 * 批量删除，多个id 以 "," 分隔
	 * @param ids
	 */
	@Delete("DELETE FROM work_leave WHERE id IN (${ids})")
	@Options(flushCache = true)
	void batDelete(@Param("ids") String ids);

	/**
	 * 增加
	 * @param leave
	 */
	@InsertProvider(type=LeaveSQLProvider.class, method="insert")
	void insert(Leave leave);

	/**
	 * 更新请假信息   (只更新：msg, start_date, end_date, leave_day, remark)
	 * @param leave
	 */
	@UpdateProvider(type=LeaveSQLProvider.class, method="update")
	void update(Leave leave);


}

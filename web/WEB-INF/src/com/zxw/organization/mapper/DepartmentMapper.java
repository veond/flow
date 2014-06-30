package com.zxw.organization.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.zxw.organization.model.Department;


/**
 * 部门
 * @author 19lou-zxw
 *
 */
public interface DepartmentMapper {

	@Select("select * from department")
	List<Department> getAll();

	@Insert("INSERT INTO department SET id=#{id}, name=#{name}, parent_id=#{parentId}, create_at=#{createAt}, remark=#{remark}")
	void add(Department dept);

	/**
	 * 获得指定父节点下的，所有子节点 
	 * @param fd_pid
	 * @return
	 */
	@Select("select * from department where parent_id=#{parentId}")
	@Deprecated
	List<Department> getChildrens(Map<String, Object> param);
	
	/**
	 * 获得指定父节点下的，所有子节点
	 * @param fd_pid
	 * @return
	 */
	@Select("select * from department where parent_id=#{parentId}")
	List<Department> getByParentId(String parentId);

	/**
	 * 根据名称取部门
	 * @param name
	 * @return
	 */
	@Select("select * from department where name=#{name}")
	Department getByName(String fd_name);

	@Update("update department SET name=#{name}, parent_id=#{parentId} where id=#{id}")
	void update(Department dept);
	
	
	@Update("update department SET name=#{name} where id=#{id}")
	void updateName(Department dept);

	@Update("delete from department where id=#{id}")
	void deleteById(String id);

	@Select("SELECT * FROM department where id=#{detpId}")
	Department getById(String deptId);
}

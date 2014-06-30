package com.zxw.organization.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.zxw.organization.model.User;
import com.zxw.organization.model.tree.UserAllotTree;
import com.zxw.system.constant.ConstantValue;

public interface UserMapper {

	@Insert("INSERT INTO user SET id=#{id}, dept_id=#{deptId}, role_id=#{roleId}, username=#{username}, loginname=#{loginname}, userpass=#{userpass}, status=#{status}, create_at=#{createAt}, remark=#{remark}")
	public void add(User user);

	@Select("SELECT * FROM user WHERE loginname=#{loginname} AND status!="+ConstantValue.USER_STATUS_DELETE)
	@ResultMap("organization.UserMapper")
	public User getByLoginName(String loginname);

	@Update("UPDATE user SET dept_id=#{deptId}, role_id=#{roleId}, username=#{username}, loginname=#{loginname}, userpass=#{userpass}, status=#{status}, update_at=now(), remark=#{remark} WHERE id=#{id}")
	public void update(User user);

	/**
	 * 获得指定部门下 用户的总数
	 * @return
	 */
	@Select("SELECT COUNT(id) FROM user WHERE dept_id=#{deptId} AND status!="+ConstantValue.USER_STATUS_DELETE)
	public int getCountByDept(String deptId);

	@Select("SELECT * FROM user WHERE dept_id=#{deptId} AND status!="+ConstantValue.USER_STATUS_DELETE+" ORDER BY create_at DESC LIMIT #{start},#{limit}")
	@ResultMap("organization.UserMapper")
	public List<User> getListByDept(Map<String, Object> param);
	
	/**
	 * 根据ID， 修改指定数据的状态
	 * @param userStatus  状态值
	 * @param userId  用户ID
	 */
	@Update("UPDATE user SET status=#{userStatus} WHERE id=#{userId}")
	public void updateStatus(Map<String, Object> param);

	/**
	 * 更新指定用户到指定的部门下
	 * @param userIds  
	 * @param deptId  : String
	 * @return
	 */
	@Update("UPDATE user SET dept_id=#{deptId} WHERE id IN (${userIds})")
	public void updateUsersDept(Map<String, Object> param);

	@Select("SELECT COUNT(id) FROM user WHERE role_id=#{roleId}")
	public int getCountByRole(String roleId);

	@Select("SELECT * FROM user WHERE role_id=#{roleId} AND status!="+ConstantValue.USER_STATUS_DELETE+" ORDER BY create_at DESC LIMIT #{start},#{limit}")
	@ResultMap("organization.UserMapper")
	public List<User> getListByRole(@Param("start")int start, @Param("limit")int limit, @Param("roleId")String roleId);

	@Select("SELECT * FROM user WHERE status!="+ConstantValue.USER_STATUS_DELETE+" ORDER BY create_at DESC")
	@ResultMap("organization.UserMapper")
	public List<User> getAllUser();
	
	@Select("SELECT * FROM user WHERE dept_id=#{deptId} AND status!="+ConstantValue.USER_STATUS_DELETE+" ORDER BY create_at DESC")
	@ResultMap("organization.UserAllotTree")
	public List<UserAllotTree> getUserTreeByDept(String deptId);

	/**
	 * 根据部门取用户 ，并且此用户没有设置角色, 或者 角色为传的role_id ( role_id = "" or role_id=#{roleId})
	 * @param deptId
	 * @param roleId
	 * @return
	 */
	@Select("SELECT * FROM user WHERE dept_id=#{deptId} AND (role_id='' OR role_id=#{roleId}) AND status!="+ConstantValue.USER_STATUS_DELETE+" ORDER BY create_at DESC")
	@ResultMap("organization.UserAllotTree")
	public List<UserAllotTree> getUserByDeptNoRole(@Param("deptId")String deptId, @Param("roleId")String roleId);

	@Update("UPDATE user SET role_id='' WHERE id=#{userId}")
	public void removeRole(String userId);
	
	@Update("UPDATE user SET role_id=#{roleId} WHERE id=#{userId}")
	public void allotUserToRole(@Param("roleId")String roleId, @Param("userId")String userId);

	@Update("UPDATE user SET role_id='' WHERE role_id=#{roleId}")
	public void rollbackAllRole(String roleId);

	@Select("SELECT * FROM user WHERE loginname=#{username} AND userpass=#{userpass} AND status="+ConstantValue.USER_STATUS_NORMAL)
	public User getUserByNameAndPass(@Param("username")String username, @Param("userpass")String userpass);

}

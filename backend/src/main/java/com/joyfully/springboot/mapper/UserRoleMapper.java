package com.joyfully.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.joyfully.springboot.entity.Role;
import com.joyfully.springboot.entity.UserRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户角色映射器
 *
 * @author marx
 * @date 2022/01/09
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {
    /**
     * 删除关联通过用户id
     *
     * @param userId 用户id
     * @return int
     */
    @Delete("delete from user_role where user_id = #{userId}")
    public int deleteAssociationByUserId(Integer userId);

    /**
     * 查询角色id通过用户id
     *
     * @param userId 用户id
     * @return {@link List}<{@link Role}>
     */
    @Select("select role_id from user_role where user_id = #{userId}")
    public List<Integer> selectRoleIdByUserId(Integer userId);
}

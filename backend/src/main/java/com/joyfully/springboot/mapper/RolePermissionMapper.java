package com.joyfully.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.joyfully.springboot.entity.Permission;
import com.joyfully.springboot.entity.RolePermission;
import org.apache.ibatis.annotations.Delete;

import java.util.List;

/**
 * 角色权限映射器
 *
 * @author marx
 * @date 2022/01/09
 */
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
    /**
     * 删除关联通过角色id
     *
     * @param roleId 角色id
     * @return int
     */
    @Delete("delete from role_permission where role_id = #{roleId}")
    public int deleteAssociationByRoleId(Integer roleId);

    /**
     * 查询权限通过角色id列表
     *
     * @param roleIdList 角色id列表
     * @return {@link List}<{@link Permission}>
     */
    public List<Permission> selectPermissionByRoleIdList(List<Integer> roleIdList);
}

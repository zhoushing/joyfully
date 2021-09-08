package com.joyfully.springboot.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.joyfully.springboot.entity.Admin;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 管理员服务
 *
 * @author marx
 * @date 2021/07/31
 */
public interface AdminService {
    /**
     * 检查权限
     *
     * @param id id
     * @return boolean
     */
    boolean checkPower(Integer id);

    /**
     * 插入一条记录
     *
     * @param entity 实体对象
     * @return int
     */
    int insert(Admin entity);

    /**
     * 根据 ID 删除
     *
     * @param id 主键ID
     * @return int
     */
    int deleteById(Serializable id);

    /**
     * 根据 columnMap 条件，删除记录
     *
     * @param columnMap 表字段 map 对象
     * @return int
     */
    int deleteByMap(Map<String, Object> columnMap);

    /**
     * 根据 entity 条件，删除记录
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null,里面的 entity 用于生成 where 语句）
     * @return int
     */
    int delete(Wrapper<Admin> queryWrapper);

    /**
     * 删除（根据ID 批量删除）
     *
     * @param idList 主键ID列表(不能为 null 以及 empty)
     * @return int
     */
    int deleteBatchIds(Collection<? extends Serializable> idList);

    /**
     * 根据 ID 修改
     *
     * @param entity 实体对象
     * @return int
     */
    int updateById(Admin entity);

    /**
     * 根据 whereEntity 条件，更新记录
     *
     * @param entity        实体对象 (set 条件值,可以为 null)
     * @param updateWrapper 实体对象封装操作类（可以为 null,里面的 entity 用于生成 where 语句）
     * @return int
     */
    int update(Admin entity, Wrapper<Admin> updateWrapper);

    /**
     * 根据 ID 查询
     *
     * @param id 主键ID
     * @return {@link Admin}
     */
    Admin selectById(Serializable id);

    /**
     * 查询（根据ID 批量查询）
     *
     * @param idList 主键ID列表(不能为 null 以及 empty)
     * @return {@link List<Admin>}
     */
    List<Admin> selectBatchIds(Collection<? extends Serializable> idList);

    /**
     * 查询（根据 columnMap 条件）
     *
     * @param columnMap 表字段 map 对象
     * @return {@link List<Admin>}
     */
    List<Admin> selectByMap(Map<String, Object> columnMap);

    /**
     * 根据 entity 条件，查询一条记录
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     * @return {@link Admin}
     */
    Admin selectOne(Wrapper<Admin> queryWrapper);

    /**
     * 根据 Wrapper 条件，查询总记录数
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     * @return {@link Integer}
     */
    Integer selectCount(Wrapper<Admin> queryWrapper);

    /**
     * 根据 entity 条件，查询全部记录
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     * @return {@link List<Admin>}
     */
    List<Admin> selectList(Wrapper<Admin> queryWrapper);

    /**
     * 根据 Wrapper 条件，查询全部记录
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     * @return {@link List<Map<String, Object>>}
     */
    List<Map<String, Object>> selectMaps(Wrapper<Admin> queryWrapper);

    /**
     * 根据 Wrapper 条件，查询全部记录
     * <p>注意： 只返回第一个字段的值</p>
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     * @return {@link List<Object>}
     */
    List<Object> selectObjs(Wrapper<Admin> queryWrapper);

    /**
     * 根据 entity 条件，查询全部记录（并翻页）
     *
     * @param page         分页查询条件（可以为 RowBounds.DEFAULAdmin）
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     * @return {@link P}
     */
    <P extends IPage<Admin>> P selectPage(P page, Wrapper<Admin> queryWrapper);

    /**
     * 根据 Wrapper 条件，查询全部记录（并翻页）
     *
     * @param page         分页查询条件
     * @param queryWrapper 实体对象封装操作类
     * @return {@link P}
     */
    <P extends IPage<Map<String, Object>>> P selectMapsPage(P page, Wrapper<Admin> queryWrapper);

}

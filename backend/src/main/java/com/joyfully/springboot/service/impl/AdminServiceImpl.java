package com.joyfully.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.joyfully.springboot.entity.Admin;
import com.joyfully.springboot.mapper.AdminMapper;
import com.joyfully.springboot.service.AdminService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 管理员服务实现
 *
 * @author marx
 * @date 2021/07/31
 */
@Service("adminService")
public class AdminServiceImpl implements AdminService {
    /**
     * 用户映射器
     */
    @Resource
    AdminMapper adminMapper;

    /**
     * 检查权限
     *
     * @param id id
     * @return boolean
     */
    @Override
    public boolean checkPower(Integer id) {
        Admin admin = adminMapper.selectById(id);
        return admin.getPower();
    }
    
    /**
     * 插入一条记录
     *
     * @param entity 实体对象
     * @return int
     */
    @Override
    public int insert(Admin entity) {
        return adminMapper.insert(entity);
    }

    /**
     * 根据 ID 删除
     *
     * @param id 主键ID
     * @return int
     */
    @Override
    public int deleteById(Serializable id) {
        return adminMapper.deleteById(id);
    }

    /**
     * 根据 columnMap 条件，删除记录
     *
     * @param columnMap 表字段 map 对象
     * @return int
     */
    @Override
    public int deleteByMap(Map<String, Object> columnMap) {
        return adminMapper.deleteByMap(columnMap);
    }

    /**
     * 根据 entity 条件，删除记录
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null,里面的 entity 用于生成 where 语句）
     * @return int
     */
    @Override
    public int delete(Wrapper<Admin> queryWrapper) {
        return adminMapper.delete(queryWrapper);
    }

    /**
     * 删除（根据ID 批量删除）
     *
     * @param idList 主键ID列表(不能为 null 以及 empty)
     * @return int
     */
    @Override
    public int deleteBatchIds(Collection<? extends Serializable> idList) {
        return adminMapper.deleteBatchIds(idList);
    }

    /**
     * 根据 ID 修改
     *
     * @param entity 实体对象
     * @return int
     */
    @Override
    public int updateById(Admin entity) {
        return adminMapper.updateById(entity);
    }

    /**
     * 根据 whereEntity 条件，更新记录
     *
     * @param entity        实体对象 (set 条件值,可以为 null)
     * @param updateWrapper 实体对象封装操作类（可以为 null,里面的 entity 用于生成 where 语句）
     * @return int
     */
    @Override
    public int update(Admin entity, Wrapper<Admin> updateWrapper) {
        return adminMapper.update(entity, updateWrapper);
    }

    /**
     * 根据 ID 查询
     *
     * @param id 主键ID
     * @return {@link Admin}
     */
    @Override
    public Admin selectById(Serializable id) {
        return adminMapper.selectById(id);
    }

    /**
     * 查询（根据ID 批量查询）
     *
     * @param idList 主键ID列表(不能为 null 以及 empty)
     * @return {@link List <Admin>}
     */
    @Override
    public List<Admin> selectBatchIds(Collection<? extends Serializable> idList) {
        return adminMapper.selectBatchIds(idList);
    }

    /**
     * 查询（根据 columnMap 条件）
     *
     * @param columnMap 表字段 map 对象
     * @return {@link List<Admin>}
     */
    @Override
    public List<Admin> selectByMap(Map<String, Object> columnMap) {
        return adminMapper.selectByMap(columnMap);
    }

    /**
     * 根据 entity 条件，查询一条记录
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     * @return {@link Admin}
     */
    @Override
    public Admin selectOne(Wrapper<Admin> queryWrapper) {
        return adminMapper.selectOne(queryWrapper);
    }

    /**
     * 根据 Wrapper 条件，查询总记录数
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     * @return {@link Integer}
     */
    @Override
    public Integer selectCount(Wrapper<Admin> queryWrapper) {
        return adminMapper.selectCount(queryWrapper);
    }

    /**
     * 根据 entity 条件，查询全部记录
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     * @return {@link List<Admin>}
     */
    @Override
    public List<Admin> selectList(Wrapper<Admin> queryWrapper) {
        return adminMapper.selectList(queryWrapper);
    }

    /**
     * 根据 Wrapper 条件，查询全部记录
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     * @return {@link List<Map<String, Object>>}
     */
    @Override
    public List<Map<String, Object>> selectMaps(Wrapper<Admin> queryWrapper) {
        return adminMapper.selectMaps(queryWrapper);
    }

    /**
     * 根据 Wrapper 条件，查询全部记录
     * <p>注意： 只返回第一个字段的值</p>
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     * @return {@link List<Object>}
     */
    @Override
    public List<Object> selectObjs(Wrapper<Admin> queryWrapper) {
        return adminMapper.selectObjs(queryWrapper);
    }

    /**
     * 选择页面
     * 根据 entity 条件，查询全部记录（并翻页）
     *
     * @param page         分页查询条件（可以为 RowBounds.DEFAULAdmin）
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     * @return {@link P}
     */
    @Override
    public <P extends IPage<Admin>> P selectPage(P page, Wrapper<Admin> queryWrapper) {
        return adminMapper.selectPage(page, queryWrapper);
    }

    /**
     * 根据 Wrapper 条件，查询全部记录（并翻页）
     *
     * @param page         分页查询条件
     * @param queryWrapper 实体对象封装操作类
     * @return {@link P}
     */
    @Override
    public <P extends IPage<Map<String, Object>>> P selectMapsPage(P page, Wrapper<Admin> queryWrapper) {
        return adminMapper.selectMapsPage(page, queryWrapper);
    }
}

package com.joyfully.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joyfully.springboot.entity.User;
import com.joyfully.springboot.mapper.UserMapper;
import com.joyfully.springboot.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 用户服务实现
 *
 * @author marx
 * @date 2021/07/31
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    /**
     * 用户映射器
     */
    @Resource
    UserMapper userMapper;
    
    /**
     * 插入一条记录
     *
     * @param entity 实体对象
     * @return int
     */
    @Override
    public int insert(User entity) {
        return userMapper.insert(entity);
    }

    /**
     * 根据 ID 删除
     *
     * @param id 主键ID
     * @return int
     */
    @Override
    public int deleteById(Serializable id) {
        return userMapper.deleteById(id);
    }

    /**
     * 根据 columnMap 条件，删除记录
     *
     * @param columnMap 表字段 map 对象
     * @return int
     */
    @Override
    public int deleteByMap(Map<String, Object> columnMap) {
        return userMapper.deleteByMap(columnMap);
    }

    /**
     * 根据 entity 条件，删除记录
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null,里面的 entity 用于生成 where 语句）
     * @return int
     */
    @Override
    public int delete(Wrapper<User> queryWrapper) {
        return userMapper.delete(queryWrapper);
    }

    /**
     * 删除（根据ID 批量删除）
     *
     * @param idList 主键ID列表(不能为 null 以及 empty)
     * @return int
     */
    @Override
    public int deleteBatchIds(Collection<? extends Serializable> idList) {
        return userMapper.deleteBatchIds(idList);
    }

    /**
     * 根据 ID 修改
     *
     * @param entity 实体对象
     * @return int
     */
    @Override
    public int updateById(User entity) {
        return userMapper.updateById(entity);
    }

    /**
     * 根据 whereEntity 条件，更新记录
     *
     * @param entity        实体对象 (set 条件值,可以为 null)
     * @param updateWrapper 实体对象封装操作类（可以为 null,里面的 entity 用于生成 where 语句）
     * @return int
     */
    @Override
    public int update(User entity, Wrapper<User> updateWrapper) {
        return userMapper.update(entity, updateWrapper);
    }

    /**
     * 根据 ID 查询
     *
     * @param id 主键ID
     * @return {@link User}
     */
    @Override
    public User selectById(Serializable id) {
        return userMapper.selectById(id);
    }

    /**
     * 查询（根据ID 批量查询）
     *
     * @param idList 主键ID列表(不能为 null 以及 empty)
     * @return {@link List <User>}
     */
    @Override
    public List<User> selectBatchIds(Collection<? extends Serializable> idList) {
        return userMapper.selectBatchIds(idList);
    }

    /**
     * 查询（根据 columnMap 条件）
     *
     * @param columnMap 表字段 map 对象
     * @return {@link List<User>}
     */
    @Override
    public List<User> selectByMap(Map<String, Object> columnMap) {
        return userMapper.selectByMap(columnMap);
    }

    /**
     * 根据 entity 条件，查询一条记录
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     * @return {@link User}
     */
    @Override
    public User selectOne(Wrapper<User> queryWrapper) {
        return userMapper.selectOne(queryWrapper);
    }

    /**
     * 根据 Wrapper 条件，查询总记录数
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     * @return {@link Integer}
     */
    @Override
    public Integer selectCount(Wrapper<User> queryWrapper) {
        return userMapper.selectCount(queryWrapper);
    }

    /**
     * 根据 entity 条件，查询全部记录
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     * @return {@link List<User>}
     */
    @Override
    public List<User> selectList(Wrapper<User> queryWrapper) {
        return userMapper.selectList(queryWrapper);
    }

    /**
     * 根据 Wrapper 条件，查询全部记录
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     * @return {@link List<Map<String, Object>>}
     */
    @Override
    public List<Map<String, Object>> selectMaps(Wrapper<User> queryWrapper) {
        return userMapper.selectMaps(queryWrapper);
    }

    /**
     * 根据 Wrapper 条件，查询全部记录
     * <p>注意： 只返回第一个字段的值</p>
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     * @return {@link List<Object>}
     */
    @Override
    public List<Object> selectObjs(Wrapper<User> queryWrapper) {
        return userMapper.selectObjs(queryWrapper);
    }

    /**
     * 选择页面
     * 根据 entity 条件，查询全部记录（并翻页）
     *
     * @param page         分页查询条件（可以为 RowBounds.DEFAULUser）
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     * @return {@link P}
     */
    @Override
    public <P extends IPage<User>> P selectPage(P page, Wrapper<User> queryWrapper) {
        return userMapper.selectPage(page, queryWrapper);
    }

    /**
     * 查询所有信息
     *
     * @param page 查询的页面
     * @return {@link Page}<{@link User}>
     */
    @Override
    public Page<User> findPage(Page page, Wrapper<User> queryWrapper) {
        return userMapper.findPage(page, queryWrapper);
    }

    /**
     * 根据 Wrapper 条件，查询全部记录（并翻页）
     *
     * @param page         分页查询条件
     * @param queryWrapper 实体对象封装操作类
     * @return {@link P}
     */
    @Override
    public <P extends IPage<Map<String, Object>>> P selectMapsPage(P page, Wrapper<User> queryWrapper) {
        return userMapper.selectMapsPage(page, queryWrapper);
    }

}

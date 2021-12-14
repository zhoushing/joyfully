package com.joyfully.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joyfully.springboot.entity.Question;
import com.joyfully.springboot.mapper.QuestionMapper;
import com.joyfully.springboot.service.QuestionService;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 问题服务实现
 *
 * @author marx
 * @date 2021/07/31
 */
@Service("questionService")
public class QuestionServiceImpl implements QuestionService {
    /**
     * 问题映射器
     */
    @Resource
    QuestionMapper questionMapper;

    /**
     * jedis
     */
    @Resource
    JedisPool jedisPool;
    
    /**
     * 插入一条记录
     *
     * @param entity 实体对象
     * @return int
     */
    @Override
    public int insert(Question entity) {
        return questionMapper.insert(entity);
    }

    /**
     * 根据 ID 删除
     *
     * @param id 主键ID
     * @return int
     */
    @Override
    public int deleteById(Serializable id) {
        return questionMapper.deleteById(id);
    }

    /**
     * 根据 columnMap 条件，删除记录
     *
     * @param columnMap 表字段 map 对象
     * @return int
     */
    @Override
    public int deleteByMap(Map<String, Object> columnMap) {
        return questionMapper.deleteByMap(columnMap);
    }

    /**
     * 根据 entity 条件，删除记录
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null,里面的 entity 用于生成 where 语句）
     * @return int
     */
    @Override
    public int delete(Wrapper<Question> queryWrapper) {
        return questionMapper.delete(queryWrapper);
    }

    /**
     * 删除（根据ID 批量删除）
     *
     * @param idList 主键ID列表(不能为 null 以及 empty)
     * @return int
     */
    @Override
    public int deleteBatchIds(Collection<? extends Serializable> idList) {
        return questionMapper.deleteBatchIds(idList);
    }

    /**
     * 根据 ID 修改
     *
     * @param entity 实体对象
     * @return int
     */
    @Override
    public int updateById(Question entity) {
        return questionMapper.updateById(entity);
    }

    /**
     * 根据 whereEntity 条件，更新记录
     *
     * @param entity        实体对象 (set 条件值,可以为 null)
     * @param updateWrapper 实体对象封装操作类（可以为 null,里面的 entity 用于生成 where 语句）
     * @return int
     */
    @Override
    public int update(Question entity, Wrapper<Question> updateWrapper) {
        return questionMapper.update(entity, updateWrapper);
    }

    /**
     * 查询所有类别通过userid
     *
     * @param userId
     * @return {@link List<String>}
     */
    @Override
    public List<String> queryAllCategoryByUserId(Integer userId) {
        return this.questionMapper.queryAllCategoryByUserId(userId);
    }

    /**
     * 查询限制条数随机问题
     *
     * @param limit 限制
     * @return {@link List<Question>}
     */
    @Override
    public List<Question> queryRandomLimit(Integer limit) {
        return this.questionMapper.queryRandomLimit(limit);
    }

    /**
     * 查询指定用户的限制条数随机问题
     *
     * @param limit 限制
     * @param id    id
     * @return {@link List}<{@link Question}>
     */
    @Override
    public List<Question> queryRandomLimitById(Integer limit, Integer id) {
        return this.questionMapper.queryRandomLimitById(limit, id);
    }

    /**
     * 根据 ID 查询
     *
     * @param id 主键ID
     * @return {@link Question}
     */
    @Override
    public Question selectById(Serializable id) {
        return questionMapper.selectById(id);
    }

    /**
     * 查询（根据ID 批量查询）
     *
     * @param idList 主键ID列表(不能为 null 以及 empty)
     * @return {@link List <Question>}
     */
    @Override
    public List<Question> selectBatchIds(Collection<? extends Serializable> idList) {
        return questionMapper.selectBatchIds(idList);
    }

    /**
     * 查询（根据 columnMap 条件）
     *
     * @param columnMap 表字段 map 对象
     * @return {@link List<Question>}
     */
    @Override
    public List<Question> selectByMap(Map<String, Object> columnMap) {
        return questionMapper.selectByMap(columnMap);
    }

    /**
     * 根据 entity 条件，查询一条记录
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     * @return {@link Question}
     */
    @Override
    public Question selectOne(Wrapper<Question> queryWrapper) {
        return questionMapper.selectOne(queryWrapper);
    }

    /**
     * 根据 Wrapper 条件，查询总记录数
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     * @return {@link Integer}
     */
    @Override
    public Integer selectCount(Wrapper<Question> queryWrapper) {
        return questionMapper.selectCount(queryWrapper);
    }

    /**
     * 根据 entity 条件，查询全部记录
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     * @return {@link List<Question>}
     */
    @Override
    public List<Question> selectList(Wrapper<Question> queryWrapper) {
        return questionMapper.selectList(queryWrapper);
    }

    /**
     * 根据 Wrapper 条件，查询全部记录
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     * @return {@link List<Map<String, Object>>}
     */
    @Override
    public List<Map<String, Object>> selectMaps(Wrapper<Question> queryWrapper) {
        return questionMapper.selectMaps(queryWrapper);
    }

    /**
     * 根据 Wrapper 条件，查询全部记录
     * <p>注意： 只返回第一个字段的值</p>
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     * @return {@link List<Object>}
     */
    @Override
    public List<Object> selectObjs(Wrapper<Question> queryWrapper) {
        return questionMapper.selectObjs(queryWrapper);
    }

    /**
     * 选择页面
     * 根据 entity 条件，查询全部记录（并翻页）
     *
     * @param page         分页查询条件（可以为 RowBounds.DEFAULQuestion）
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     * @return {@link P}
     */
    @Override
    public <P extends IPage<Question>> P selectPage(P page, Wrapper<Question> queryWrapper) {
        return questionMapper.selectPage(page, queryWrapper);
    }

    /**
     * 查询所有信息
     * @param page 查询的页面
     * @return page
     */
    public Page<Question> findPage(Page<Question> page, Wrapper<Question> queryWrapper) {
        return questionMapper.findPage(page, queryWrapper);
    }

    /**
     * 根据 Wrapper 条件，查询全部记录（并翻页）
     *
     * @param page         分页查询条件
     * @param queryWrapper 实体对象封装操作类
     * @return {@link P}
     */
    @Override
    public <P extends IPage<Map<String, Object>>> P selectMapsPage(P page, Wrapper<Question> queryWrapper) {
        return questionMapper.selectMapsPage(page, queryWrapper);
    }
}

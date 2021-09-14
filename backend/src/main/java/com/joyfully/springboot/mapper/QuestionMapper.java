package com.joyfully.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.joyfully.springboot.entity.Question;

import java.util.List;

/**
 * 问题映射器
 *
 * @author marx
 * @date 2021/07/31
 */
public interface QuestionMapper extends BaseMapper<Question> {

    /**
     * 查询所有类别通过userid
     *
     * @param userId 用户id
     * @return {@link List}<{@link String}>
     */
    List<String> queryAllCategoryByUserId(Integer userId);

    /**
     * 查询限制条数随机问题
     *
     * @param limit 限制
     * @return {@link List<Question>}
     */
    List<Question> queryRandomLimit(Integer limit);

    /**
     * 查询指定用户的限制条数随机问题
     *
     * @param limit 限制
     * @param id    id
     * @return {@link List}<{@link Question}>
     */
    List<Question> queryRandomLimitById(Integer limit, Integer id);
}

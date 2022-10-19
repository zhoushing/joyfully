package com.joyfully.springboot.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.joyfully.springboot.entity.Question;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 问题映射器
 *
 * @author marx
 * @date 2021/07/31
 */
public interface QuestionMapper extends BaseMapper<Question> {
    /**
     * 查询所有
     *
     * @param questionIdList 问题id列表
     * @param search         搜索
     * @return {@link List}<{@link Question}>
     */
    List<Question> queryAll(@Param("questionIdList") List<Integer> questionIdList, @Param("search") String search);

    /**
     * 查询限制条数随机问题
     *
     * @param limit 限制
     * @return {@link List<Question>}
     */
    List<Question> queryRandomLimit(Integer limit);

    /**
     * 查询限制条数根据好评数排序
     *
     * @param limit 限制
     * @return {@link List<Question>}
     */
    List<Question> queryByPraise(Integer limit);
}

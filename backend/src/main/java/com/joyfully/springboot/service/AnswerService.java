package com.joyfully.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.joyfully.springboot.entity.Answer;

/**
 * 回答服务
 *
 * @author marx
 * @date 2022/01/29
 */
public interface AnswerService extends IService<Answer>, EvaluationTargetService {
    /**
     * 插入
     *
     * @param answer 回答
     */
    public void insert(Answer answer);

    /**
     * 更新或插入
     *
     * @param answer 回答
     */
    public void updateOrInsert(Answer answer);

    /**
     * 删除
     *
     * @param answer 回答
     */
    public void delete(Answer answer);

    /**
     * 更新
     *
     * @param answer 回答
     */
    public void update(Answer answer);

    /**
     * 找到通过id
     *
     * @param userId     用户id
     * @param questionId 问题id
     * @return {@link Answer}
     */
    public Answer findById(Integer userId, Integer questionId);
}

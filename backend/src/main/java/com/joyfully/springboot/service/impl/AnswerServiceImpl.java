package com.joyfully.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joyfully.springboot.entity.Answer;
import com.joyfully.springboot.enums.HttpCodeEnum;
import com.joyfully.springboot.exception.AnswerException;
import com.joyfully.springboot.exception.EvaluationException;
import com.joyfully.springboot.mapper.AnswerMapper;
import com.joyfully.springboot.service.AnswerService;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 回答服务实现
 *
 * @author marx
 * @date 2022/01/30
 */
@Service("answerService")
public class AnswerServiceImpl extends ServiceImpl<AnswerMapper, Answer> implements AnswerService {
    /**
     * 回答映射器
     */
    @Resource
    AnswerMapper answerMapper;

    /**
     * 插入
     *
     * @param answer 回答
     */
    @Override
    public void insert(Answer answer) {
        int insert = answerMapper.insert(answer);

        if (insert == 0) {
            throw new AnswerException(HttpCodeEnum.DB_INSERT_ERROR);
        }
    }

    /**
     * 更新或插入
     *
     * @param answer 回答
     */
    @Override
    public void updateOrInsert(Answer answer) {
        Answer select = answerMapper.selectAnswerById(answer.getUserId(), answer.getQuestionId());

        if (select == null) {
            answerMapper.insert(answer);
        }
        else {
            answerMapper.updateById(answer);
        }
    }

    /**
     * 删除
     *
     * @param answer 回答
     */
    @Override
    public void delete(Answer answer) {
        int delete = answerMapper.delete(answer);

        if (delete == 0) {
            throw new AnswerException(HttpCodeEnum.DB_DELETE_ERROR);
        }
    }

    /**
     * 更新
     *
     * @param answer 回答
     */
    @Override
    public void update(Answer answer) {
        int update = answerMapper.updateById(answer);

        if (update == 0) {
            throw new AnswerException(HttpCodeEnum.DB_UPDATE_ERROR);
        }
    }

    /**
     * 评价
     *
     * @param id       用户id和问题id的联合字符串，以"_"分隔
     * @param isBelittle 是赞美
     * @return boolean
     */
    @Override
    public boolean evaluate(String id, boolean isBelittle) {
        String[] split = id.split("_");

        if (split.length != 2) {
            throw new EvaluationException(HttpCodeEnum.EVALUATION_TARGET_ID_EXCEPTION);
        }

        String userId = split[0];
        String questionId = split[1];

        Answer answer = answerMapper.selectAnswerById(userId, questionId);

        if (answer == null) {
            return false;
        }

        if (isBelittle) {
            answer.setBelittleCount(answer.getBelittleCount() + 1);
        }
        else {
            answer.setPraiseCount(answer.getPraiseCount() + 1);
        }

        answerMapper.updateById(answer);

        return true;
    }

    /**
     * 找到通过id
     *
     * @param userId     用户id
     * @param questionId 问题id
     * @return {@link Answer}
     */
    @Override
    public Answer findById(Integer userId, Integer questionId) {
        return answerMapper.selectAnswerById(userId, questionId);
    }

    /**
     * 查询通过id
     *
     * @param id id
     * @return {@link Answer}
     */
    @Override
    public Answer getById(String id) {
        Integer userId = Integer.valueOf(id.split("_")[0]);
        Integer questionId = Integer.valueOf(id.split("_")[1]);
        return findById(userId, questionId);
    }
}

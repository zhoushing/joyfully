package com.joyfully.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.joyfully.springboot.entity.*;
import com.joyfully.springboot.enums.HttpCodeEnum;
import com.joyfully.springboot.exception.QuestionException;
import com.joyfully.springboot.mapper.QuestionCategoryMapper;
import com.joyfully.springboot.mapper.QuestionMapper;
import com.joyfully.springboot.mapper.UserQuestionMapper;
import com.joyfully.springboot.service.QuestionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Wrapper;
import java.util.List;

/**
 * 问题服务实现
 *
 * @author marx
 * @date 2021/07/31
 */
@Service("questionService")
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {
    /**
     * 问题映射器
     */
    @Resource
    QuestionMapper questionMapper;

    /**
     * 用户问题映射器
     */
    @Resource
    UserQuestionMapper userQuestionMapper;

    /**
     * 问题类别映射器
     */
    @Resource
    QuestionCategoryMapper questionCategoryMapper;


    /**
     * 保存问题
     *
     * @param question 问题
     * @return 保存是否成功
     */
    @Override
    public boolean save(Question question) {
        int insert = questionMapper.insert(question);

        List<Category> categoryList = question.getCategoryList();

        if (categoryList != null && categoryList.size() != 0) {
            categoryList.forEach(category -> {
                QuestionCategory questionCategory = QuestionCategory.builder()
                        .questionId(question.getId())
                        .categoryId(category.getId())
                        .build();
                questionCategoryMapper.insert(questionCategory);
            });
        }

        return insert != 0;
    }

    /**
     * 添加关注
     *
     * @param userQuestion 用户问题
     * @return boolean
     */
    @Override
    public boolean addAttention(UserQuestion userQuestion) {
        UserQuestion select = userQuestionMapper.selectRepeat(userQuestion);

        if (select != null) {
            return false;
        }

        int insert = userQuestionMapper.insert(userQuestion);

        if (insert == 0) {
            throw new QuestionException(HttpCodeEnum.DB_INSERT_ERROR);
        }
        return true;
    }

    /**
     * 添加类别
     *
     * @param questionId   问题id
     * @param categoryList 类别列表
     */
    @Override
    public void addCategory(Integer questionId, List<Category> categoryList) {
        if (categoryList == null || categoryList.size() == 0) {
            return ;
        }

        for (Category category : categoryList) {
            LambdaQueryWrapper<QuestionCategory> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(QuestionCategory::getQuestionId, questionId).
                    eq(QuestionCategory::getCategoryId, category.getId());
            QuestionCategory questionCategory = questionCategoryMapper.selectOne(wrapper);

            if (questionCategory == null) {
                questionCategory = QuestionCategory.builder()
                        .questionId(questionId)
                        .categoryId(category.getId())
                        .build();
                questionCategoryMapper.insert(questionCategory);
            }
        }
    }

    /**
     * 添加类别
     *
     * @param questionId 问题id
     * @param category   类别
     */
    @Override
    public void addCategory(Integer questionId, Category category) {
        QuestionCategory questionCategory = QuestionCategory.builder()
                .questionId(questionId)
                .categoryId(category.getId())
                .build();
        questionCategoryMapper.insert(questionCategory);
    }

    /**
     * 删除
     * 删除指定问题
     *
     * @param id id
     */
    @Override
    public void delete(Integer id) {
        int delete = questionMapper.deleteById(id);
        if (delete == 0) {
            throw new QuestionException(HttpCodeEnum.DB_DELETE_ERROR);
        }
    }

    /**
     * 删除通过用户id
     *
     * @param id id
     */
    @Override
    public void deleteByUserId(Integer id) {
        int delete = userQuestionMapper.deleteByUserId(id);
        if (delete == 0) {
            throw new QuestionException(HttpCodeEnum.DB_DELETE_ERROR);
        }
    }

    /**
     * 删除类别
     *
     * @param questionCategory 问题类别
     */
    @Override
    public void deleteCategory(QuestionCategory questionCategory) {
        LambdaQueryWrapper<QuestionCategory> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(QuestionCategory::getQuestionId, questionCategory.getQuestionId())
                .eq(QuestionCategory::getCategoryId, questionCategory.getCategoryId());

        questionCategoryMapper.delete(wrapper);
    }

    /**
     * 更新
     * 更新问题
     *
     * @param question 问题
     */
    @Override
    public void update(Question question) {
        int update = questionMapper.updateById(question);

        if (update == 0) {
            throw new QuestionException(HttpCodeEnum.DB_UPDATE_ERROR);
        }
    }

    /**
     * 评价
     *
     * @param id         id
     * @param isBelittle 是赞美
     * @return boolean
     */
    @Override
    public boolean evaluate(String id, boolean isBelittle) {
        Question question = questionMapper.selectById(id);

        if (question == null) {
            return false;
        }

        if (isBelittle) {
            question.setBelittleCount(question.getBelittleCount() + 1);
        } else {
            question.setPraiseCount(question.getPraiseCount() + 1);
        }

        questionMapper.updateById(question);

        return true;
    }

    /**
     * 得到通过id
     *
     * @param id id
     * @return {@link File}
     */
    @Override
    public Question getById(String id) {
        return questionMapper.selectById(id);
    }

    @Override
    public PageInfo<Question> findPage(Integer pageNum, Integer pageSize, Integer userId) {
        return findPage(pageNum, pageSize, userId, null);
    }

    /**
     * 找到页面
     * 查询问题列表
     *
     * @param pageNum  当前页面位置
     * @param pageSize 页面大小
     * @param userId   用户id
     * @return {@link PageInfo}<{@link Question}>
     */
    @Override
    public PageInfo<Question> findPage(Integer pageNum, Integer pageSize, Integer userId, String search) {
        List<Integer> questionIdList = null;

        if (userId != null) {
            questionIdList = userQuestionMapper.selectQuestionIdByUserId(userId);
        }

        // 注意：只有紧跟着PageHelper.startPage(pageNum,pageSize)的sql语句才被pagehelper起作用
        PageHelper.startPage(pageNum, pageSize);
        List<Question> questionList = questionMapper.queryAll(questionIdList, search);

        return new PageInfo<>(questionList);
    }

    /**
     * 查询限制条数根据好评数排序
     *
     * @param limit 限制
     * @return {@link List<Question>}
     */
    @Override
    public List<Question> queryByPraise(Integer limit) {

        return questionMapper.queryByPraise(limit);
    }
}

package com.joyfully.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.joyfully.springboot.common.Result;
import com.joyfully.springboot.entity.Category;
import com.joyfully.springboot.entity.Question;
import com.joyfully.springboot.entity.QuestionCategory;
import com.joyfully.springboot.entity.UserQuestion;
import com.joyfully.springboot.enums.HttpCodeEnum;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 问题服务
 *
 * @author marx
 * @date 2021/07/31
 */
public interface QuestionService extends IService<Question>, EvaluationTargetService {
    /**
     * 保存问题
     *
     * @param question 问题
     * @return int
     */
    @Override
    public boolean save(Question question);


    /**
     * 添加关注
     *
     * @param userQuestion 用户问题
     * @return boolean
     */
    public boolean addAttention(UserQuestion userQuestion);

    /**
     * 添加类别
     *
     * @param questionId   问题id
     * @param categoryList 类别列表
     */
    public void addCategory(Integer questionId, List<Category> categoryList);

    /**
     * 添加类别
     *
     * @param questionId 问题id
     * @param category   类别
     */
    public void addCategory(Integer questionId, Category category);

    /**
     * 删除指定问题
     *
     * @param id id
     * @return {@link Result<?>}
     */
    public void delete(Integer id);

    /**
     * 删除通过用户id
     *
     * @param id id
     */
    public void deleteByUserId(Integer id);

    /**
     * 删除类别
     *
     * @param questionCategory 问题类别
     */
    public void deleteCategory(QuestionCategory questionCategory);

    /**
     * 更新问题
     *
     * @param question 问题
     * @return {@link int}
     */
    public void update(Question question);

    /**
     * 找到页面
     * 查询问题列表
     *
     * @param pageNum  当前页面位置
     * @param pageSize 页面大小
     * @param userId   用户id
     * @return {@link PageInfo}<{@link Question}>
     */
    public PageInfo<Question> findPage(Integer pageNum, Integer pageSize, Integer userId);

    /**
     * 找到页面
     * 查询问题列表
     *
     * @param pageNum  当前页面位置
     * @param pageSize 页面大小
     * @param userId   用户id
     * @param search   搜索
     * @return {@link PageInfo}<{@link Question}>
     */
    public PageInfo<Question> findPage(Integer pageNum, Integer pageSize, Integer userId, String search);

    /**
     * 查询限制条数根据好评数排序
     *
     * @param limit 限制
     * @return {@link List<Question>}
     */
    public List<Question> queryByPraise(Integer limit);
}

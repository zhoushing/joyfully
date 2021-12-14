package com.joyfully.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joyfully.springboot.entity.Question;
import com.joyfully.springboot.common.Result;
import com.joyfully.springboot.service.impl.QuestionServiceImpl;
import com.joyfully.springboot.service.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 问题控制器
 *
 * @author marx
 * @date 2021/07/31
 */
@RestController
@RequestMapping("/question")
public class QuestionController {

    @Resource(description = "questionService")
    QuestionServiceImpl questionService;

    @Resource(description = "userService")
    UserServiceImpl userService;

    /**
     * 保存问题
     *
     * @param question 问题
     * @return {@link Result<?>}
     */
    @PostMapping("/save")
    public Result<?> save(@RequestBody Question question) {
        if (question.getCategory() == null || "".equals(question.getCategory())) {
            question.setCategory("默认");
        }

        questionService.insert(question);
        return Result.success();
    }

    /**
     * 删除指定问题
     *
     * @param id id
     * @return {@link Result<?>}
     */
    @DeleteMapping("/delete/{id}")
    public Result<?> delete(@PathVariable Long id) {
        questionService.deleteById(id);
        return Result.success();
    }

    /**
     * 更新问题
     *
     * @param question 问题
     * @return {@link Result<?>}
     */
    @PutMapping("/update")
    public Result<?> update(@RequestBody Question question) {
        questionService.updateById(question);
        return Result.success();
    }

    /**
     * 查询问题列表
     *
     * @param pageNum  当前页面位置
     * @param pageSize 页面大小
     * @param search   搜索
     * @return {@link Result<?>}
     */
    @GetMapping("/find")
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<Question> wrapper = Wrappers.<Question>lambdaQuery();

        // search条件不为空时，执行模糊查询，否则查询所有
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(Question::getQUserId, search);
        }

        Page<Question> questionPage = questionService.findPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(questionPage);

    }

    /**
     * 查询页面
     *
     * @param pageNum  当前页面位置
     * @param pageSize 页面大小
     * @param search   搜索
     * @return {@link Result<?>}
     */
    @GetMapping("/findByUserId")
    public Result<?> findPageByUserId(@RequestParam(defaultValue = "1") Integer pageNum,
                                      @RequestParam(defaultValue = "10") Integer pageSize,
                                      @RequestParam(defaultValue = "") String search,
                                      @RequestParam Integer userId) {

        LambdaQueryWrapper<Question> wrapper = Wrappers.<Question>lambdaQuery();

        // 根据userId筛选记录
        wrapper.eq(Question::getQUserId, userId);

        // search条件不为空时，执行模糊查询，否则查询所有
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(Question::getCategory, search);
        }
        Page<Question> questionPage = questionService.findPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(questionPage);

    }

    /**
     * 查询随机问题
     *
     * @param limit 限制条数
     * @return {@link Result<?>}
     */
    @GetMapping("/findRandom")
    public Result<?> findRandom(@RequestParam Integer limit) {
        List<Question> questionList = questionService.queryRandomLimit(limit);
        return Result.success(questionList);
    }

    /**
     * 查询随机问题（指定用户）
     *
     * @param limit  限制条数
     * @param userId 用户id
     * @return {@link Result}<{@link ?}>
     */
    @GetMapping("/findRandomById")
    public Result<?> findRandomById(@RequestParam Integer limit, @RequestParam Integer userId) {
        List<Question> questionList = questionService.queryRandomLimitById(limit, userId);
        return Result.success(questionList);
    }
}
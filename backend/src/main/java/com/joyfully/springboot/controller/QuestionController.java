package com.joyfully.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.joyfully.springboot.common.Result;
import com.joyfully.springboot.entity.Category;
import com.joyfully.springboot.entity.Question;
import com.joyfully.springboot.entity.QuestionCategory;
import com.joyfully.springboot.entity.UserQuestion;
import com.joyfully.springboot.enums.HttpCodeEnum;
import com.joyfully.springboot.exception.QuestionException;
import com.joyfully.springboot.service.BaseService;
import com.joyfully.springboot.service.CategoryService;
import com.joyfully.springboot.service.QuestionService;
import com.joyfully.springboot.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Wrapper;
import java.util.List;

/**
 * 问题控制器
 *
 * @author marx
 * @date 2021/07/31
 */
@Api(tags = "问题操作模块")
@RestController
@RequestMapping("/question")
public class QuestionController {
    /**
     * 问题服务
     */
    @Resource
    QuestionService questionService;

    /**
     * 基础服务
     */
    @Resource
    BaseService baseService;

    /**
     * 类别服务
     */
    @Resource
    CategoryService categoryService;

    /**
     * 保存问题
     *
     * @param question 问题
     * @return {@link Result<?>}
     */
    @ApiImplicitParam(name = "question", value = "要添加的问题", dataType = "Question", required = true)
    @ApiOperation("添加问题")
    @PostMapping("/save")
    public Result<?> save(@RequestBody Question question) throws QuestionException {
        questionService.save(question);
        return Result.success();
    }

    /**
     * 关注
     *
     * @param id id
     * @return {@link Result}
     */
    @ApiOperation("关注")
    @PostMapping("/attention/{id}")
    public Result<?> attention(@PathVariable Integer id, HttpServletRequest request) {
        if (id == null) {
            return Result.error(HttpCodeEnum.QUESTION_NOT_FIND);
        }

        String token = request.getHeader("token");
        Integer userId = baseService.getUserId(token);

        UserQuestion userQuestion = UserQuestion.builder()
                .userId(userId)
                .questionId(id)
                .build();

        boolean attention = questionService.addAttention(userQuestion);

        if (!attention) {
            return Result.error(HttpCodeEnum.REPEAT_ATTENTION_QUESTION);
        }

        return Result.success();
    }


    /**
     * 添加问题类别
     *
     * @return {@link Result<?>}
     */
    @ApiOperation("添加问题类别")
    @PostMapping("/category/{name}")
    public Result<?> addCategory(@PathVariable String name) {
        LambdaQueryWrapper<Category> wrapper = Wrappers.lambdaQuery();

        wrapper.eq(Category::getName, name);

        Category category = categoryService.getOne(wrapper);

        if (category != null) {
            return Result.error(HttpCodeEnum.QUESTION_CATEGORY_REPEAT);
        }

        category = Category.builder().name(name).build();
        categoryService.save(category);

        return Result.success();
    }

    /**
     * 删除指定问题
     *
     * @param id id
     * @return {@link Result<?>}
     */
    @ApiImplicitParam(name = "id", value = "要删除的问题ID", dataType = "Integer", required = true)
    @ApiOperation("删除指定问题")
    @DeleteMapping("/delete/{id}")
    public Result<?> delete(@PathVariable Integer id) throws QuestionException {
        questionService.delete(id);
        return Result.success();
    }

    /**
     * 删除问题类别
     *
     * @param questionCategory 问题类别
     * @return {@link Result}<{@link ?}>
     */
    @ApiOperation("添加问题类别")
    @DeleteMapping("/category")
    public Result<?> deleteCategory(@RequestBody QuestionCategory questionCategory) {
        questionService.deleteCategory(questionCategory);
        return Result.success();
    }

    /**
     * 更新问题
     *
     * @param question 问题
     * @return {@link Result<?>}
     */
    @ApiImplicitParam(name = "question", value = "要更新的问题", dataType = "Question", required = true)
    @ApiOperation("更新问题")
    @PutMapping("/update")
    public Result<?> update(@RequestBody Question question) throws QuestionException {
        questionService.update(question);

        List<Category> categoryList = question.getCategoryList();
        if (categoryList != null && categoryList.size() != 0) {
            questionService.addCategory(question.getId(), categoryList);
        }

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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "查询的页数", dataType = "String", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "查询的页大小", dataType = "String", defaultValue = "10"),
            @ApiImplicitParam(name = "search", value = "筛选信息", dataType = "String", defaultValue = "")
    })
    @ApiOperation("查询问题列表")
    @GetMapping("/find")
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        if (!"".equals(search) && !StringUtil.isNumeric(search)) {
            return Result.error(HttpCodeEnum.USER_ID_FORMAT_ERROR);
        }

        PageInfo<Question> page = questionService.findPage(pageNum, pageSize, ("".equals(search)? null :Integer.parseInt(search)));

        return Result.success(page);
    }

    /**
     * 找到页面通过用户id
     *
     * @param pageNum  当前页面位置
     * @param pageSize 页面大小
     * @param request  请求
     * @return {@link Result}<{@link ?}>
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "查询的页数", dataType = "String", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "查询的页大小", dataType = "String", defaultValue = "10"),
            @ApiImplicitParam(name = "search", value = "筛选信息", dataType = "String", defaultValue = "")
    })
    @ApiOperation("查询问题列表通过用户ID")
    @GetMapping("/findByUserId")
    public Result<?> findPageByUserId(@RequestParam(defaultValue = "1") Integer pageNum,
                                      @RequestParam(defaultValue = "10") Integer pageSize,
                                      @RequestParam(defaultValue = "") String search,
                                      HttpServletRequest request) {
        Integer userId = baseService.getUserId(request);

        PageInfo<Question> page = questionService.findPage(pageNum, pageSize, userId, search);

        return Result.success(page);
    }

    /**
     * 查询问题（根据点赞数排序）
     *
     * @param limit 限制条数
     * @return {@link Result<?>}
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "查询的页数", dataType = "String", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "查询的页大小", dataType = "String", defaultValue = "10"),
            @ApiImplicitParam(name = "search", value = "筛选信息", dataType = "String", defaultValue = "")
    })
    @ApiOperation("查询问题（根据点赞数排序）")
    @GetMapping("/findByPraise")
    public Result<?> findQuestionOrderByPraiseCount(@RequestParam Integer limit) {
        List<Question> questionList = questionService.queryByPraise(limit);
        return Result.success(questionList);
    }

    /**
     * 查询问题（根据点赞数排序）
     *
     * @return {@link Result<?>}
     */
    @ApiOperation("查询所有问题类别")
    @GetMapping("/category")
    public Result<?> findCategory() {
        List<Category> categoryList = categoryService.list();
        return Result.success(categoryList);
    }

}
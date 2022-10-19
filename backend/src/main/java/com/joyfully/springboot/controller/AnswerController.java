package com.joyfully.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.joyfully.springboot.common.Result;
import com.joyfully.springboot.entity.Answer;
import com.joyfully.springboot.enums.HttpCodeEnum;
import com.joyfully.springboot.exception.AnswerException;
import com.joyfully.springboot.service.AnswerService;
import com.joyfully.springboot.service.BaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 回答控制器
 *
 * @author marx
 * @date 2022/01/30
 */
@Api(tags = "回答操作模块")
@RestController
@RequestMapping("/answer")
public class AnswerController {
    /**
     * 回答映射器
     */
    @Resource
    AnswerService answerService;

    /**
     * 基础服务
     */
    @Resource
    BaseService baseService;

    /**
     * 插入
     *
     * @param answer 回答
     */
    @ApiImplicitParam(name = "answer", value = "要添加的回答", dataType = "Answer", required = true)
    @ApiOperation("添加回答")
    @PostMapping("/save")
    public Result<?> save(@RequestBody Answer answer, HttpServletRequest request) throws AnswerException{
        Result check = checkAnswer(answer, false);

        if (check != null) {
            return check;
        }

        String token = request.getHeader("token");
        Integer userId = baseService.getUserId(token);

        answer.setUserId(userId);

        answerService.updateOrInsert(answer);

        return Result.success();
    }

    /**
     * 删除
     *
     * @param answer 回答
     * @return {@link Result}<{@link ?}>
     */
    @ApiImplicitParam(name = "answer", value = "要删除的回答", dataType = "Answer", required = true)
    @ApiOperation("删除回答")
    @DeleteMapping("/delete")
    public Result<?> delete(@RequestBody Answer answer) throws AnswerException {
        Result check = checkAnswer(answer, true);

        if (check != null) {
            return check;
        }

        answerService.delete(answer);

        return Result.success();
    }

    /**
     * 用户删除自己的回答
     *
     * @param answer 回答
     * @return {@link Result}<{@link ?}>
     */
    @ApiImplicitParam(name = "answer", value = "要删除的回答", dataType = "Answer", required = true)
    @ApiOperation("用户删除自己的回答")
    @DeleteMapping("/deleteByUser")
    public Result<?> deleteByUser(@RequestBody Answer answer, HttpServletRequest request) throws AnswerException {
        Result check = checkAnswer(answer, false);

        if (check != null) {
            return check;
        }

        String token = request.getHeader("token");
        Integer userId = baseService.getUserId(token);

        answer.setUserId(userId);

        answerService.delete(answer);

        return Result.success();
    }

    /**
     * 用户更新自己的回答
     *
     * @param answer 回答
     */
    @ApiImplicitParam(name = "answer", value = "要更新的回答", dataType = "Answer", required = true)
    @ApiOperation("用户更新自己的回答")
    @PostMapping("/updateByUser")
    public Result<?> update(@RequestBody Answer answer, HttpServletRequest request) throws AnswerException {
        if (answer.getContent() == null && answer.getPraiseCount() == null && answer.getBelittleCount() == null) {
            return Result.error(HttpCodeEnum.ANSWER_NOT_FILL);
        }
        
        Result check = checkAnswer(answer, false);

        if (check != null) {
            return check;
        }

        String token = request.getHeader("token");
        Integer userId = baseService.getUserId(token);

        answer.setUserId(userId);

        answerService.update(answer);

        return Result.success();
    }

    /**
     * 检查答案
     *
     * @param answer    答案
     * @param checkUser 检查用户
     */
    private Result checkAnswer(Answer answer, boolean checkUser) {
        if (answer.getQuestionId() == null) {
            return Result.error(HttpCodeEnum.ANSWER_UNRELATED_QUESTION);
        }
        if (checkUser && answer.getUserId() == null) {
            return Result.error(HttpCodeEnum.ANSWER_UNRELATED_USER);
        }
        if (StrUtil.isBlank(answer.getContent())) {
            return Result.error(HttpCodeEnum.ANSWER_NOT_FILL);
        }
        return null;
    }
}

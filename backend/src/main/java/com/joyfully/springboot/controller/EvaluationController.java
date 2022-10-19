package com.joyfully.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.joyfully.springboot.common.Result;
import com.joyfully.springboot.controller.dto.EvaluationInfo;
import com.joyfully.springboot.controller.dto.EvaluationTargetInfo;
import com.joyfully.springboot.entity.Evaluation;
import com.joyfully.springboot.enums.HttpCodeEnum;
import com.joyfully.springboot.exception.CustomException;
import com.joyfully.springboot.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * 评价控制器
 *
 * @author marx
 * @date 2022/03/02
 */
@Api(tags = "评价操作模块")
@RestController
@RequestMapping("/evaluation")
public class EvaluationController {
    /**
     * 评价服务
     */
    @Resource
    EvaluationService evaluationService;

    /**
     * 问题服务
     */
    @Resource
    QuestionService questionService;

    /**
     * 回答服务
     */
    @Resource
    AnswerService answerService;

    /**
     * 文件服务
     */
    @Resource
    FileService fileService;

    /**
     * 基础服务
     */
    @Resource
    BaseService baseService;

    /**
     * 目标map
     */
    private static HashMap<String, EvaluationTargetService> targetMap = new HashMap<>();

    /**
     * 目标set
     */
    private static HashSet<String> targetSet = new HashSet<>();

    /**
     * 方便填充对应的评价目标
     */
    @PostConstruct
    public void init() {
        targetSet.add("question");
        targetSet.add("answer");
        targetSet.add("file");

        targetMap.put("question", questionService);
        targetMap.put("answer", answerService);
        targetMap.put("file", fileService);
    }

    /**
     * 评价
     *
     * @param evaluation 评价
     * @return {@link Result}<{@link ?}>
     */
    @ApiImplicitParam(name = "evaluation", value = "评价", dataType = "Evaluation", required = true)
    @ApiOperation("评价")
    @PostMapping
    public Result<?> evaluate(@RequestBody Evaluation evaluation, HttpServletRequest request) throws CustomException {
        Result<?> check = checkTarget(evaluation.getTarget());

        if (check != null) {
            return check;
        }

        // 获取评价人的ID
        String token = request.getHeader("token");
        Integer userId = baseService.getUserId(token);

        // 查询是否已经做出过评价
        Evaluation select = evaluationService.getOne(evaluation.getTarget(), userId);

        if (select != null) {
            return Result.error(HttpCodeEnum.ALREADY_EVALUATED);
        }

        String[] split = evaluation.getTarget().split(",", 2);

        if (!evaluation.getReport()) {
            boolean evaluate = targetMap.get(split[0]).evaluate(split[1], evaluation.getBelittle());

            if (!evaluate) {
                return Result.error(HttpCodeEnum.EVALUATION_TARGET_NOT_EXISTS);
            }
        }

        // 填充默认将举报理由
        if (StrUtil.isBlank(evaluation.getReportReason())) {
            evaluation.setReportReason("无");
        }

        // 评价人ID注入
        evaluation.setUserId(userId);

        evaluationService.save(evaluation);

        return Result.success();
    }

    /**
     * 标记已经审查
     *
     * @param evaluation 评价
     * @return {@link Result}<{@link ?}>
     * @throws CustomException 自定义异常
     */
    @ApiImplicitParam(name = "evaluation", value = "评价", dataType = "Evaluation", required = true)
    @ApiOperation("评价")
    @PostMapping("/pass")
    public Result<?> pass(@RequestBody Evaluation evaluation) throws CustomException {
        Result<?> check = checkTarget(evaluation.getTarget());

        if (check != null) {
            return check;
        }

        Map<String, Object> map = new HashMap<String, Object>(){{
            put("user_id", evaluation.getUserId());
            put("target", evaluation.getTarget());
        }};

        UpdateWrapper<Evaluation> wrapper = Wrappers.update();
        wrapper.allEq(map)
                .set("checked", true);

        evaluationService.update(wrapper);

        return Result.success();
    }

    /**
     * 检查目标
     *
     * @param target 目标
     * @return {@link Result}<{@link ?}>
     */
    private Result<?> checkTarget(String target) {
        if (StrUtil.isBlank(target)) {
            return Result.error(HttpCodeEnum.EVALUATION_TARGET_IS_EMPTY);
        }

        String[] split = target.split(",", 2);

        if (split.length != 2) {
            return Result.error(HttpCodeEnum.EVALUATION_TARGET_ID_EXCEPTION);
        }

        String targetObj = split[0];

        if (!targetSet.contains(targetObj)) {
            return Result.error(HttpCodeEnum.EVALUATION_TARGET_NOT_SUPPORT);
        }

        return null;
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "查询的页数", dataType = "String", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "查询的页大小", dataType = "String", defaultValue = "10"),
            @ApiImplicitParam(name = "search", value = "要筛选的目标用户昵称", dataType = "String", defaultValue = "")
    })
    @ApiOperation("查询所有评价")
    @GetMapping("/all")
    public Result<?> getEvaluations(@RequestParam(defaultValue = "1") Integer pageNum,
                                    @RequestParam(defaultValue = "10") Integer pageSize,
                                    @RequestParam(defaultValue = "") String search) {
        return Result.success(evaluationService.getAll(pageNum, pageSize, search));
    }

    /**
     * 得到评价目标
     *
     * @param target 目标
     * @return {@link Result}<{@link ?}>
     */
    @ApiOperation("查询指定评价目标")
    @GetMapping("/one")
    public Result<?> getEvaluationTarget(@RequestParam String target) {
        Result<?> check = checkTarget(target);

        if (check != null) {
            return check;
        }

        String[] split = target.split(",", 2);

        EvaluationTargetInfo targetInfo = EvaluationTargetInfo.getTargetInfo(targetMap.get(split[0]).getById(split[1]));

        if (targetInfo == null) {
            return Result.error(HttpCodeEnum.EVALUATION_TARGET_NOT_EXISTS);
        }

        return Result.success(targetInfo);
    }
}

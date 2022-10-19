package com.joyfully.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.joyfully.springboot.controller.dto.EvaluationInfo;
import com.joyfully.springboot.entity.Evaluation;
import com.joyfully.springboot.mapper.EvaluationMapper;
import com.joyfully.springboot.service.EvaluationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 评价服务实现
 *
 * @author marx
 * @date 2022/03/02
 */
@Service("evaluationService")
public class EvaluationServiceImpl extends ServiceImpl<EvaluationMapper, Evaluation> implements EvaluationService {
    /**
     * 评价映射器
     */
    @Resource
    EvaluationMapper evaluationMapper;

    /**
     * 得到所有
     *
     * @param pageNum  页面个数
     * @param pageSize 页面大小
     * @param search   搜索
     * @return {@link PageInfo}<{@link EvaluationInfo}>
     */
    @Override
    public PageInfo<EvaluationInfo> getAll(Integer pageNum, Integer pageSize, String search) {

        PageHelper.startPage(pageNum, pageSize);
        List<EvaluationInfo> evaluationInfoList = evaluationMapper.selectAll(search + "%");

        return new PageInfo<>(evaluationInfoList);
    }

    /**
     * 得到一个
     *
     * @param target 目标
     * @param userId 用户id
     * @return {@link Evaluation}
     */
    @Override
    public Evaluation getOne(String target, Integer userId) {
        LambdaQueryWrapper<Evaluation> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Evaluation::getTarget, target).eq(Evaluation::getUserId, userId);

        return evaluationMapper.selectOne(wrapper);
    }
}

package com.joyfully.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.joyfully.springboot.controller.dto.EvaluationInfo;
import com.joyfully.springboot.entity.Evaluation;

/**
 * 评价服务
 *
 * @author marx
 * @date 2022/03/02
 */
public interface EvaluationService extends IService<Evaluation> {
    /**
     * 得到所有
     *
     * @param pageNum  页面个数
     * @param pageSize 页面大小
     * @param search   搜索
     * @return {@link PageInfo}<{@link EvaluationInfo}>
     */
    public PageInfo<EvaluationInfo> getAll(Integer pageNum, Integer pageSize, String search);

    /**
     * 得到一个
     *
     * @param target 目标
     * @param userId 用户id
     * @return {@link Evaluation}
     */
    public Evaluation getOne(String target, Integer userId);
}

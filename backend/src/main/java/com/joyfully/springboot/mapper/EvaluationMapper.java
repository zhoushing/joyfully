package com.joyfully.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.joyfully.springboot.controller.dto.EvaluationInfo;
import com.joyfully.springboot.entity.Evaluation;

import java.util.List;

/**
 * 评价映射器
 *
 * @author marx
 * @date 2022/03/02
 */
public interface EvaluationMapper extends BaseMapper<Evaluation> {
    /**
     * 查询所有
     *
     * @param search 搜索
     * @return {@link List}<{@link EvaluationInfo}>
     */
    List<EvaluationInfo> selectAll(String search);
}

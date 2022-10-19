package com.joyfully.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.joyfully.springboot.entity.File;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * 文件映射器
 *
 * @author marx
 * @date 2022/01/09
 */
public interface FileMapper extends BaseMapper<File> {
    /**
     * 查询所有订单通过点赞数排序
     *
     * @param limit 限制
     * @return {@link List}<{@link File}>
     */
    public List<File> queryAllOrderByPraiseCount(Integer limit);
}

package com.joyfully.springboot.service;

/**
 * 评价目标服务
 *
 * @author marx
 * @date 2022/03/20
 */
public interface EvaluationTargetService {
    /**
     * 评价
     *
     * @param id       id
     * @param isBelittle 是赞美
     * @return boolean
     */
    boolean evaluate(String id, boolean isBelittle);

    /**
     * 得到通过id
     *
     * @param id id
     * @return {@link Object}
     */
    Object getById(String id);
}

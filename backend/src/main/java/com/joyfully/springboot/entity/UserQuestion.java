package com.joyfully.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户问题（关注列表）
 *
 * @author marx
 * @date 2021/12/22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user_question")
@Builder
public class UserQuestion {
    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 问题id
     */
    private Integer questionId;
}

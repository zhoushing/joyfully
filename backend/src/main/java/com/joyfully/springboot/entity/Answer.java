package com.joyfully.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 回答
 *
 * @author marx
 * @date 2021/11/03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Answer {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 内容
     */
    private String content;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 问题id
     */
    private Integer questionId;
}

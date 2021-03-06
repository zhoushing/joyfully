package com.joyfully.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;
import java.util.Scanner;

/**
 * 问题
 *
 * @author marx
 * @date 2021/08/03
 */
@TableName("question")
@Data
public class Question {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 问题
     */
    private String issue;

    /**
     * 问题对应的答案列表
     */
    @TableField(exist = false)
    private List<Answer> answerList;

    /**
     * 类别（类似于计算机科学，临床医学）
     */
    private String category;

    /**
     * 类型（比如选择题，问答题）
     */
    private String type;

    /**
     * 用户id
     */
    private Integer qUserId;

    /**
     * 是否分享
     */
    private Boolean share;

}

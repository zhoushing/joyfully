package com.joyfully.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.joyfully.springboot.controller.dto.AnswerInfo;
import com.joyfully.springboot.controller.dto.QuestionerInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Question", description = "问题")
public class Question {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "问题ID", dataType = "Integer")
    private Integer id;

    /**
     * 问题内容
     */
    @ApiModelProperty(value = "问题内容", dataType = "String")
    private String issue;

    /**
     * 问题类型（比如选择题，问答题）
     */
    @ApiModelProperty(value = "问题类型", dataType = "String")
    private String type;

    /**
     * 是否分享
     */
    @ApiModelProperty(value = "问题是否分享", dataType = "Boolean")
    private Boolean share;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "问题所属用户ID", dataType = "Integer")
    private Integer userId;

    /**
     * 点赞数
     */
    @ApiModelProperty(value = "点赞数", dataType = "Integer")
    private Integer praiseCount;

    /**
     * 点踩数
     */
    @ApiModelProperty(value = "点踩数", dataType = "Integer")
    private Integer belittleCount;

    /**
     * 关注数
     */
    @ApiModelProperty(value = "关注数", dataType = "Integer")
    private Integer attentionCount;

    /**
     * 问题对应的答案列表
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "问题对应的答案列表", dataType = "List")
    private List<AnswerInfo> answerList;

    /**
     * 类别列表
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "类别列表", dataType = "List")
    private List<Category> categoryList;

    /**
     * 提问者信息
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "提问者信息", dataType = "QuestionerInfo")
    private QuestionerInfo questionerInfo;
}

package com.joyfully.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@TableName("answer")
@ApiModel(value = "Answer", description = "回答")
public class Answer {
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户ID", dataType = "Integer")
    private Integer userId;

    /**
     * 问题id
     */
    @ApiModelProperty(value = "问题ID", dataType = "Integer")
    private Integer questionId;

    /**
     * 内容
     */
    @ApiModelProperty(value = "答案内容", dataType = "String")
    private String content;

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
}

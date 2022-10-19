package com.joyfully.springboot.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 回答信息
 *
 * @author marx
 * @date 2022/02/26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "AnswerInfo", description = "答案信息")
public class AnswerInfo {
    /**
     * 所属用户id
     */
    @ApiModelProperty(value = "用户ID", dataType = "Integer")
    private Integer userId;

    /**
     * 问题id
     */
    @ApiModelProperty(value = "问题ID", dataType = "Integer")
    private Integer questionId;

    /**
     * 所属用户昵称
     */
    @ApiModelProperty(value = "所属用户昵称", dataType = "String")
    private String userNickname;

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

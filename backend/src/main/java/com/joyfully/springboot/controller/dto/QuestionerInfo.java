package com.joyfully.springboot.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 提问者信息
 *
 * @author marx
 * @date 2022/03/08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "QuestionerInfo", description = "提问者信息")
public class QuestionerInfo {
    /**
     * 用户id
     */
    @ApiModelProperty(value = "提问者用户ID", dataType = "Integer")
    private Integer userId;
    /**
     * 昵称
     */
    @ApiModelProperty(value = "提问者昵称", dataType = "String")
    private String nickname;
    /**
     * 介绍
     */
    @ApiModelProperty(value = "提问者个人简介", dataType = "String")
    private String introduction;
    /**
     * 用户头像
     */
    @ApiModelProperty(value = "提问者用户头像", dataType = "String")
    private String avatar;
    /**
     * 问题数
     */
    @ApiModelProperty(value = "提问者拥有问题数", dataType = "Integer")
    private Integer questionCount;
    /**
     * 回答数
     */
    @ApiModelProperty(value = "提问者拥有回答数", dataType = "Integer")
    private Integer answerCount;
}

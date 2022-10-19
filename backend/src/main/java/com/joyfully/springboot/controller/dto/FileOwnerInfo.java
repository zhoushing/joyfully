package com.joyfully.springboot.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文件所有者信息
 *
 * @author marx
 * @date 2022/04/07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "FileOwnerInfo", description = "文件所有者信息")
public class FileOwnerInfo {
    /**
     * 用户id
     */
    @ApiModelProperty(value = "文件所有者用户ID", dataType = "Integer")
    private Integer userId;
    /**
     * 昵称
     */
    @ApiModelProperty(value = "文件所有者昵称", dataType = "String")
    private String nickname;
    /**
     * 介绍
     */
    @ApiModelProperty(value = "文件所有者个人简介", dataType = "String")
    private String introduction;
    /**
     * 用户头像
     */
    @ApiModelProperty(value = "文件所有者用户头像", dataType = "String")
    private String avatar;
    /**
     * 文件数
     */
    @ApiModelProperty(value = "提问者拥有文件数", dataType = "Integer")
    private Integer fileCount;
}

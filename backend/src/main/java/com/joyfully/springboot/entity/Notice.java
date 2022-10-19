package com.joyfully.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 公告
 *
 * @author marx
 * @date 2022/03/03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("notice")
@ApiModel(value = "Notice", description = "公告")
public class Notice {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "公告ID", dataType = "Integer")
    private Integer id;
    /**
     * 标题
     */
    @ApiModelProperty(value = "标题", dataType = "String")
    private String title;
    /**
     * 内容
     */
    @ApiModelProperty(value = "内容", dataType = "String")
    private String content;
    /**
     * 发布日期
     */
    @ApiModelProperty(value = "公告发布时间", dataType = "java.time.LocalDateTime")
    private LocalDateTime date;
    /**
     * 发布者
     */
    @ApiModelProperty(value = "发布者昵称", dataType = "String")
    private String publisher;

    /**
     * 优先级
     */
    @ApiModelProperty(value = "公告优先级", dataType = "Integer")
    private Integer priority;
}

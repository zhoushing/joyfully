package com.joyfully.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 评价
 *
 * @author marx
 * @date 2022/03/02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("evaluation")
@ApiModel(value = "Evaluation", description = "对目标的评价")
public class Evaluation {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "评价ID", dataType = "Integer")
    private Integer id;
    /**
     * 目标（评价目标+评价目标ID）
     */
    @ApiModelProperty(value = "目标（评价目标+评价目标ID）", dataType = "String")
    private String target;
    /**
     * 是否为差评
     */
    @ApiModelProperty(value = "是否为差评", dataType = "Boolean")
    private Boolean belittle;
    /**
     * 是否为举报
     */
    @ApiModelProperty(value = "是否为举报", dataType = "Boolean")
    private Boolean report;
    /**
     * 举报原因
     */
    @ApiModelProperty(value = "举报原因", dataType = "String")
    private String reportReason;
    /**
     * 所属用户id
     */
    @ApiModelProperty(value = "所属用户ID", dataType = "Integer")
    private Integer userId;
    /**
     * 是否已经审查
     */
    @ApiModelProperty(value = "是否已经审查", dataType = "Boolean")
    private Boolean checked;
}

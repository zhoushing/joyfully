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
 * 类别
 *
 * @author marx
 * @date 2021/12/22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("category")
@Builder
@ApiModel(value = "Category", description = "问题类别")
public class Category {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "问题类别ID", dataType = "Integer")
    private Integer id;

    /**
     * 类别名称
     */
    @ApiModelProperty(value = "问题类别名称", dataType = "String")
    private String name;
}

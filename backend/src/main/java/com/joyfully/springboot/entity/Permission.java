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
 * 许可
 *
 * @author marx
 * @date 2021/12/22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("permission")
@Builder
@ApiModel(value = "Permission", description = "权限")
public class Permission {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "权限ID", dataType = "Integer")
    private Integer id;

    /**
     * 权限名称
     */
    @ApiModelProperty(value = "权限名称", dataType = "String")
    private String name;

    /**
     * 权限路径
     */
    @ApiModelProperty(value = "权限路径", dataType = "String")
    private String path;

    /**
     * 权限备注
     */
    @ApiModelProperty(value = "权限备注", dataType = "String")
    private String remark;

    /**
     * 权限图标
     */
    @ApiModelProperty(value = "权限图标", dataType = "String")
    private String img;
}

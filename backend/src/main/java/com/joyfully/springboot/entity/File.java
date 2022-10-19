package com.joyfully.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.joyfully.springboot.controller.dto.FileOwnerInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * 文件
 *
 * @author marx
 * @date 2021/08/03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("file")
@Builder
@ApiModel(value = "File", description = "文件")
public class File {
    /**
     * uuid
     */
    @TableId(value = "UUID")
    @ApiModelProperty(value = "文件UUID", dataType = "String")
    private String UUID;

    /**
     * 文件名
     */
    @ApiModelProperty(value = "文件名", dataType = "String")
    private String name;

    /**
     * 文件类型
     */
    @ApiModelProperty(value = "文件类型", dataType = "String")
    private String type;

    /**
     * 所属用户id
     */
    @ApiModelProperty(value = "所属用户ID", dataType = "Integer")
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
     * 文件所有者信息
     */
    @ApiModelProperty(value = "文件所有者信息", dataType = "FileOwnerInfo")
    @TableField(exist = false)
    private FileOwnerInfo fileOwnerInfo;

    public File(String UUID, String name, String type, Integer userId) {
        this.UUID = UUID;
        this.name = name;
        this.type = type;
        this.userId = userId;
        this.praiseCount = 0;
        this.belittleCount = 0;
    }
}

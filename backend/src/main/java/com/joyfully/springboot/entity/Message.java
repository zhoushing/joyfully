package com.joyfully.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 消息
 *
 * @author marx
 * @date 2022/03/06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("message")
@ApiModel(value = "Message", description = "消息")
public class Message {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "消息ID", dataType = "Integer")
    private Integer id;
    /**
     * 发送者id
     */
    @ApiModelProperty(value = "发送者ID", dataType = "Integer")
    private Integer fromId;
    /**
     * 接收者id
     */
    @ApiModelProperty(value = "接收者ID", dataType = "Integer")
    private Integer toId;
    /**
     * 内容
     */
    @ApiModelProperty(value = "内容", dataType = "String")
    private String content;
    /**
     * 会话id
     */
    @ApiModelProperty(value = "会话ID", dataType = "String")
    private String conversationId;
    /**
     * 发送时间
     */
    @ApiModelProperty(value = "消息发送时间", dataType = "java.time.LocalDateTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime sendTime;
}

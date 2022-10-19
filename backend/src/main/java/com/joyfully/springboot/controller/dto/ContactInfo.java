package com.joyfully.springboot.controller.dto;

import com.joyfully.springboot.entity.Message;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 联系人信息
 *
 * @author marx
 * @date 2022/04/30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "ContactInfo", description = "联系人信息")
public class ContactInfo {
    /**
     * 联系人id
     */
    @ApiModelProperty(value = "联系人ID", dataType = "Integer")
    private Integer contactId;
    /**
     * 昵称
     */
    @ApiModelProperty(value = "联系人昵称", dataType = "String")
    private String nickname;
    /**
     * 联系人头像
     */
    @ApiModelProperty(value = "用户头像", dataType = "String")
    private String avatar;
    /**
     * 消息列表
     */
    @ApiModelProperty(value = "消息列表", dataType = "List")
    private List<Message> messageList;
}

package com.joyfully.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.joyfully.springboot.common.Result;
import com.joyfully.springboot.controller.dto.ContactInfo;
import com.joyfully.springboot.entity.Message;
import com.joyfully.springboot.entity.User;
import com.joyfully.springboot.exception.MessageException;
import com.joyfully.springboot.service.BaseService;
import com.joyfully.springboot.service.MessageService;
import com.joyfully.springboot.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 消息控制器
 *
 * @author marx
 * @date 2022/03/07
 */
@RestController
@RequestMapping("/message")
public class MessageController {
    /**
     * 消息服务
     */
    @Resource
    MessageService messageService;

    /**
     * 基础服务
     */
    @Resource
    BaseService baseService;

    /**
     * 用户服务
     */
    @Resource
    UserService userService;

    /**
     * 发送
     *
     * @return {@link Result}<{@link ?}>
     */
    @ApiImplicitParam(name = "message", value = "消息", dataType = "Message")
    @ApiOperation("发送消息")
    @PostMapping("/send")
    public Result<?> send(@RequestBody Message message, HttpServletRequest request) throws MessageException {
        Integer fromId = baseService.getUserId(request);
        message.setFromId(fromId);

        String conversationId = fromId + "_" + message.getToId();
        message.setConversationId(conversationId);

        messageService.save(message);

        return Result.success();
    }

    /**
     * 发送
     *
     * @return {@link Result}<{@link ?}>
     */
    @ApiOperation("查询所有消息")
    @GetMapping
    public Result<?> getAll(HttpServletRequest request) throws MessageException {
        Integer userId = baseService.getUserId(request);

        String partConversationId = "" + userId;

        LambdaQueryWrapper<Message> wrapper = Wrappers.lambdaQuery();
        wrapper.like(Message::getConversationId, partConversationId);

        List<Message> messageList = messageService.list(wrapper);

        Map<String, ContactInfo> contactInfoMap = new HashMap<>();

        for (Message message : messageList) {
            Integer otherId = message.getFromId().equals(userId) ? message.getToId(): message.getFromId();
            User user = userService.getUserById(otherId);

            ContactInfo info = contactInfoMap.get(user.getNickname());
            if (info != null) {
                List<Message> messages = info.getMessageList();
                messages.add(message);
                info.setMessageList(messages);
            }
            else {
                List<Message> messages = new ArrayList<>();
                messages.add(message);

                info = ContactInfo.builder()
                        .contactId(otherId)
                        .nickname(user.getNickname())
                        .avatar(user.getAvatar())
                        .messageList(messages)
                        .build();
            }

            contactInfoMap.put(user.getNickname(), info);
        }

        List<ContactInfo> contactInfoList = new ArrayList<>();

        for (ContactInfo value : contactInfoMap.values()) {
            List<Message> list = value.getMessageList();
            list.sort(Comparator.comparing(Message::getSendTime));
            value.setMessageList(list);

            contactInfoList.add(value);
        }

        return Result.success(contactInfoList);
    }
}

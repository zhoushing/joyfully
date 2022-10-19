package com.joyfully.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joyfully.springboot.entity.Message;
import com.joyfully.springboot.mapper.MessageMapper;
import com.joyfully.springboot.service.MessageService;
import org.springframework.stereotype.Service;

/**
 * 消息服务实现
 *
 * @author marx
 * @date 2022/03/07
 */
@Service("messageService")
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {
}

package com.joyfully.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joyfully.springboot.entity.Notice;
import com.joyfully.springboot.mapper.NoticeMapper;
import com.joyfully.springboot.service.NoticeService;
import org.springframework.stereotype.Service;

/**
 * 公告服务实现
 *
 * @author marx
 * @date 2022/03/07
 */
@Service("noticeService")
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {
}

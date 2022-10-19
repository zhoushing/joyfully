package com.joyfully.springboot.service.impl;

import com.joyfully.springboot.mapper.UserMapper;
import com.joyfully.springboot.service.JedisService;
import com.joyfully.springboot.util.TokenUtil;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * jedis服务实现
 *
 * @author marx
 * @date 2022/01/14
 */
@Service("jedisService")
public class JedisServiceImpl implements JedisService {
    /**
     * 要操作的键
     */
    private static final String USER_NAMES = "userNames";

    @Resource
    private JedisPool jedisPool;

    private static JedisPool staticJedisPool;

    @Resource
    private UserMapper userMapper;

    private static UserMapper staticUserMapper;

    /**
     * 初始化
     * {@link TokenUtil}
     */
    @Override
    @PostConstruct
    public void init() {
        staticUserMapper = userMapper;
        staticJedisPool = jedisPool;
    }

    /**
     * 初始化缓存中的userName
     */
    public static void initDate() {
        Jedis jedis = staticJedisPool.getResource();

        List<String> userNameList = staticUserMapper.selectAllUserName();

        System.out.println(userNameList);

        jedis.del(USER_NAMES);

        for (String s : userNameList) {
            jedis.sadd(USER_NAMES, s);
        }

        jedis.close();
    }

    /**
     * 插入用户名字从缓存
     *
     * @param userName 用户名字
     * @return boolean 如果用户名已存在就返回false
     */
    @Override
    public boolean insertUserName(String userName) {
        Jedis jedis = jedisPool.getResource();

        if (!jedis.exists(USER_NAMES)) {
            initDate();
        }

        if (jedis.sismember(USER_NAMES, userName)) {
            return false;
        }

        jedis.sadd(USER_NAMES, userName);

        jedis.close();

        return true;
    }

    /**
     * 删除用户名字从缓存
     *
     * @param userName 用户名字
     * @return boolean 不存在就返回false
     */
    @Override
    public boolean deleteUserName(String userName) {
        Jedis jedis = jedisPool.getResource();

        if (!jedis.exists(USER_NAMES)) {
            initDate();
        }

        if (!jedis.sismember(USER_NAMES, userName)) {
            return false;
        }

        jedis.srem(USER_NAMES, userName);

        jedis.close();

        return true;
    }

    /**
     * 更新用户名字从缓存
     *
     * @param beforeName 之前名字
     * @param nowName    现在名字
     * @return boolean
     */
    @Override
    public boolean updateUserName(String beforeName, String nowName) {
        Jedis jedis = jedisPool.getResource();

        if (!jedis.exists(USER_NAMES)) {
            initDate();
        }

        deleteUserName(beforeName);

        jedis.close();

        return insertUserName(nowName);
    }

    /**
     * 查询用户名字从缓存
     *
     * @param userName 用户名字
     * @return boolean
     */
    @Override
    public boolean selectUserName(String userName) {
        Jedis jedis = jedisPool.getResource();

        if (!jedis.exists(USER_NAMES)) {
            initDate();
        }

        jedis.close();

        return jedis.sismember(USER_NAMES, userName);
    }
}

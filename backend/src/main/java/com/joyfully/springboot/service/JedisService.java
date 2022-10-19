package com.joyfully.springboot.service;

/**
 * jedis服务
 *
 * @author marx
 * @date 2022/01/14
 */
public interface JedisService {

    /**
     * 初始化
     */
    public void init();

    /**
     * 插入用户名字从缓存
     *
     * @param userName 用户名字
     * @return boolean
     */
    public boolean insertUserName(String userName);

    /**
     * 删除用户名字从缓存
     *
     * @param userName 用户名字
     * @return boolean
     */
    public boolean deleteUserName(String userName);

    /**
     * 更新用户名字从缓存
     *
     * @param beforeName 之前名字
     * @param nowName    现在名字
     * @return boolean
     */
    public boolean updateUserName(String beforeName, String nowName);

    /**
     * 查询用户名字从缓存
     *
     * @param userName 用户名字
     * @return boolean
     */
    public boolean selectUserName(String userName);
}

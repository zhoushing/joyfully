package com.joyfully.springboot.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * jedis配置
 *
 * @author marx
 * @date 2021/11/09
 */
@Configuration
public class JedisConfig {
    /**
     * 日志记录器
     */
    private Logger logger = LoggerFactory.getLogger(JedisConfig.class);

    @Value("${spring.redis.port}")
    private Integer port;
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.jedis.pool.max-idle}")
    private Integer maxIdle;
    @Value("${spring.redis.jedis.pool.max-active}")
    private Integer maxActive;
    @Value("${spring.redis.jedis.pool.min-idle}")
    private Integer minIdle;
    @Value("${spring.redis.timeout}")
    private Integer timeout;


    /**
     * jedis 连接池
     *
     * @return {@link JedisPool}
     */
    @Bean
    public JedisPool jedisPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setMaxTotal(maxActive);

        JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, null);

        logger.info("Jedis连接成功：" + host + ":" + port);

        Jedis jedis = jedisPool.getResource();

        if (jedis.exists("userNames")) {
            jedis.del("userNames");
        }

        jedis.close();

        return jedisPool;
    }
}

package com.ysllin.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

public class RedisConfig {
    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;

    /**
     * @return
     * @author 七脉 描述：需要手动注册RedisMessageListenerContainer加入IOC容器
     */
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer() {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();

        container.setConnectionFactory(jedisConnectionFactory);

        return container;

    }

}

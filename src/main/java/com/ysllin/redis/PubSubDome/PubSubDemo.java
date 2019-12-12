package com.ysllin.redis.PubSubDome;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class PubSubDemo {

    public static void main(String[] args) {
        // 连接redis服务端
        JedisPool jedisPool = new JedisPool(new JedisPoolConfig(), "192.168.56.11", 6379);

        System.out.println(String.format("redis pool is starting, redis ip %s, redis port %d", "192.168.56.11", 6379));

        SubThread subThread = new SubThread(jedisPool);  //订阅者
        subThread.start();

        Publisher publisher = new Publisher(jedisPool);    //发布者
        publisher.start();

//        Jedis jedis = new Jedis("192.168.56.11", 6379);
//
//        System.out.println("服务器正在运行：" + jedis.ping());
    }

}

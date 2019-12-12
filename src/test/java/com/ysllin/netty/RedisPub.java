package com.ysllin.netty;

import com.ysllin.nettyRedis.utils.JsonUtil;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RedisPub {

    @Test
    public void aa() {
        System.out.println(123);
        Jedis jedis = new Jedis("192.168.56.11", 6379);
        System.out.println(jedis.ping());
        Map<String, Object> map = new HashMap<>();
        map.put("action", "com.zjs.dzfd.websocket.redis.action.BroadCastAction");
        map.put("message", "11111");
        jedis.publish("websocket", JsonUtil.serializeMap(map));

    }

    @Test
    public void bb() {
        Set<String> sentinels = new HashSet<String>();
        String hostAndPort1 = "10.10.6.47:26379";
        String hostAndPort2 = "10.10.6.47:26479";
        String hostAndPort3 = "10.10.6.47:26579";
        sentinels.add(hostAndPort1);
        sentinels.add(hostAndPort2);
        sentinels.add(hostAndPort3);

        String clusterName = "mymaster";
//        String password = "123456";

        JedisSentinelPool redisSentinelJedisPool = new JedisSentinelPool(clusterName, sentinels);
//        JedisSentinelPool redisSentinelJedisPool = new JedisSentinelPool(clusterName, sentinels, password);

        Jedis jedis = null;
        try {
            jedis = redisSentinelJedisPool.getResource();

            System.out.println(jedis.ping());
            Map<String, Object> map = new HashMap<>();
            map.put("action", "com.zjs.dzfd.websocket.redis.action.BroadCastAction");
//            map.put("action", "com.zjs.dzfd.websocket.redis.action.SendMessageAction");
            map.put("message", "456456");
            jedis.publish("websocket", JsonUtil.serializeMap(map));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            redisSentinelJedisPool.returnBrokenResource(jedis);
        }

        redisSentinelJedisPool.close();
    }
}

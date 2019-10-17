package com.ysllin.redis.netty;

import io.netty.channel.Channel;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserChannelRel {

    private static Map<String, Channel> manager = new ConcurrentHashMap<>();

    public static void put(String userId, Channel channel) {
        manager.put(userId, channel);
    }

    public static Channel get(String userId) {
        return manager.get(userId);
    }

    public static void output() {
        for (HashMap.Entry<String, Channel> entry : manager.entrySet()) {
            System.out.println("UserId: " + entry.getKey() + ", ChannelId: " + entry.getValue().id().asLongText());
        }
    }

    public static void remove(Channel channel) {
        Collection<Channel> values = manager.values();
        values.remove(channel);
    }
}

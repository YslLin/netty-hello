package com.ysllin.nettyRedis;

import com.ysllin.nettyRedis.utils.SpringContextHolder;
import com.ysllin.nettyRedis.utils.WebSocketUtil;

import javax.websocket.Session;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xiongshiyan at 2018/10/10 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class MemWebSocketManager implements WebSocketManager {
    /**
     * 因为全局只有一个 WebSocketManager ，所以才敢定义为非static
     */
    private final Map<String, Session> connections = new ConcurrentHashMap<>(100);

    @Override
    public Session get(String identifier) {
        return connections.get(identifier);
    }

    @Override
    public void put(String identifier, Session session) {
        connections.put(identifier, session);
        // 此处可发布事件 publishEvent
    }

    @Override
    public void remove(String identifier) {
        connections.remove(identifier);
        // 此处可发布事件 publishEvent
    }


    @Override
    public Map<String, Session> localWebSocketMap() {
        return connections;
    }

    @Override
    public void sendMessage(String identifier, String message) {
        Session session = get(identifier);
        if (null == session) {
            throw new RuntimeException("identifier 不存在");
        }

        WebSocketUtil.sendMessage(session, message);
    }

    @Override
    public void broadcast(String message) {
        localWebSocketMap().values().forEach(
                session -> WebSocketUtil.sendMessage(
                        session, message));
    }

    @Override
    public void onMessage(String identifier, String message) {

    }
}

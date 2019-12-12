package com.ysllin.nettyRedis.action;

import com.alibaba.fastjson.JSONObject;
import com.ysllin.nettyRedis.WebSocketManager;
import com.ysllin.nettyRedis.utils.WebSocketUtil;

/**
 * {
 * "action":"broadcast",
 * "message":"xxxxxxxxxxxxx"
 * }
 * 广播给所有的websocket发送消息 action
 *
 * @author xiongshiyan at 2018/10/12 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class BroadCastAction implements Action {
    private static final String MESSAGE = "message";

    @Override
    public void doMessage(WebSocketManager manager, JSONObject object) {
        if (!object.containsKey(MESSAGE)) {
            return;
        }
        String message = object.getString(MESSAGE);
        System.out.println(message);
        // 从本地取出所有的websocket发送消息
        manager.localWebSocketMap().values().forEach(
                session -> WebSocketUtil.sendMessage(
                        session, message));
    }
}

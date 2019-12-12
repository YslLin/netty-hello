package com.ysllin.nettyRedis.action;

import com.alibaba.fastjson.JSONObject;
import com.ysllin.nettyRedis.DefaultRedisReceiver;
import com.ysllin.nettyRedis.WebSocketManager;
import com.ysllin.nettyRedis.utils.WebSocketUtil;

import javax.websocket.Session;

/**
 * {
 * "action":"sendMessage",
 * "identifier":"xxx",
 * "message":"xxxxxxxxxxx"
 * }
 * 给webSocket发送消息的action
 *
 * @author xiongshiyan at 2018/10/12 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class SendMessageAction implements Action {
    private static final String MESSAGE = "message";

    @Override
    public void doMessage(WebSocketManager manager, JSONObject object) {
        if (!object.containsKey(DefaultRedisReceiver.IDENTIFIER)) {
            return;
        }
        if (!object.containsKey(MESSAGE)) {
            return;
        }

        String identifier = object.getString(DefaultRedisReceiver.IDENTIFIER);

        Session session = manager.get(identifier);
        if (null == session) {
            return;
        }
        WebSocketUtil.sendMessage(session, object.getString(MESSAGE));
    }
}

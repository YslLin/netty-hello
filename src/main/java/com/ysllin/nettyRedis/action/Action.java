package com.ysllin.nettyRedis.action;

import com.alibaba.fastjson.JSONObject;
import com.ysllin.nettyRedis.WebSocketManager;

/**
 * 要做的事情
 *
 * @author xiongshiyan
 */
public interface Action {
    /**
     * 根据消息做自己的事情
     *
     * @param manager webSocket管理器
     * @param object  消息体转化的JSON
     */
    void doMessage(WebSocketManager manager, JSONObject object);
}

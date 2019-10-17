package com.ysllin.redis.netty;

import com.ysllin.redis.enums.MsgActionEnum;

/**
 * 消息载体类
 */
public class DataContent {

    private Integer action; // 动作类型
    private String chatMsg; // 消息内容
    private String extend; // 扩展字段
    private MsgActionEnum msgAction; // 扩展字段

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public String getChatMsg() {
        return chatMsg;
    }

    public void setChatMsg(String chatMsg) {
        this.chatMsg = chatMsg;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public MsgActionEnum getMsgAction() {
        return msgAction;
    }

    public void setMsgAction(MsgActionEnum msgAction) {
        this.msgAction = msgAction;
    }
}

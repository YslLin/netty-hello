package com.ysllin.redis.enums;

public enum MsgActionEnum {

    CONNECT(1, "初始化连接"),
    CHAT(2, "聊天消息"),
    SIGNED(3, "消息签收"),
    KEEP_ALIVE(4, "保存心跳"),
    PULL_FRIEND(5, "拉取好友");

    private final Integer type;
    private final String content;

    MsgActionEnum(Integer type, String content) {
        this.type = type;
        this.content = content;
    }

    public Integer getType() {
        return type;
    }

    public String getContent() {
        return content;
    }
}

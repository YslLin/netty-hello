package com.ysllin.redis.netty;

import com.ysllin.redis.enums.MsgActionEnum;
import com.ysllin.redis.utils.JsonUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Optional;

/**
 * 处理消息的handler
 * TextWebSocketFrame：在netty中，是用于为WebSocket 专门处理文本的对象，frame是消息的载体
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    // 用于记录和管理所有客户端的channel
    public static ChannelGroup users = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 每当从客户端接收到新数据时，就用接收到的消息调用此方法。
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        // 获取客户端传输过来的消息
        String content = msg.text();
//        System.out.println(content);

        Channel currentChannel = ctx.channel();

        // 1. 获取客户端发来的消息
        DataContent dataContent = JsonUtils.jsonToPojo(content, DataContent.class);
        if (dataContent == null) return;
        Integer action = dataContent.getAction();
        Integer action1 = Optional.ofNullable(dataContent).map(d -> d.getAction()).orElse(0);
//        System.out.println(action1);

        // 2. 判断消息类型，根据不同的类型处理不同的业务
        if (action.equals(MsgActionEnum.CONNECT.getType())) {
            //  2.1  websocket 连接 open 时，将channel和userId进行关联
            String userId = dataContent.getChatMsg();
            UserChannelRel.put(userId, currentChannel);

            // 测试
            System.out.println("=====================================");
            for (Channel c : users) {
                System.out.println(c.id().asLongText());
            }
            System.out.println("=====================================");
            UserChannelRel.output();

        } else if (action.equals(MsgActionEnum.KEEP_ALIVE.getType())) {
            //  2.4  心跳类型的消息
//            System.out.println("收到来自channel为[" + currentChannel + "]的心跳包...");
        }

        // 使用枚举类属性 在反序列化时 容易发生索引越界异常 所以推荐使用 Integer 类型接收前端数据，如上面代码
        // 枚举值对比 使用 == 与使用 equals 一样，因为在Enum类里面，已经重写了equals方法，里面就是直接使用 ==
//        MsgActionEnum msgAction = dataContent.getMsgAction();
//        if (msgAction == MsgActionEnum.KEEP_ALIVE) {
//            System.out.println("1111111111");
//        }
//        if (msgAction.equals(MsgActionEnum.KEEP_ALIVE)) {
//            System.out.println("222222222");
//        }
    }

    /**
     * 当客户端连接服务端之后（打开连接）
     * 获取客户端的channel，并放到ChannelGroup中去进行管理
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("助手类添加");
        System.out.println("客户端打开连接，channel 长 id 为：" + ctx.channel().id().asLongText()
                + "；端 id 为：" + ctx.channel().id().asShortText());
        users.add(ctx.channel());
    }

    /**
     * 当客观端关闭浏览器（关闭连接）
     * 会触发 handlerRemoved 方法，ChannelGroup 会自动移除对应的客户端 Channel
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("助手类移除");
        System.out.println("客户端断开，channel 长 id 为：" + ctx.channel().id().asLongText()
                + "；端 id 为：" + ctx.channel().id().asShortText());

        // 当触发handlerRemoved，ChannelGroup会自动移除对应客户端的channel
//        users.remove(ctx.channel());
        UserChannelRel.remove(ctx.channel());
    }

    /**
     * 发生异常之后,会自动关闭连接（关闭channel），随后从ChannelGroup中移除
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        System.out.println("捕获到异常...");
        cause.printStackTrace();

        System.out.println("客户端异常，channel 长 id 为：" + ctx.channel().id().asLongText()
                + "；端 id 为：" + ctx.channel().id().asShortText());
        // 发生异常之后关闭连接（关闭channel），随后从ChannelGroup中移除
//        ctx.channel().close();
//        users.remove(ctx.channel());
    }
}

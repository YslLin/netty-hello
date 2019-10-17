package com.ysllin.netty.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;

public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    // 用于记录和管理所有客户端的 Channel
    private static ChannelGroup clients =
            new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 每当从客户端接收到新数据时，就用接收到的消息调用此方法。
     *
     * @param channelHandlerContext
     * @param textWebSocketFrame
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame)
            throws Exception {
        String content = textWebSocketFrame.text();
        System.out.println("接受到的数据：" + content);

        // 向所有客户端发送消息
//        for (Channel channel : clients) {
//            channel.writeAndFlush(
//                    new TextWebSocketFrame(
//                            "[服务器在]" + LocalDateTime.now()
//                                    + "接受消息，消息为：" + content));
//        }
        // 下面这个方法，和上面的 for 循环，一致
        clients.writeAndFlush(
                new TextWebSocketFrame(
                        "[服务器在]" + LocalDateTime.now()
                                + "接受消息，消息为：" + content));

    }

    /**
     * 当客户端连接服务端之后（打开连接）
     * 获取客户端的 Channel ，并放到 ChannelGroup 中去管理
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端打开连接，channel 长 id 为：" + ctx.channel().id().asLongText()
                + "；端 id 为：" + ctx.channel().id().asShortText());
        clients.add(ctx.channel());
    }

    /**
     * 当客观端关闭浏览器（关闭连接）
     * 会触发 handlerRemoved 方法，ChannelGroup 会自动移除对应的客户端 Channel
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
//        clients.remove(ctx.channel()); // 触发 handlerRemoved 的客户端会自动从 ChannelGroup 中移除
        System.out.println("客户端断开，channel 长 id 为：" + ctx.channel().id().asLongText()
                + "；端 id 为：" + ctx.channel().id().asShortText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }


}

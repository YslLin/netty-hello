package com.ysllin.netty.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WSServerInitializter extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        // websocket 基于 http 协议，所以需要 http 编解码器
        pipeline.addLast(new HttpServerCodec());
        // 对写大数据流的支持
        pipeline.addLast(new ChunkedWriteHandler());
        // 对 httpMessage 进行聚合，聚合成 FullHttpRequest 或 FullHttpResponse
        // 几乎在所有 netty 编程中，都会使用到此 handler
        pipeline.addLast(new HttpObjectAggregator(1024 * 64));

        // ====================== 以上是用于支持 http 协议 =============================
        /**
         * WebSocket 服务器处理协议，用于给客户端连接访问的路由：/ws
         * 本 handler 会帮助你处理一些繁重复杂的事
         * 会帮你处理握手动作：handshaking（close， ping， pong） ping + pong = 心跳
         * 对于 WebSocket 来讲，都是以 frames 进行传输的，不同的数据类型对应的 frames 也不同
         */
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        // 聊天助手类
        pipeline.addLast(new ChatHandler());
    }
}

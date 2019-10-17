package com.ysllin.redis.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * 初始化器
 */
public class WSServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        // 信道处理器（拦截器）列表
        ChannelPipeline pipeline = ch.pipeline();

        // 添加信道处理器（拦截器）; ChannelPipeline 的 addLast 方法来添加 ChannelHandler
        // http 编/解码器，WebSocket 基于http 协议
        pipeline.addLast(new HttpServerCodec());
        // 大数据流处理器
        pipeline.addLast(new ChunkedWriteHandler());
        // 对httpMessage进行聚合，聚合成FullHttpRequest或FullHttpResponse
        // 几乎在netty中的编程，都会使用到此handler
        pipeline.addLast(new HttpObjectAggregator(1024 * 64));

        // ===================== 以上是用于支持http协议 =========================


        // ===================== 增加心跳机制 =====================
        // 针对客户端， 如果在1分钟时间没有向服务端发送读写心跳（ALL），则主动断开
        /**
         * 心跳检测处理器
         * readerIdleTime读空闲超时时间设定，
         *      如果channelRead()方法超过readerIdleTime时间未被调用则会触发超时事件调用userEventTrigger()方法；
         * writerIdleTime写空闲超时时间设定，
         *      如果write()方法超过writerIdleTime时间未被调用则会触发超时事件调用userEventTrigger()方法；
         * allIdleTime所有类型的空闲超时时间设定，包括读空闲和写空闲；
         * unit时间单位，默认是 秒；
         * 0 则不检测
         */
        pipeline.addLast(new IdleStateHandler(0, 0, 20000));
        // 自定义的空闲状态检测
        pipeline.addLast(new HeartBeatHandler());


        // ===================== 以下是支持httpWebSocket =======================
        /**
         * WebSocket 服务器处理的协议，用于指定给客户端连接访问的路由: /ws
         * 本handler会帮你处理一些繁重的复杂的事
         * 会帮你处理握手动作： handshaking（close, ping, pong） ping + pong = 心跳
         * 对于 WebSocket 来讲，都是以frames进行传输的，不同的数据类型对应的frames也不同
         */
        pipeline.addLast(new WebSocketServerProtocolHandler("/websocket"));
        // 自定义WebSocket连接处理器
        pipeline.addLast(new ChatHandler());
    }
}

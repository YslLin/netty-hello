package com.ysllin.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @Description: 初始化器，channel 注册后，会执行初始化方法 initChannel
 */
public class HelloServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        // 通过 SocketChannel 获取对应的管道 pipeline
        ChannelPipeline pipeline = channel.pipeline();

        // 通过管道，添加handler
        // HttpServerCodec 是由 Netty 提供的助手类，可以理解为拦截器
        // 当请求到服务器，我们需要做解码，响应到客户端做编码
        pipeline.addLast("HttpServerCodec", new HttpServerCodec());

        // 添加自定义助手类，返回 “hello netty~”
        pipeline.addLast("customHandler", new HelloCustomHandler());
    }
}

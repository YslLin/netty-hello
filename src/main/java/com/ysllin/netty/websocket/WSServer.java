package com.ysllin.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class WSServer {
    public static void main(String[] args) throws Exception {
        // 定义主从线程组
        EventLoopGroup mainGroup = new NioEventLoopGroup();
        EventLoopGroup subGroup = new NioEventLoopGroup();

        try {
            // Netty 服务器启动类
            ServerBootstrap server = new ServerBootstrap();
            server.group(mainGroup, subGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new WSServerInitializter())
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            // 启动 sever 设置服务器端口号
            ChannelFuture future = server.bind(8089).sync();

            // 监听关闭的 channel
            future.channel().closeFuture().sync();
        } finally {
            // 关闭主从线程组
            mainGroup.shutdownGracefully();
            subGroup.shutdownGracefully();
        }
    }
}

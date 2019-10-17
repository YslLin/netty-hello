package com.ysllin.netty.HelloNetty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Description：实现客户端发送一个请求，服务器返回“hello netty~”
 */
public class HelloServer {
    public static void main(String[] args) throws Exception {

        // 定义一对线程组
        // 主线程组，用于接受客户端的连接，但不做任何处理
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 从线程组，负责处理连接的任务
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 创建 Netty 服务器启动类
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup) // 设置主从线程组
                    .channel(NioServerSocketChannel.class) // 设置nio双向通道
                    .childHandler(new HelloServerInitializer()); // 子处理器，用于处理workerGroup

            // 启动server，设置端口号为8088，同时启动方式为同步
            ChannelFuture channelFuture = serverBootstrap.bind(8088).sync();

            // 监听关闭的channel，设置为同步
            channelFuture.channel().closeFuture().sync();
        } finally {
            System.out.println(123);
            // 优雅地关闭主从线程组
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}

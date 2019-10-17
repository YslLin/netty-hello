package com.ysllin.redis.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Netty WebSocket 服务
 */
public class WSServer {

    // 单例模式
    public static class SingletonWSServer {
        static final WSServer instance = new WSServer();
    }

    // 获取实例
    public static WSServer getInstance() {
        return SingletonWSServer.instance;
    }

    // 主线程组
    private EventLoopGroup mainGroup;
    // 从线程组
    private EventLoopGroup subGroup;
    // 服务启动类
    private ServerBootstrap server;
    // 监听器
    private ChannelFuture future;

    public WSServer() {
        mainGroup = new NioEventLoopGroup();
        subGroup = new NioEventLoopGroup();
        server = new ServerBootstrap();
        server.group(mainGroup, subGroup) // 设置主从线程组
                .channel(NioServerSocketChannel.class) // 设置NIO双向通道
                .childHandler(new WSServerInitializer()); // 子处理器，用于处理从线程组任务
    }

    public void start() {
        // 启动server，并且设置端口号为8088
        this.future = server.bind(8088);
        System.out.println("WSServer===netty WebSocket server 启动完毕...");
    }
}

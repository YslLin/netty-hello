package com.ysllin.netty.officialDemo.TimeServer002;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class TimeClient {
    public static void main(String[] args) throws Exception {
        String host = "10.10.12.131";
        if (args.length > 0) {
            host = args[0];
        }
        int port = 8088;
        if (args.length > 1) {
            port = Integer.parseInt(args[1]);
        }

        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // Bootstrap 与 ServerBootstrap 类似，不同之处在于它用于非服务器通道，如客户端通道或无连接通道。
            Bootstrap b = new Bootstrap();
            // 如果您只指定一个 EventLoopGroup，那么它将同时用作boss组和worker组。但是boss worker并不用于客户端。
            b.group(workerGroup);
            // NioSocketChannel用于创建客户端通道，而不是NioServerSocketChannel
            b.channel(NioSocketChannel.class);
            // 注意，这里我们不像在ServerBootstrap中那样使用childOption()，因为客户端SocketChannel没有父节点。
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new TimeClientHandler());
                }
            });

            // 启动客户端
            // 我们应该调用connect()方法，而不是bind()方法。
            ChannelFuture f = b.connect(host, port).sync();

            // 等待连接关闭
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}

package com.ysllin.netty.officialDemo.DiscardServer001;

import com.ysllin.netty.officialDemo.TimeServer002.TimeServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class DiscardServer {

    private int port;

    public DiscardServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new DiscardServerHandler());
                        }
                    })
                    // 您还可以设置特定于通道实现的参数。
                    // option() 用于接受传入连接的 NioServerSocketChannel。
                    // childOption() 用于父服务器通道接受的通道，在本例中是 NioServerSocketChannel。
                    // ServerBootstrap中有option和childOption的设置,区别在哪里?
                    // option()是提供给NioServerSocketChannel用来接收进来的连接,也就是boss线程。
                    // childOption()是提供给由父管道ServerChannel接收到的连接，也就是worker线程。
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            // 绑定并开始接受传入连接
            ChannelFuture future = bootstrap.bind(port).sync();

            // 等待服务器套接字关闭。
            // 在本例中，这种情况不会发生，但是您可以优雅地这样做
            // 关闭服务器。
            future.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {

        int port = 8088;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        new DiscardServer(port).run();
    }
}

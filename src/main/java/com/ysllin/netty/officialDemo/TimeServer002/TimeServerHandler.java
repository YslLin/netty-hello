package com.ysllin.netty.officialDemo.TimeServer002;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    // 当建立连接并准备生成流量时，将调用channelActive()方法。
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("服务端连接激活。。。");
        // 要发送新消息，我们需要分配一个新的缓冲区，其中将包含该消息。
        // 我们要写一个32位整数，因此我们需要一个 ByteBuf，它的容量至少是4字节。
        // 通过 ChannelHandlerContext.alloc() 获取当前 ByteBufAllocator，并分配一个新的缓冲区。
        final ByteBuf time = ctx.alloc().buffer(4);
        time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));

        // ChannelHandlerContext.write()和writeAndFlush()方法返回一个ChannelFuture。
        // ChannelFuture表示尚未发生的I/O操作。
        // 这意味着，可能还没有执行任何请求的操作，因为Netty中的所有操作都是异步的。比如：
        // ch.writeAndFlush(message);
        // ch.close();
        final ChannelFuture f = ctx.writeAndFlush(time);
        // 因此，您需要在ChannelFuture完成之后调用close()方法，该方法由write()方法返回，并且在写操作完成时通知侦听器。
        // 请注意，close()也可能不会立即关闭连接，它将返回一个ChannelFuture。即可以再次添加监听
        // 在这里，我们创建了一个新的匿名 ChannelFutureListener，它在操作完成时关闭通道。
        f.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                assert f == future;
                ctx.close();
            }
        });

        // 或者，您可以使用预定义的侦听器简化代码
//        f.addListener(ChannelFutureListener.CLOSE);
    }
}

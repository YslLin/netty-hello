package com.ysllin.netty.officialDemo.DiscardServer001;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

public class DiscardServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            // 用信息做点什么
            // 释放消息，释放后将无法再次使用
//            ((ByteBuf) msg).release();
            ByteBuf in = (ByteBuf) msg;
//            while (in.isReadable()){
//                System.out.print((char) in.readByte());
//                System.out.flush();
//            }
            // 这个低效的循环实际上可以简化为:
            System.out.println(in.toString(io.netty.util.CharsetUtil.US_ASCII));

            // 将消息写入缓冲区
            // 我们调用write(Object)以逐字记录接收到的消息。
            // 请注意，我们没有像在丢弃示例中那样释放接收到的消息 release()。这是因为Netty在它被写到wire时为您释放它。
            ctx.write(msg);
            // 刷新消息到连接中
            // write(Object)不会将消息写入到连接中。它在内部进行缓冲，然后由ctx.flush()将其刷新到连接。
            ctx.flush();
            // 写入并刷新
            // 为了简单起见，可以调用ctx.writeAndFlush(msg)。
            // msg 被释放之后无法再次使用，会报异常：io.netty.util.IllegalReferenceCountException: refCnt: 0, decrement: 1
            ctx.writeAndFlush(Unpooled.copiedBuffer("hello netty~", CharsetUtil.UTF_8));
        } finally {
            // ByteBuf是一个引用计数的对象，必须通过release()方法显式地释放它。请记住，释放传递给处理程序的任何引用计数的对象是处理程序的责任。
//            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("捕获到异常");
        cause.printStackTrace();
        ctx.close();
    }
}

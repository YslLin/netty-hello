package com.ysllin.netty.officialDemo.TimeServer002;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/**
 * 客户端助手类，从服务器接收一个32位整数，将其转换为人类可读的格式，打印转换后的时间，并关闭连接
 */
public class TimeClientHandler extends ChannelInboundHandlerAdapter {

//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        System.out.println("客户端连接服务端回写成功。。。");
//        // 在TCP/IP中，Netty读取从对等点发送到ByteBuf的数据
//        ByteBuf m = (ByteBuf) msg;
//        try {
//            long currentTimeMillis = (m.readUnsignedInt() - 2208988800L) * 1000L;
//            System.out.println(new Date(currentTimeMillis));
//            ctx.close();
//        } finally {
//            m.release();
//        }
//    }
}

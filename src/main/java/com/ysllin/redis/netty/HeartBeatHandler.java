package com.ysllin.redis.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 自定义空闲检测
 * 用于检测channel的心跳handler
 * 继承ChannelInboundHandlerAdapter，从而不需要实现channelRead0方法
 * IdleStateHandler 当连接的空闲时间（读或者写）太长时，将会触发一个 IdleStateEvent 事件。
 * 然后，你可以通过你的 ChannelInboundHandler 中重写 userEventTriggered 方法来处理该事件。
 */
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("HeartBeatHandler===IdleStateHandler 触发 userEventTriggered");
        // 判断 evt 是否是 IdleStateEvent (用于触发用户事件，读空闲/写空闲/读写空闲)
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;

            if (event.state() == IdleState.READER_IDLE) {
                System.out.println("HeartBeatHandler===进入读空闲...");
            } else if (event.state() == IdleState.WRITER_IDLE) {
                System.out.println("HeartBeatHandler===进入写空闲...");
            } else if (event.state() == IdleState.ALL_IDLE) {
                System.out.println("HeartBeatHandler===进入读写空闲...");
                System.out.println("HeartBeatHandler===channel关闭前，users的数量为：" + ChatHandler.users.size());

                Channel channel = ctx.channel();
                channel.close();

                System.out.println("HeartBeatHandler===channel关闭后，users的数量为：" + ChatHandler.users.size());
            }
        }
    }
}

package com.ysllin;

import com.ysllin.redis.netty.WSServer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Netty 服务启动类
 * web应用程序启动（这里指spring IOC容器加载bean）成功后，进行WebSocket服务启动
 */
@Component
public class NettyBooter implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        // 防止子容器初始化成功后执行两遍
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
            System.out.println("NettyBooter===Application 没有 parent");
            WSServer.getInstance().start();
        } else {
            System.out.println("NettyBooter===Application 有 parent");
        }
    }
}

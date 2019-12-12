package com.ysllin.redis.PubSub001;

import com.ysllin.redis.netty.DataContent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class RedisConfig02 {

//    @Component
//    public class MessageReceiver {
//        /**接收消息的方法*/
//        public void handleMessage(String message){
//            System.out.println("收到一条消息："+message);
//        }
//    }
//
//    /**
//     * redis消息监听器容器
//     * 可以添加多个监听不同话题的redis监听器，只需要把消息监听器和相应的消息订阅处理器绑定，该消息监听器通过反射技术调用消息
//     * 订阅处理器的相关方法进行一些业务处理
//     * @param connectionFactory
//     * @param listenerAdapter
//     * @return
//     */
//    @Bean //相当于xml中的bean
//    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
//                                            MessageListenerAdapter listenerAdapter) {
//
//        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        //订阅了一个叫chat 的通道
//        container.addMessageListener(listenerAdapter, new PatternTopic("chat"));
//        //这个container 可以添加多个 messageListener
//        return container;
//    }
//
//    /**
//     * 消息监听器适配器，绑定消息处理器，利用反射技术调用消息处理器的业务方法
//     * @param receiver
//     * @return
//     */
//    @Bean
//    MessageListenerAdapter listenerAdapter(MessageReceiver receiver) {
//        //这个地方 是给messageListenerAdapter 传入一个消息接受的处理器，利用反射的方法调用“receiveMessage”
//        //也有好几个重载方法，这边默认调用处理器的方法 叫handleMessage 可以自己到源码里面看
//        return new MessageListenerAdapter(receiver);
//    }
//
////    /**redis 读取内容的template */
////    @Bean
////    StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
////        return new StringRedisTemplate(connectionFactory);
////    }
//
//    public static void main(String[] args) throws InterruptedException {
//
//        ApplicationContext ctx = SpringApplication.run(RedisConfig02.class, args);
//
//        StringRedisTemplate template = ctx.getBean(StringRedisTemplate.class);
//
//        template.convertAndSend("chat", "Hello from Redis!");
//
////        String[] namesForType = ctx.getBeanNamesForType(RedisConnectionFactory.class);
////        for (String name : namesForType) {
////            System.out.println("bean名称为==="+name);
////        }
////
////
////        RedisConnectionFactory connectionFactory = ctx.getBean(RedisConnectionFactory.class);
////        MessageReceiver receiver = ctx.getBean(MessageReceiver.class);
////        MessageListenerAdapter listenerAdapter = new MessageListenerAdapter(receiver, "receiveMessage");
////        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
////        container.setConnectionFactory(connectionFactory);
////        //订阅了一个叫chat 的通道
////        container.addMessageListener(listenerAdapter, new PatternTopic("chat"));
//    }
}

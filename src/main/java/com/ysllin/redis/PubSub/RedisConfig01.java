package com.ysllin.redis.PubSub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.util.concurrent.CountDownLatch;

@SpringBootApplication
public class RedisConfig01 {

//    public class Receiver {
//        private CountDownLatch latch;
//
//        @Autowired
//        public Receiver(CountDownLatch latch) {
//            this.latch = latch;
//        }
//
//        public void receiveMessage(String message) {
//            System.out.println("Received <" + message + ">");
//            latch.countDown();
//        }
//
//    }
//
//    @Bean
//    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
//                                            MessageListenerAdapter listenerAdapter) {
//
//        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        container.addMessageListener(listenerAdapter, new PatternTopic("chat"));
//
//        return container;
//    }
//
//    @Bean
//    MessageListenerAdapter listenerAdapter(Receiver receivern) {
//        return new MessageListenerAdapter(receivern, "receiveMessage");
//    }
//
//    @Bean
//    Receiver receiver(CountDownLatch latchl) {
//        return new Receiver(latchl);
//    }
//
//    @Bean
//    CountDownLatch latchr() {
//        return new CountDownLatch(1);
//    }
//
//    @Bean
//    StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
//        return new StringRedisTemplate(connectionFactory);
//    }
//
//    public static void main(String[] args) throws InterruptedException {
//
//        ApplicationContext ctx = SpringApplication.run(RedisConfig01.class, args);
//
//        StringRedisTemplate template = ctx.getBean(StringRedisTemplate.class);
//
//        CountDownLatch latch = ctx.getBean(CountDownLatch.class);
//
//        template.convertAndSend("chat", "Hello from Redis!");
//
//        // 等待子线程执行完毕后，继续执行主线程
//        latch.await();
//
////        System.exit(0);
//    }

}

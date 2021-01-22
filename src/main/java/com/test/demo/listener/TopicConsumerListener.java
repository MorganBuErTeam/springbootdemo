package com.test.demo.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * activeMQ
 * topic模式的消费者
 */
@Component
public class TopicConsumerListener {

    /**
     * 将反馈信息写入到return-register队列，供生产者回调
     * @param message 接收到的消息，此参数会自动注入
     * @return 返回给生产者的信息
     * */
    @JmsListener(destination = "${spring.activemq.topic-name}", containerFactory = "topicListener")
    @SendTo("return-register")
    public Object readActiveQueue(String message) {
        System.out.println("topic接受到：" + message);
        return "我是topic队列的消费者，已收到消息，执行业务......";
    }
}

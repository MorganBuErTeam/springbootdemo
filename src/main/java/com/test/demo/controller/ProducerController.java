package com.test.demo.controller;

/**
 * activeMQ 生产者（queue 和 topic）
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Destination;
import javax.jms.Queue;
import javax.jms.Topic;

@RestController
public class ProducerController {

    //注入存放消息的队列，用于下列方法一
    @Autowired
    private Queue queue;

    @Autowired
    private Topic topic;

    //注入springboot封装的工具类
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @PostMapping("/queue/test")
    public String sendQueue(@RequestBody String str) {
        this.sendMessage(this.queue, str);
        return "success";
    }

    @PostMapping("/topic/test")
    public String sendTopic(@RequestBody String str) {
        this.sendMessage(this.topic, str);
        return "success";
    }

    // 发送消息，destination是发送到的队列，message是待发送的消息
    private void sendMessage(Destination destination, final String message) {
        //方法一：添加消息到消息队列
        jmsMessagingTemplate.convertAndSend(destination, message);
        //方法二：这种方式不需要手动创建queue，系统会自行创建名为test的队列
        //jmsMessagingTemplate.convertAndSend("Test", name);
    }

    /**
     *  此方法用于接收消费者、订阅者的反馈，比如返回是否执行成功，再决定其他的提交、回滚、重试操作
     * @param message 消费者、订阅者返回的消息，此处定义将返回信息写入到return-register队列
     * */
    @JmsListener(destination = "return-register")
    public void returnMsgOne(String message){
        //dev配置文件中，pub-sub-domain: false    如果是true，开启订阅模式，返回的消息可以打印；点对点模式返回的消息则无法打印；
        System.out.println("订阅者说："+message);
    }



}

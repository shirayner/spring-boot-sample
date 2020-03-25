package com.ray.study.smaple.mq.rabbit.receive;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
//监听队列topic.msg
@RabbitListener(queues = {"topic.msg"})
public class MyTopicReceiver1 {

    @RabbitHandler
    public void receiver(String msg) {
        System.out.println("MyTopicReceiver1 :" + msg);
    }
}
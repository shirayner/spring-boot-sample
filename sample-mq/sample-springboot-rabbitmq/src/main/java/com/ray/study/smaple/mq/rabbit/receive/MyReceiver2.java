package com.ray.study.smaple.mq.rabbit.receive;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
//监听队列kinson
@RabbitListener(queues = {"kinson"})
public class MyReceiver2 {

    @RabbitHandler
    public void receiver(String msg) {
        System.out.println("MyReceiver2 :" + msg);
    }
}
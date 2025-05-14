package com.vasyl.practice.rabbitmq.service;

import java.util.Map;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessageDirect(Map<String, Object> map) {
        rabbitTemplate.convertAndSend("test.exch", "testKey", map);
    }

    public void sendMessageFanout(Map<String, Object> map) {
        rabbitTemplate.convertAndSend("test.exch.fanout", "", map);
    }
}

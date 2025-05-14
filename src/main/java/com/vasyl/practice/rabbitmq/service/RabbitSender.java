package com.vasyl.practice.rabbitmq.service;

import java.util.Map;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

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

    public void sendMessageTopic(Map<String, Object> map, String key) {
        rabbitTemplate.convertAndSend("test.exch.topic", key, map);
    }
}

package com.vasyl.practice.rabbitmq.web;

import com.vasyl.practice.rabbitmq.service.RabbitSender;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private RabbitSender rabbitSender;

    @PostMapping("/direct")
    public void sendMessage(@RequestBody(required = false) Map<String, Object> body) {
        rabbitSender.sendMessageDirect(body);
    }

    @PostMapping("/fanout")
    public void sendMessageFanout(@RequestBody(required = false) Map<String, Object> body) {
        rabbitSender.sendMessageFanout(body);
    }

    @PostMapping("/topic")
    public void sendMessageTopic(@RequestBody(required = false) Map<String, Object> body,
            @RequestParam String key) {
        rabbitSender.sendMessageTopic(body, key);
    }
}

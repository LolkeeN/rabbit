package com.vasyl.practice.rabbitmq.consumer;

import java.util.logging.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitConsumerDirect {

    private static final Logger LOG = Logger.getLogger(RabbitConsumerDirect.class.getName());

    @RabbitListener(queues = "testQueue")
    public void handleMessage(Message message) {
        LOG.info("Received Message: " + new String(message.getBody()));
    }

}

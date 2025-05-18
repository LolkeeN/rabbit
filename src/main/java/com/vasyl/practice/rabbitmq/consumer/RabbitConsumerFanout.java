package com.vasyl.practice.rabbitmq.consumer;

import java.util.logging.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitConsumerFanout {

    private static final Logger LOG = Logger.getLogger(RabbitConsumerFanout.class.getName());

    @RabbitListener(queues = "testQueueFanout1")
    public void handleTestFanout1(Message message) {
        LOG.info("Fanout1 Received Message: " + new String(message.getBody()));
    }

    @RabbitListener(queues = "testQueueFanout2")
    public void handleTestFanout2(Message message) {
        LOG.info("Fanout2 Received Message: " + new String(message.getBody()));
    }

    @RabbitListener(queues = "testQueueFanout3")
    public void handleTestFanout3(Message message) {
        LOG.info("Fanout3 Received Message: " + new String(message.getBody()));
    }

}

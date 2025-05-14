package com.vasyl.practice.rabbitmq.consumer;

import java.util.logging.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitConsumerTopic {

    private static final Logger LOG = Logger.getLogger(RabbitConsumerTopic.class.getName());

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "testQueueTopic1", durable = "true"),
            exchange = @Exchange(value = "test.exch.topic", ignoreDeclarationExceptions = "true", type = "topic"),
    key = "test.topic.number.one")
    )
    public void handleTestTopic1(Message message) {
        LOG.info("Topic1 Received Message: " + new String(message.getBody()));
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "testQueueTopic2", durable = "true"),
            exchange = @Exchange(value = "test.exch.topic", ignoreDeclarationExceptions = "true", type = "topic"),
    key = "test.topic.#")
    )
    public void handleTestTopic2(Message message) {
        LOG.info("Topic2 Received Message: " + new String(message.getBody()));
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "testQueueTopic3", durable = "true"),
            exchange = @Exchange(value = "test.exch.topic", ignoreDeclarationExceptions = "true", type = "topic"),
    key = "test.topic.number.*")
    )
    public void handleTestFanout1(Message message) {
        LOG.info("Topic3 Received Message: " + new String(message.getBody()));
    }

}

package com.vasyl.practice.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.util.logging.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
public class RabbitConsumerDirect {

    private static final Logger LOG = Logger.getLogger(RabbitConsumerDirect.class.getName());

    @RabbitListener(queues = "testQueue", containerFactory = "manualAckContainerFactory")
    public void handleMessage(Message message, Channel channel, @Header(value = AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        LOG.info("Received Message: " + new String(message.getBody()));
        channel.basicNack(tag, false, false);
    }

}

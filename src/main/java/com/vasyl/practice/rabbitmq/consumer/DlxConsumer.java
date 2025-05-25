package com.vasyl.practice.rabbitmq.consumer;

import java.util.logging.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class DlxConsumer {

    private final static Logger LOG = Logger.getLogger(DlxConsumer.class.getName());

    @RabbitListener(queues = {"dlx.test.queue"}, containerFactory = "manualAckContainerFactory")
    public void handleMessage(String message) {
        LOG.warning("Received DLX Message: " + message);
    }
}

package com.vasyl.practice.rabbitmq.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RetryConsumer {

    private static final Logger logger = LoggerFactory.getLogger(RetryConsumer.class);

    @RabbitListener(queues = "retryQueue", containerFactory = "retryContainerFactory")
    public void handleWithRetry(String payload) {
        logger.info("Processing message from blocking-queue: {}", payload);

        throw new RuntimeException("exception occured!");
    }
}

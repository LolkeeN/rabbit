package com.vasyl.practice.rabbitmq.config;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.interceptor.MethodInvocationRecoverer;
import org.springframework.retry.interceptor.RetryInterceptorBuilder;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;
import org.springframework.retry.interceptor.StatefulRetryOperationsInterceptor;

@Configuration
@EnableRabbit
public class RabbitConfig {

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());

        return rabbitTemplate;
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("test.exch.topic");
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("test.exch.fanout");
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("test.exch");
    }
    @Bean
    public DirectExchange dlxTestExchange() {
        return new DirectExchange("dlx.test.exch");
    }

    @Bean
    public Queue dlxTestQueue() {
        return new Queue("dlx.test.queue");
    }

    @Bean
    public Queue testQueueTopic1() {
        return new Queue("testQueueTopic1", false);
    }

    @Bean
    public Queue testQueueTopic2() {
        return new Queue("testQueueTopic2", false);
    }

    @Bean
    public Queue testQueueTopic3() {
        return new Queue("testQueueTopic3", false);
    }

    @Bean
    public Queue testQueueFanout1() {
        return new Queue("testQueueFanout1", false);
    }


    @Bean
    public Queue testQueueFanout2() {
        return new Queue("testQueueFanout2", false);
    }

    @Bean
    public Queue testQueueFanout3() {
        return new Queue("testQueueFanout3", false);
    }

    @Bean
    public Queue testQueueDirect1() {
        return QueueBuilder.nonDurable("testQueue")
                .withArgument("x-dead-letter-exchange", "dlx.test.exch")
                .withArgument("x-dead-letter-routing-key", "dlx.test.key")
                .build();
    }

    @Bean
    public Queue retryQueue() {
        return QueueBuilder.nonDurable("retryQueue")
                .build();
    }


    @Bean
    public Declarables topicBindings() {
        Binding topicExchange1 = BindingBuilder.bind(testQueueTopic1()).to(topicExchange()).with("test.topic");
        Binding topicExchange2 = BindingBuilder.bind(testQueueTopic2()).to(topicExchange()).with("test.topic.#");
        Binding topicExchange3 = BindingBuilder.bind(testQueueTopic3()).to(topicExchange()).with("test.topic.number.*");

        Binding fanoutExchange1 = BindingBuilder.bind(testQueueFanout1()).to(fanoutExchange());
        Binding fanoutExchange2 = BindingBuilder.bind(testQueueFanout2()).to(fanoutExchange());
        Binding fanoutExchange3 = BindingBuilder.bind(testQueueFanout3()).to(fanoutExchange());

        Binding directExchange1 = BindingBuilder.bind(testQueueDirect1()).to(directExchange()).with("testKey");
        Binding directExchange2 = BindingBuilder.bind(retryQueue()).to(directExchange()).with("testKey");

        Binding dlxExchangeBinding = BindingBuilder.bind(dlxTestQueue()).to(dlxTestExchange()).with("retry.key");
        return new Declarables(topicExchange1, topicExchange2, topicExchange3,
                fanoutExchange1, fanoutExchange2, fanoutExchange3, directExchange1, dlxExchangeBinding,
                directExchange2);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory manualAckContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory retryContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setAdviceChain(retryInterceptor());

        return factory;
    }

    @Bean
    public RetryOperationsInterceptor retryInterceptor() {
        return RetryInterceptorBuilder.stateless()
                .maxAttempts(5)
                .backOffOptions(1000, 2.0, 10000)
                .recoverer(getDontRequeueRecoverer())
                .build();
    }

    private static MethodInvocationRecoverer<Object> getDontRequeueRecoverer() {
        return (args, cause) -> {
            // ничего не делаем — сообщение будет отвергнуто
            // главное — не ловим и не проглатываем ошибку
            throw new AmqpRejectAndDontRequeueException("Retries exhausted", cause);
        };
    }

}

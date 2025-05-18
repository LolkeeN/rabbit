package com.vasyl.practice.rabbitmq.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    public Queue testQueueTopic1() {
        return new Queue("testQueueTopic1", true);
    }

    @Bean
    public Queue testQueueTopic2() {
        return new Queue("testQueueTopic2", true);
    }

    @Bean
    public Queue testQueueTopic3() {
        return new Queue("testQueueTopic3", true);
    }

    @Bean
    public Queue testQueueFanout1() {
        return new Queue("testQueueFanout1", true);
    }


    @Bean
    public Queue testQueueFanout2() {
        return new Queue("testQueueFanout2", true);
    }

    @Bean
    public Queue testQueueFanout3() {
        return new Queue("testQueueFanout3", true);
    }

    @Bean
    public Queue testQueueDirect1() {
        return new Queue("testQueue", true);
    }


    @Bean
    public List<Binding> topicBindings() {
        Binding topicExchange1 = BindingBuilder.bind(testQueueTopic1()).to(topicExchange()).with("test.topic");
        Binding topicExchange2 = BindingBuilder.bind(testQueueTopic2()).to(topicExchange()).with("test.topic.#");
        Binding topicExchange3 = BindingBuilder.bind(testQueueTopic3()).to(topicExchange()).with("test.topic.number.*");

        Binding fanoutExchange1 = BindingBuilder.bind(testQueueFanout1()).to(fanoutExchange());
        Binding fanoutExchange2 = BindingBuilder.bind(testQueueFanout2()).to(fanoutExchange());
        Binding fanoutExchange3 = BindingBuilder.bind(testQueueFanout3()).to(fanoutExchange());

        Binding directExchange1 = BindingBuilder.bind(testQueueDirect1()).to(directExchange()).with("testKey");
        return List.of(topicExchange1, topicExchange2, topicExchange3, fanoutExchange1, fanoutExchange2, fanoutExchange3, directExchange1);
    }
}
